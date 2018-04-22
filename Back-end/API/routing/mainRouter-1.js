<<<<<<< HEAD
//--------------------------------------------configuration-----------------------------------------------------------
var express = require('express');
var bodyParser = require('body-parser');
var authenticator = require("../../authentication/authenticator-2.js");
var fs = require('fs');

=======
var express = require('express');
var bodyParser = require('body-parser');
//var authenticate = require("../../authentication/authenticator.js");
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
//var authorise = require("../../authentication/authoriser.js");

var appRouter = express();

//appRouter.use(cookieParser());
appRouter.use(bodyParser.json());
appRouter.use(bodyParser.urlencoded({ extended: true }));

<<<<<<< HEAD
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

=======
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
//--------------------------------------------POST REQUESTS-----------------------------------------------------------
//sign in
appRouter.post('/users/authlib/:user_type/reqID=sign_in', function(request, response){
    switch(request.params.user_type){
        case("lecturer"):
            if(!request.body.user_id || !request.body.password){
<<<<<<< HEAD
                var message = "\nFAILURE: No request parameters for Sign In @ " + new Date;
                fs.appendFileSync('serverlog', message);
//ensure both user_id and password are not empty
                response.status("400");      //bad request
                response.send();
            }
            else{
                var techmail = request.body.user_id;
                var password = request.body.password;
                
                authenticator.lecturerLogin(techmail, password, response, dbConn, fs);
                
=======
//ensure both user_id and password are not empty
                response.status("400");      //bad request
                //response.send("Invalid details!");
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
            }
            break
        
        case("student"):
            if(!request.body.user_id || !request.body.password){       //ensure both user_id and password are not empty
<<<<<<< HEAD
                var message = "\nFAILURE: No request parameters for Sign In @ " + new Date;
                fs.appendFileSync('serverlog', message);
                
                response.status("400");      //bad request
                response.send();
            }
            else{
                var username = request.body.user_id;
                var password = request.body.password;
                
                authenticator.studentLogin(username, password, response, dbConn, fs);
                
=======
                response.status("400");      //bad request
                //response.send("Invalid details!");
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
            }
            break
        
    }
});

//sign out
<<<<<<< HEAD
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
            case "lecturer":
                authenticator.lecturerLogout(session_token, response, dbConn, fs);
                break
            
            case "student":
                authenticator.studentLogout(session_token, response, dbConn, fs);
                break
        }
=======
appRouter.post('/users/deauthlib/lecturer/reqID=:session-token', function(request, response){
    //extract token from URL
    if(!request.params.session-token){
        response.status("400");
        //response.send("No-Session-Token");
        console.log("Failed: POST request on Sign Out. No token");
    }
    else{
        var session_token = request.params.session-token;
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
    }
});

//process posted messages
appRouter.post('/data/systlab/post/reqID=:session-token', function(request, response){
    //extract token from URL
    if(!request.params.session-token){
        response.status("400");
        //response.send("No-Session-Token");
        console.log("Failed: POST request on Data. No token");
    }
    else{
        var session_token = request.params.session-token;
        var date = new Date();
//        console.log("Success: Token validated for "+session_token+" @ " +date);
    }
    //check session validity    
    
    
});

//collect results of vote on reschedule
appRouter.post("/data/systlab/poll/reqID=:session-token", function(request, response){
    //extract token from URL
    if(!request.params.session-token){
        response.status("400");
        //response.send("No-Session-Token");
        console.log("Failed: POST request on Poll");
    }
    else{
        var session_token = request.params.session-token;
        var date = new Date();
//        console.log("Success: Token validated for "+session_token+" @ " +date);
    }
    //check session validity    

});

//Update course session data
//Special function to only be used by lecturers
appRouter.post("/data/systlab/course/session/reqID=:session-token", function(request, response){
    //extract token from URL
    if(!request.params.session-token){
        response.status("400");
        //response.send("No-Session-Token");
        console.log("Failed: POST request on CCA Update");
    }
    else{
        var session_token = request.params.session-token;
        var date = new Date();
//        console.log("Success: Token validated for "+session_token+" @ " +date);
    }
});

//---------------------------------------------GET REQUESTS--------------------------------------------------------

//serve requested posts
appRouter.get('/data/post/reqID=:session-token/post/:program-year/:time', function(request, response){
    if(!request.params.session-token || !request.params.program-year || !request.params.program-year){
        response.status("400");
        //response.send("Invalid Details");
        console.log("Failed: GET request on Posts");
    }
    else{
        var session_token = request.params.session-token;
        var program_year = request.params.program-year;
        var messages_after = request.params.program-year;
    }
});

//Results of poll
appRouter.get('/data/share/reqID=:session-token/poll/:message-id', function(request, response){
    if(!request.params.session-token || !request.params.message-id){
        response.status("400");
        //response.send("Invalid Details");
        console.log("Failed: GET request on Poll");
    }
});

//serve course sessions
appRouter.get('/data/course/session/reqID=:session-token/share/:lecturer', function(request, response){
    if(!request.params.session-token || !request.params.lecturer){
        response.status("400");
        //response.send("Invalid Details");
        console.log("Failed: GET request on Course Sessions");
    }
});

//serve biodata

appRouter.get('/data/users/share/reqID=:session-token/:user-type', function(request, response){
    if(!request.params.session-token || !request.params.user-type){
        response.status("400");
        //response.send("Invalid Details");
        console.log("Failed: GET request on Bio Data");
    }
});

appRouter.listen(5555);
