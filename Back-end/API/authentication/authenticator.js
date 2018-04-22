var tokeniser = require("./tokeniser.js");  //hash function to create token
//var databaseDriver = require("./tempRouter.js"); //provide access to database driver
var mysql = require('mysql');
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "classrep"
});

con.connect(function(err) {
  if (err){
    console.log("Database Inaccessible! Is it up?"+"\n\n");
    console.log(err);
//    connected = false;
  }
  else {
    console.log("Connected to ClassRep Database!"+"\n\n"+"Listening"+"\n\n");
//    connected = true;
  }
});
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

/* LECTURER LOGIN
pass username (techmail or index number) AND password to this function and it will return token on success else error
*/
    var success = false;
function lecturerLogin(credential1, credential2){
//credentials
    var techMail = credential1;
    var userPassword = credential2;
    var token = 0;
//query
    var passwordQuery = 'SELECT Password FROM `lecturer table` WHERE Tech_MAil = "'+techMail+'";';
    

/*    ========================================
                AUTHORISATION
      ========================================    */
      
//BLOCKING VERSION

        var sqlResponse = con.query(passwordQuery);
//convert mysql object into JSON array AND extract data
//        var Result = JSON.stringify(sqlResponse);
        console.log(sqlResponse);        //conversion successful
//        var dbPassword = result[0].Password;  //object is an array AND contains only one object
//        console.log(dbPassword);    //acquisition successful
        
/*    con.query(passwordQuery, function(err, result, fields){
//convert mysql object into JSON array AND extract data
        var Result = JSON.stringify(result);
        console.log(Result);        //conversion successful
        var dbPassword = result[0].Password;  //object is an array AND contains only one object
        console.log(dbPassword);    //acquisition successful

//authorise user
        if (dbPassword === userPassword){
            console.log("Successful login by "+techMail);
            token = tokeniser.Creator(techMail, userPassword);
            console.log(token);     //token received successfully
            return token;
            success = true;
            
          //  return token;
        }
        
        if (success === true) console.log(token);   //didn't exit non-blocking code loop
    });
*/
}
//return token if successfully created
if (success === true) console.log("out of loop" + token);
//if (h != 0) return token; 

// STUDENT LOGIN
//pass username (techmail or index number) AND password to this function and it will return token on success else error
/*function studentLogin(credential1, credential2)
{
    var username = credential1;
    var password = credential2;
   // var lectTable = "`Lecturer Table`";
    var passwordQuery = 'SELECT password FROM `Lecturer Table` WHERE Tech_MAil = "'+username+'";';
    var token;
    // code to obtain current time and format it for SQL
    var currentTime;
    var tokenQuery = 'INSERT INTO `Token Table` VALUES("'+token+'", "'+currentTime+'");';
    
    con.query(passwordQuery, function(err, result, fields){
        var Password = result.Password;
        
        //authorise user
        if (password === Password){
            console.log("Successful login by "+username);
            
            // hash credentials and store as token
            // TIME TO BE INCLUDED TO HASH
            token = tokeniser.hash(username+password);
            //store token
            con.query(tokenQuery, function(err, result, fields){
                /*
                ENSURE SUCCESS BEFORE PROCEEDING
                
            });
            
        return token;
        }
        else return -404; //error when user is unauthorised
    });
}*/
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//provide functions with external scope
module.exports  = {
    lecturerLogin : lecturerLogin,
//    studentLogin : studentLogin
}
