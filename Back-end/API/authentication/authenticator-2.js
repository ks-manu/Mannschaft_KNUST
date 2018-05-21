var tokeniser = require("./tokeniser.js");  //hash function to create Token
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

// LECTURER LOGIN
function lecturerLogin(credential1, credential2, response, dbConn, fs){
//credentials
    var Techmail = credential1;
    var Password = credential2;
    var Token = 0;
    
//query
    var integrityQuery = 'SELECT Token FROM Token WHERE UserID = "'+Techmail+'";';      //to check whether Token has already been assigned
    var passwordQuery = 'SELECT Password FROM `Lecturer_table` WHERE Tech_MAil = "'+Techmail+'";';      //to obtain pre-stored password for comparison and authentication
    
    dbConn.query(integrityQuery, function(err, result, fields){
        if(err || result !== 0){
            fs.appendFileSync('serverlog', '\nFAILURE: Database error for ' +Techmail+' @ '+new Date+' #PossibleTokenAssign');
            if(err) fs.appendFileSync('serverlog',  '\n'+err);
            
            response.status('403');     //forbidden: authentication failed
            response.end();
        }
        else{
            dbConn.query(passwordQuery, function(err, result, fields){
                if(err | result/*.length*/ === 0 | result == undefined){
                    if(err){
                        var message = "\nFAILURE: Database error for " + Techmail +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);     //log activities
                        fs.appendFileSync('serverlog', err);
                        
                        response.status('503');     //service unavailable
                        response.end();
                    }            
                    else if(result.length == 0){
                        var message = "\nFAILURE: No password match for " + Techmail +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);     //log activities
                        
                        response.status('403');     //forbidden: authentication failed
                        response.end();
                    }
                }
                else{
                var password = result[0].Password;
                    //validate user
                    if(Password === password){
                        //create Token
                            Token = tokeniser.Creator(Techmail, Password);
                            
                            //check Token validity, log, store, and respond
                            if(Token !== 0){
                                tokenQuery = 'INSERT INTO Token VALUES("'+Token+'", "'+Techmail+'", CURRENT_TIMESTAMP);';
                            //log Token creation
                                var message = "\nSUCCESS: Token generated for " + Techmail +" @ " + new Date;                        
                                fs.appendFileSync('serverlog', message);                    
                            //store Token
                                dbConn.query(tokenQuery, function(err, res){
                                    if(err){
                                        var message = "\nFAILURE: Token wasn't stored for " + Techmail +" @ " + new Date;                        
                                        fs.appendFileSync('serverlog', message);
                                        response.status('500');     //internal server error: Token wasn't stored
                                        response.end();
                                    }
                                    else{
                                    //store activities in server log                                
                                        var message = "\nSUCCESS: Token stored for " + Techmail +" @ " + new Date;                        
                                        fs.appendFileSync('serverlog', message);
                                        
                                        message = "\nSUCCESS: Login by " + Techmail +" @ " + new Date;
                                        fs.appendFileSync('serverlog', message);     //log activities
                                    //respond    
                                        response.status('200');
                                        response.send(Token);
                                        response.end();
                                    }
                                });
                            }
                            else{
                                var message = "\nFAILURE: Token not generated for " + Techmail +" @ " + new Date;                        
                                fs.appendFileSync('serverlog', message);     //log activities
                            //respond
                                response.status('503');    //service unavailable: Token not generated
                                response.end();
                            }
                    }
                    else{
                        var message = "\nFAILURE: No password match for " + Techmail +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);     //log activities
                        
                        response.status('403');     //forbidden: authenitication failed
                        response.end();
                    }
                }
            });
        }
    });
    
    
}
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//LECTURER LOGOUT
function lecturerLogout(Token, response, dbConn, fs){
    var logoutQuery = "DELETE FROM Token where Token = '"+Token+"';";
    
    dbConn.query(logoutQuery, function(err, result, fields){
        if(err | result.rowsAffected === 0){
            var message = "\nFAILURE: Lecturer Log out error for "+Token+" @ "+ new Date;
            fs.appendFileSync('serverlog', message);
            
            response.status('503');     //service unavailable
            response.end();
        }
        else if(result.rowsAffected ===1){
            var message = "\nSUCCESS: Lecturer Log out success for "+Token+" @ " +new Date;
            fs.appendFileSync('serverlog', message);
            
            response.status('200');
            response.end();
        }
        
    });
}

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
// STUDENT LOGIN
function studentLogin(username, password, response, dbConn, fs){
   //credentials
    var IndexNumber = parseInt(username);
    var Password = password;
    var Token = 0;
    
//queries
    var integrityQuery = 'SELECT Token FROM Token WHERE UserID = "'+IndexNumber+'";';      //to check whether Token has already been assigned
    var passwordQuery = 'SELECT Password FROM `students table` WHERE Index_Number = '+IndexNumber+' AND Password ="'+Password+'";';
    
    dbConn.query(integrityQuery, function(err, result, fields){
        if(err || result !== 0){
            fs.appendFileSync('serverlog', '\nFAILURE: Database error for ' +IndexNumber+' @ '+new Date+' #PossibleTokenAssign');
            if(err) fs.appendFileSync('serverlog',  '\n'+err);
            
//            response.status('403');     //forbidden: authentication failed
            response.end();
        }
        else{
            dbConn.query(passwordQuery, function(err, result, fields){
        //    console.log(result);
                if(err | result/*.length*/ === 0 | result == undefined){
                    if(err){
                        var message = "\nFAILURE: Database error for " + IndexNumber +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);     //log activities
                        fs.appendFileSync('serverlog', err);
                        
                        response.status('503');     //service unavailable
                        response.end();
                    }            
                    else if(result.length == 0){
                        var message = "\nFAILURE: No password match for " + IndexNumber +" @ " + new Date;                        
                        fs.appendFileSync('serverlog', message);     //log activities
                        
                        response.status('403');     //forbidden: authentication failed
                        response.end();
                    }
                }
                else{
                var password = result[0].Password;
                
                    //validate user
                    if(Password === password){
                        //create Token
                            Token = tokeniser.Creator(IndexNumber, Password);
                            //check Token validity, log, store, and respond
                            if(Token !== 0){
                                tokenQuery = 'INSERT INTO Token VALUES("'+Token+'", "'+IndexNumber+'", CURRENT_TIMESTAMP);';
                            //log Token creation
                                var message = "\nSUCCESS: Token generated for " + IndexNumber +" @ " + new Date;                        
                                fs.appendFileSync('serverlog', message);                    
                            //store Token
                                dbConn.query(tokenQuery, function(err, res){
                                    if(err){
                                        console.log(err);
                                        var message = "\nFAILURE: Token wasn't stored for " + IndexNumber +" @ " + new Date;                        
                                        fs.appendFileSync('serverlog', message);
                                        response.status('500');     //internal server error: Token wasn't stored
                                        response.end();
                                    }
                                    else{
                                        var message = "\nSUCCESS: Token stored for " + IndexNumber +" @ " + new Date;                        
                                        fs.appendFileSync('serverlog', message);
                                    //store activities in server log
                                        message = "\nSUCCESS: Login by " + IndexNumber +" @ " + new Date;
                                        fs.appendFileSync('serverlog', message);     //log activities
                                    //respond    
                                        response.status('200');
                                        response.send(Token);
                                        response.end();
                                    }
                                });
                            }
                            else{
                                var message = "\nFAILURE: Token not generated for " + IndexNumber +" @ " + new Date;                        
                                fs.appendFileSync('serverlog', message);     //log activities
                            //respond
                                response.status('503');    //service unavailable: Token not generated
                                response.end();
                            }
                    }
                }
            });
        }
    });
}
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//STUDENT LOGOUT
function studentLogout(Token, response, dbConn, fs){
    authoriser.Authorise(Token, response, dbConn, fs);
    
    var logoutQuery = "DELETE FROM Token where Token = '"+Token+"';";
    
    dbConn.query(logoutQuery, function(err, result, fields){
        if(err | result.rowsAffected === 0){
            var message = "\nFAILURE: Student Log out error for "+Token+" @ "+ new Date;
            fs.appendFileSync('serverlog', message);
            
            response.status('503');     //service unavailable
            response.end();
        }
        else if(result.rowsAffected === 1){
            var message = "\nSUCCESS: Student Log out success for "+Token+" @ " +new Date;
            fs.appendFileSync('serverlog', message);
            
            response.status('200');
            response.end();
        }
});
}

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
function studentSignUp(IndexNumber, FirstName, LastName, ProgrammeAndYear, Password, response, dbConn, fs){
    var integrityQuery = "SELECT * FROM Student WHERE IndexNumber="+IndexNumber+";";
    var signUpQuery = "INSERT INTO Student VALUES("+IndexNumber+", '"+FirstName+"', '"+LastName+"', '"+ProgrammeAndYear+"', '"+Password+"');";
    
    dbConn.query(integrityQuery, function(err, result){
        if(err || result !== 0){        //User NOT unregistered
            fs.appendFileSync('serverlog', '\nFAILURE: Sign Up on Database for '+IndexNumber+' '+FirstName+' '+LastName+' @ '+new Date+' #PossibleDuplicate');
            if(err) fs.appendFileSync('serverlog', '\n'+err);
            response.status('400');
            response.end;
        }
        else{
            dbConn.query(signUpQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){    //Insertion failure
                    fs.appendFileSync('serverlog', '\nFAILURE: Sign Up on Database for '+IndexNumber+' '+FirstName+' '+LastName+' @ '+new Date+' #DatabaseWriteErr');
                    if(err) fs.appendFileSync('serverlog', '\n'+err);
                    response.status('500');
                    response.end;
                }
                else{       //Insertion Success
                    fs.appendFileSync('serverlog', '\nSUCCESS: Sign Up on Database for '+IndexNumber+' '+FirstName+' '+LastName+' @ '+new Date);
                    response.status('200');
                    response.end();
                }
            });
        }
    });
}
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//provide functions with external scope
module.exports  = {
    lecturerLogin : lecturerLogin,
    lecturerLogout: lecturerLogout,
    studentLogin : studentLogin,
    studentLogout : studentLogout,
    studentSignUp : studentSignUp
}
