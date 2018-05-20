//--------------------------------------------configuration-----------------------------------------------------------
var express = require('express');
var bodyParser = require('body-parser');
var authenticator = require("../../authentication/authenticator-2.js");
var fs = require('fs');

//var authorise = require("../../authentication/authoriser.js");

var appRouter = express();

//appRouter.use(cookieParser());
appRouter.use(bodyParser.json());
appRouter.use(bodyParser.urlencoded({ extended: true }));

var mysql = require('mysql');
var dbConn = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "classrep"
});

dbConn.connect(function(err) {
  if (err){
    console.log("Database Inaccessible! Is it up?"+"\n\n");
    console.log(err);
  }
  else {
    console.log("Connected to ClassRep Database!"+"\n\n"+"Listening"+"\n\n");
  }
});

//--------------------------------------------POST REQUESTS-----------------------------------------------------------
//sign in
appRouter.post('/users/authlib/:user_type/reqID=sign_in', function(request, response){
    switch(request.params.user_type){
        case("Lecturer"):
            if(!request.body.TechMail || !request.body.Password){
                var message = "\nFAILURE: No request parameters for Sign In @ " + new Date;
                fs.appendFileSync('serverlog', message);
//ensure both user_id and password are not empty
                response.status("400");      //bad request
                response.send();
            }
            else{
                var TechMail = request.body.TechMail;
                var Password = request.body.Password;
                
                authenticator.lecturerLogin(TechMail, Password, response, dbConn, fs);
                
            }
            break
        
        case("Student"):
            if(!request.body.IndexNumber || !request.body.Password){       //ensure both user_id and password are not empty
                var message = "\nFAILURE: No request parameters for Sign In @ " + new Date;
                fs.appendFileSync('serverlog', message);
                
                response.status("400");      //bad request
                response.send();
            }
            else{
                var IndexNumber = request.body.IndexNumber;
                var Password = request.body.Password;
                
                authenticator.studentLogin(IndexNumber, Password, response, dbConn, fs);
                
            }
            break
        
    }
});

//sign out
appRouter.post('/users/deauthlib/:user_type/reqID=:session_token', function(request, response){
    //extract token from URL
    if(!request.params.session_token){
        var message = "\nFAILURE: No request parameters for Sign Out @ " + new Date;
        fs.appendFileSync('serverlog', message);
        
        response.status("400");     //bad request
        response.send();
    }
    else{
        var session_token = request.params.session_token;
        
        switch(request.params.user_type){
            case "Lecturer":
                authenticator.lecturerLogout(session_token, response, dbConn, fs);
                break
            
            case "Student":
                authenticator.studentLogout(session_token, response, dbConn, fs);
                break
        }
    }
});

///process posted messages
appRouter.post('/data/systlab/:session_token/post/:course_table', function(request, response){
    //extract token from URL
    if(!request.params.session_token){
        var message = "\nFAILURE: No session token on Post-Store @ "+ new Date;
        fs.appendFileSync('serverlog', message);
        
        response.status("400");
        response.send();
    }
    else{
    //check contents
        authoriser.Authorise(request.params.session_token, response, dbConn, fs);       //check session token validity
        //SQL TIMESTAMP Format 2018-04-22 10:58:16
        
        //var session_token = request.params.session_token;
        //var date = new Date();
        database.postDb(request.params.session_token, request.params.course_table, request.body.Sent_By, request.body.Time_sent, request.body.Post_ID, request.body.Message, request.body.Votable, request.body.Attachment, dbConn, fs, response);
    }
    //check session validity    
    
    
});

//collect results of vote on message
appRouter.post("/data/systlab/reqID=:session_token/poll", function(request, response){
    //extract token from URL
    if(!request.params.session_token|!request.body.messageID|!request.body.indexNo|!request.body.vote){
//        response.set();        //bad request
        response.send();
        fs.appendFileSync('serverlog', '\nFAILURE: POST request on Poll@ '+new Date);        //log activities
//        console.log("Failed: POST request on Poll");
    }
    else{
        authoriser.Authorise(request.params.session_token, response, dbConn, fs);
        database.postVote(request.params.session_token, request.body.messageID, request.body.indexNo, request.body.vote, dbConn, fs, response);
//        console.log("Success: Token validated for "+session_token+" @ " +date);
    }
});

//Update course session data
//Special function only be used by lecturers
appRouter.post("/data/systlab/course/session/reqID=:session_token", function(request, response){
    //extract token from URL
    if(!request.params.session_token | !request.body.alter){
        response.status("400");
        response.send("Either session_token or alter or both params missing");
        fs.appendFileSync('serverlog', '\nFAILURE: Update on Course Sessions @ '+new Date+' #TokenOrAlter');
    }
    else{
        authoriser.Authorise(request.params.session_token, response, dbConn, fs);       //check session token validity
        database.updateCourse(request.params.session_token, request.body.alter, request.body.course_code, request.body.starting_time, request.body.ending_time, request.body.techmail, request.body.venue, request.body.programme_year, dbConn, fs, response);
    }
});

//---------------------------------------------GET REQUESTS--------------------------------------------------------

//serve requested posts
appRouter.get('/data/post/reqID=:session_token/:session/:time', function(request, response){
    if(!request.params.session_token || !request.params.course_code || !request.params.time){
        fs.appendFileSync('serverlog','\nFAILURE: Request on Get posts @ '+new Date+' #TokenOrCodeOrTime')
        response.status("400");
        response.send('Invalid params');
    }
    else{
        authoriser.Authorise(request.params.session_token, response, dbConn, fs);       //check session token validity
        database.getPosts(request.params.session_token, request.params.course_code, request.params.time, response, dbConn, fs);
    }
});

//Results of poll
appRouter.get('/data/share/reqID=:session_token/poll/:message_id', function(request, response){
    if(!request.params.session_token || !request.params.message_id){
        response.status("400");     //bad request
        response.send("Invalid Details");
        fs.appendFileSync('serverlog', 'FAILURE: GET request on Poll Results @ '+new Date+' #BadParams');
    }
    else{
        authoriser.Authorise(request.params.session_token, response, dbConn, fs);       //check session token validity
        database.getPolls(request.params.session_token, request.params.message_id, response, dbConn, fs);
    }
});

//serve course sessions
appRouter.get('/data/course/session/reqID=:session_token/share/:user_type/:key', function(request, response){
    if(!request.params.session_token || !request.params.user_type || !request.params.key){
        response.status("400");
        response.send("Invalid Details");
        fs.appendFileSync('serverlog', 'FAILURE: GET request on CCA @ '+new Date+' #BadParams');
    }
    else{
        authoriser.Authorise(request.params.session_token, response, dbConn, fs);       //check session token validity
        database.getSessions(request.params.session_token, request.params.user_type, request.params.key, response, dbConn, fs);
    }
});

//serve biodata
appRouter.get('/data/users/share/reqID=:session_token/:user_type/:user_id', function(request, response){
    if(!request.params.session_token || !request.params.user_type|| !request.params.user_id){
        response.status("400");
        response.send("Invalid Details");
        fs.appendFileSync('serverlog', 'Failed: GET request on Bio Data');
    }
    else{
        authoriser.Authorise(request.params.session_token, response, dbConn, fs);       //check session token validity
        database.getBioData(request.params.session_token, request.params.user_type, request.params.user_id, response, dbConn, fs);
    }
});

appRouter.listen(5555);
