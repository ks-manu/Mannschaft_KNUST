var fs = require('fs');
var serverlog = fs.readFileSync('serverlog', 'utf8');
var tokeniser = require("./tokeniser.js");  //hash function to create token

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

// LECTURER LOGIN

function lecturerLogin(credential1, credential2, response, dbConn){
//credentials
    var techMail = credential1;
    var userPassword = credential2;
    var token = 0;
    
//query
    var passwordQuery = 'SELECT Password FROM `lecturer table` WHERE Tech_MAil = "'+techMail+'";';
    
    dbConn.query(passwordQuery, function(err, result, fields){
        if(err | result.length === 0){
            if(err){
                var message = "\nFAILURE: Database error for " + techMail +" @ " + new Date;                        
                fs.appendFileSync('serverlog', message);     //log activities
                fs.appendFileSync('serverlog', err);
                
                response.status('503');     //service unavailable
                response.send();
            }            
            else if(result.length == 0){
                var message = "\nFAILURE: No password match for " + techMail +" @ " + new Date;                        
                fs.appendFileSync('serverlog', message);     //log activities
                
                response.status('403');     //forbidden: authentication failed
                response.send();
            }
        }
        else{
        var password = result[0].Password;
            //validate user
            if(userPassword === password){
                //create token
                    token = tokeniser.Creator(techMail, userPassword);
                    
                    //check token validity, log, store, and respond
                    if(token != 0){
                        tokenQuery = 'INSERT INTO token_table VALUES("'+token+'", "'+techMail+'", CURRENT_TIMESTAMP);';
                    //log token creation
                        var message = "\nSUCCESS: Token generated for " + techMail +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);                    
                    //store token
                        dbConn.query(tokenQuery, function(err, res){
                            if(err){
                                var message = "\nFAILURE: Token wasn't stored for " + techMail +" @ " + new Date;                        
                                fs.appendFileSync('serverlog', message);
                                response.status('500');     //internal server error: token wasn't stored
                                response.send();
                            }
                            else{
                            //store activities in server log                                
                                var message = "\nSUCCESS: Token stored for " + techMail +" @ " + new Date;                        
                                fs.appendFileSync('serverlog', message);
                                
                                message = "\nSUCCESS: Login by " + techMail +" @ " + new Date;
                                fs.appendFileSync('serverlog', message);     //log activities
                            //respond    
                                response.status('200');
                                response.send(token);                 
                            }
                        
                        });
                    }
                    else{
                        var message = "\nFAILURE: Token not generated for " + techMail +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);     //log activities
                    //respond
                        response.status('503');    //service unavailable: token not generated
                        response.send();
                    }
            }
            else{
                var message = "\nFAILURE: No password match for " + techMail +" @ " + new Date;                        
                fs.appendFileSync('serverlog', message);     //log activities
                
                response.status('403');     //forbidden: authenitication failed
            }
        }
    });
}
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//LECTURER LOGOUT
function lecturerLogout(session-token, response, dbConn){

}

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
// STUDENT LOGIN
function studentLogin(username, password, response, dbConn){
   //credentials
    var indexNumber = parseInt(username);
    var userPassword = password;
    var token = 0;
    
//query
    var passwordQuery = 'SELECT Password FROM `students table` WHERE Index_Number = '+indexNumber+' AND Password ="'+userPassword+'";';
    
    dbConn.query(passwordQuery, function(err, result, fields){
        if(err | result.length === 0){
            if(err){
                var message = "\nFAILURE: Database error for " + indexNumber +" @ " + new Date;                        
                fs.appendFileSync('serverlog', message);     //log activities
                fs.appendFileSync('serverlog', err);
                
                response.status('503');     //service unavailable
                response.send();
            }            
            else if(result.length == 0){
                var message = "\nFAILURE: No password match for " + indexNumber +" @ " + new Date;                        
                fs.appendFileSync('serverlog', message);     //log activities
                
                response.status('403');     //forbidden: authentication failed
                response.send();
            }
        }
        else{
        var password = result[0].Password;
        
            //validate user
            if(userPassword === password){
                //create token
                    token = tokeniser.Creator(indexNumber, userPassword);
                    
                    //check token validity, log, store, and respond
                    if(token != 0){
                        tokenQuery = 'INSERT INTO token_table VALUES("'+token+'", "'+indexNumber+'", CURRENT_TIMESTAMP);';
                    //log token creation
                        var message = "\nSUCCESS: Token generated for " + indexNumber +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);                    
                    //store token
                        dbConn.query(tokenQuery, function(err, res){
                            if(err){
                                console.log(err);
                                var message = "\nFAILURE: Token wasn't stored for " + indexNumber +" @ " + new Date;                        
                                fs.appendFileSync('serverlog', message);
                                response.status('500');     //internal server error: token wasn't stored
                                response.send();
                            }
                            else{
                                var message = "\nSUCCESS: Token stored for " + indexNumber +" @ " + new Date;                        
                                fs.appendFileSync('serverlog', message);
                                
                            //store activities in server log
                                message = "\nSUCCESS: Login by " + indexNumber +" @ " + new Date;
                                fs.appendFileSync('serverlog', message);     //log activities
                            //respond    
                                response.status('200');
                                response.send(token);                 
                            }
                        
                        });
                    }
                    else{
                        var message = "\nFAILURE: Token not generated for " + indexNumber +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);     //log activities
                    //respond
                        response.status('503');    //service unavailable: token not generated
                        response.send();
                    }
            }
            
        }
    });
}
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//STUDENT LOGOUT
function studentLogout(session-token, response, dbConn){

}

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//provide functions with external scope
module.exports  = {
    lecturerLogin : lecturerLogin,
    studentLogin : studentLogin
}
