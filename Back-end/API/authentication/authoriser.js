/*var fs = require('fs');
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
*/
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//Lecturer authoriser
function Authorise(tokenString, response, dbConn, fs){
    var tokenQuery = 'SELECT * FROM `token_table` WHERE token = "'+tokenString+'";';
    
    // Verify token
    dbConn.query(tokenQuery, function(error, result, fields){
        if (error | result == 0 | result == undefined){       // invalid token returns empty set
            fs.appendFileSync('\nserverlog', 'FAILURE: Access denied for '+tokenString+' @ '+new Date+' #InvalidToken');
            response.set('400');        //bad request
            response.send();
        }
        else{
            fs.appendFileSync('\nserverlog', 'SUCCESS: Access granted for '+tokenString+' @ '+new Date);
            
        }
    });
}


module.exports = {
    Authorise:Authorise
}

//var token="gDnqNYMlRy", response=0;
//Authorise(token, response, dbConn, fs);
