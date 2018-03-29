/* MAY NOT BE NECESSARY. MAIN APP WILL MAINTAIN CONNECTION WITH DATABASE AND CALL OTHER FUNCTIONS
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

// sql driver
var mysql = require('mysql');
//hash function
var credentialHash = require("./hashFunction.js");

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//create a connection to be used for desired communication
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "classrep"
});

//test connection to database
con.connect(function(err) {
  if (err){
    console.log("Database is inaccessible to Authenticator! Is it up?"+"\n\n");
    console.log(err);
    connected = false;
  }
  else {
    console.log("Authenticator Connected!"+"\n\n");
    connected = true;
  });
*/
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

// LECTURER LOGIN
//pass username (techmail or index number) AND password to this function and it will return
function lecturerLogin(credential1, credential2)
{
    var techMail = credential1;
    var password = credential2;
   // var lectTable = "`Lecturer Table`";
    var loginQuery = 'SELECT password FROM `Lecturer Table` WHERE Tech_MAil = "'+techMail+'";';
    var token;
    // code to obtain current time and format it for SQL
    var currentTime;
    var tokenQuery = 'INSERT INTO `Token Table` VALUES("'+token+'", "'+currentTime+'");';
    
    /*
    ========================================
    ATTEMPT AUTHORISATION
    ========================================
    */
    con.query(loginQuery, function(err, result, fields){
        var Password = result.Password;
        
        //authorise user
        if (password === Password){
            console.log("Successful login by "+techMail);
            
            // hash credentials and store as token
            token = credentialHash.hash(techMail+password+currentTime);
                /*
                =================
                STORE TOKEN
                =================
                */
            con.query(tokenQuery, function(err, result, fields){
                /*
                ENSURE SUCCESS BEFORE PROCEEDING
                */
            });
            
        return token;
        }
        else return -404; //error when user is unauthorised
    });
}


// STUDENT LOGIN
//pass username (techmail or index number) AND password to this function and it will return
function studentLogin(credential1, credential2)
{
    var username = credential1;
    var password = credential2;
   // var lectTable = "`Lecturer Table`";
    var loginQuery = 'SELECT password FROM `Lecturer Table` WHERE Tech_MAil = "'+username+'";';
    var token;
    // code to obtain current time and format it for SQL
    var currentTime;
    var tokenQuery = 'INSERT INTO `Token Table` VALUES("'+token+'", "'+currentTime+'");';
    
    con.query(loginQuery, function(err, result, fields){
        var Password = result.Password;
        
        //authorise user
        if (password === Password){
            console.log("Successful login by "+username);
            
            // hash credentials and store as token
            // TIME TO BE INCLUDED TO HASH
            token = credentialHash.hash(username+password);
            //store token
            con.query(tokenQuery, function(err, result, fields){
                /*
                ENSURE SUCCESS BEFORE PROCEEDING
                */
            });
            
        return token;
        }
        else return -404; //error when user is unauthorised
    });
}
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+  
