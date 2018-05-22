//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//POST REQUEST HANDLERS
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
function updateCourse(Token, alter, CourseCode, StartingTime, EndingTime, Techmail, Venue, ProgrammeAndYear, dbConn, fs, response){
    
    var updateAllQuery = 'UPDATE CourseSession SET StartingTime ="'+StartingTime+'", EndingTime="'+EndingTime+'", Venue="'+Venue+'" WHERE `CourseCode`="'+CourseCode+'" AND Techmail="'+Techmail+'" AND ProgrammeAndYear="'+ProgrammeAndYear+'";';
    var updateSNEQuery = 'UPDATE CourseSession SET StartingTime ="'+StartingTime+'", EndingTime="'+EndingTime+'" WHERE `CourseCode`="'+CourseCode+'" AND Techmail="'+Techmail+'" AND ProgrammeAndYear="'+ProgrammeAndYear+'";';
    var updateVENQuery = 'UPDATE CourseSession SET Venue="'+Venue+'" WHERE `CourseCode`="'+CourseCode+'" AND Techmail="'+Techmail+'" AND ProgrammeAndYear="'+ProgrammeAndYear+'";';
    
    switch(alter){
        case "all":
            dbConn.query(updateAllQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Session-Update by '+Token+' @ '+new Date+' #UpdateAllFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('500');
                    response.send('Internal Server Error');
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Session-Update by '+Token+' @ '+new Date+' #UpdateAllOK');
                    response.set('200');        //OK>success
                    response.send("Success");
                }
            });
            break
        case "sne":
            dbConn.query(updateSNEQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Session-Update by '+Token+' @ '+new Date+' #UpdateSNEFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('500');
                    response.send('Internal Server Error');
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Session-Update by '+Token+' @ '+new Date+' #UpdateSNEOK');
                    response.set('200');        //OK>success
                    response.send("Success");
                }
            });
            break
        case "ven":
            dbConn.query(updateVENQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Session-Update by '+Token+' @ '+new Date+' #UpdateVENFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('500');
                    response.send('Internal Server Error');
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Session-Update by '+Token+' @ '+new Date+' #UpdateVENOK');
                    response.set('200');        //OK>success
                    response.send("Success");
                }
            });
            break
        default:
            response.set('400');
            response.send();
    }
}

function postVote(Token, PostID, IndexNumber, Vote, dbConn, fs, response){
    var voteIntegrity = 'SELECT Vote FROM Vote WHERE PostID ="'+PostID+'" AND IndexNumber ="'+IndexNumber+'";';      //double voting check query
    var voteQuery = 'INSERT INTO Vote VALUES("'+PostID+'", "'+IndexNumber+'", "'+Vote+'")';     //accepted Vote query
    
    dbConn.query(voteIntegrity, function(err, result, fields){
        if(err || result != 0){
            fs.appendFileSync('serverlog', '\nFAILURE: Vote-Log by '+Token+' @ '+new Date+' #DoubleVoteAttempt');        //log activities
            if (err) fs.appendFileSync('serverlog', err);
            response.set('400');        //error code: bad request   ---> TENDS TO RETURN 200 but messages are unchanged
            response.send("bad");        //error message
        }
        else{
            dbConn.query(voteQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Vote-Log by '+Token+' @ '+new Date);     //log activities
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('500');        //internal server error
                    response.send();
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Vote-Log by '+Token+' @ '+new Date);     //log activities
                    response.set('200');        //ok
                    response.send("OK");
                }
            });
        }
    });
}

function postDb(Token, CourseCode, SentBy, TimeSent, PostID, Message, Votable, Attachment, dBConn, fs, response){
    var postQuery = "INSERT INTO "+CourseCode+" VALUES ('"+PostID+"','"+Message+"','"+Attachment+"','"+Votable+"','"+SentBy+"','"+TimeSent+"');";
    
    dbConn.query(postQuery, function(err, result, fields){
        if(err || result.rowsAffected == 0){
            fs.appendFileSync('serverlog', '\nFAILURE: Post-Store by '+Token+' @ '+new Date);
            if (err) fs.appendFileSync('serverlog', err);
            response.set('500');        //internal server error
            response.send();
        }
        else{
            fs.appendFileSync('serverlog', '\nSUCCESS: Post-Store by '+Token+' '+new Date);
            response.set('200');        //success
            response.send();
        }
    });


}

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//GET REQUEST HANDLERS
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
function getBioData(Token, UserType, UserID, response, dbConn, fs){
    var lcrSessQuery = 'SELECT Techmail, First_Name, Last_Name, Title FROM Lecturer_table WHERE Techmail ="'+UserID+'";';
    var stdSessQuery = 'SELECT IndexNumber, First_Name, Last_Name, ProgrammeAndYear FROM Course_sessions WHERE IndexNumber="'+UserID+'";';
    
    switch(UserType){
        case "Lecturer":
            dbConn.query(lcrSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on Bio by '+Token+' for '+UserID+' @ '+new Date+' #BadParams');
                    response.set('400');
                    response.send('Bad Request or Params');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on Bio by '+Token+' for '+UserID+'@ '+new Date);
                    response.set('200');
                    response.send('0K');
                }
            });
            break
        case "Student":
            dbConn.query(stdSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on Bio by '+Token+' for '+UserID+' @ '+new Date+' #BadParams');
                    response.set('400');
                    response.send('Bad Request or Params');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on Bio by '+Token+' for '+UserID+' @ '+new Date);
                    response.set('200');
                    response.send('0K');
                }
            });
            break
        default:
            response.status("400");
            response.send("Invalid Details");
            fs.appendFileSync('serverlog', '\nFAILURE: GET Data on Bio by'+Token+' for '+UserID+' @ '+new Date+' #BadUsrParams');
    }
}

function getSessions(Tokenadmin@m0nt3r0:/home/workspace/Software Engineering/Mannschaft_KNUST$ git pull
, UserType, Query, response, dbConn, fs){
    var stdSessQuery = 'SELECT * FROM CourseSession WHERE ProgrammeAndYear ="'+Query+'";';
    var lcrSessQuery = 'SELECT * FROM CourseSession WHERE Techmail="'+Query+'";';
    
    switch(UserType){
        case "lcr":
            dbConn.query(lcrSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on CCA by '+Token+' @ '+new Date+' #BadParams');
                    if(err) fs.appendFileSync('serverlog', err);
                    response.set('400');
                    response.send('Bad Request or Params');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on CCA by '+Token+' @ '+new Date);
                    response.set('200');
                    response.send('0K');
                }
            });
            break
        case "std":
            dbConn.query(stdSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on CCA by '+Token+' @ '+new Date+' #BadParams');
                    response.set('400');
                    response.send('Bad Request or Params');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on CCA by '+Token+' @ '+new Date);
                    response.set('200');
                    response.send('0K');
                }
            });
            break
        default:
            response.status("400");
            response.send("Invalid Details");
            fs.appendFileSync('serverlog', 'FAILURE: GET request on CCA by'+Token+' @ '+new Date+' #BadUsrParams');
    }
}

function getPolls(Token, PostID, response, dbConn, fs){
    var yesCount = 'SELECT Vote, Count(*) number FROM Vote WHERE Vote="yes" AND PostID="'+PostID+'";';
    var noCount = 'SELECT Vote, Count(*) number FROM Vote WHERE Vote="no" AND PostID="'+PostID+'";';
    var yes, no;
    
    dbConn.query(yesCount, function(err, result, fields){
        if(err || result == undefined ){
            fs.appendFileSync('serverlog', '\nFAILURE: ERR No results on YES count for '+Token+' @ '+ new Date);
            fs.appendFileSync('serverlog', err);
            response.set('500');
            response.send('Database Error');
        }
        else if(result == 0 /*|| result[0].Count(*) = 0*/){
            fs.appendFileSync('serverlog', '\nFAIL[URE]: No results on YES count for '+Token+' @ '+ new Date);
            yes = "NA";
        }
        else{
            JSON.stringify(result);
            yes = result[0].number;
            fs.appendFileSync('serverlog', '\nSUCCESS: Obtained results on YES count for '+Token+' @ '+ new Date);
        }
        dbConn.query(noCount, function(err, result, fields){
            if(err || result == undefined ){
                fs.appendFileSync('serverlog', '\nFAILURE: ERR No results on NO count for '+Token+' @ '+ new Date);
                fs.appendFileSync('serverlog', err);
                response.set('500');
                response.send('Database Error');
            }
            else if (result == 0/*|| result[0].Count(*) = 0*/){
                fs.appendFileSync('serverlog', '\nFAIL[URE]: No results on NO count for '+Token+' @ '+ new Date);
                no = "NA";
            }
            else{
                JSON.stringify(result);
                no = result[0].number;
                fs.appendFileSync('serverlog', '\nSUCCESS: Obtained results on NO count for '+Token+' @ '+ new Date);
            }
            response.set('200');
            response.send('{"PostID":"'+PostID+'","Result":'+ int(yes-no) +'}');
        });
    });
}

function getPosts(Token, CourseCode, Time, response, dbConn, fs){
    var nPostsQuery = 'SELECT * FROM '+CourseCode+' WHERE Time_sent > '+Time+';';
    var allPostsQuery = 'SELECT * FROM '+CourseCode+';';
    
    switch(time){
        case "all":
            dbConn.query(allPostsQuery, function(err, result, fields){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Request on '+session+' Posts Table by '+Token+' @ '+new Date+'#allGETFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('400');        //bad request. response unavailable
                    response.send('response unavailable');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog','\nSUCCESS: Request on '+session+' Posts Table by '+Token+' @ '+new Date);
                    response.set('200');
                    response.send(result);
                }
            });
            break
        default:
            dbConn.query(nPostsQuery, function(err, result, fields){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Request on '+session+' Posts Table by '+Token+' @ '+new Date+'#nGETFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('400');        //bad request. response unavailable
                    response.send('response unavailable');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog','\nSUCCESS: Request on '+session+' Posts Table by '+Token+' @ '+new Date);
                    response.set('200');
                    response.send(result);
                }
            });
    }
}

module.exports = {
    postDb:postDb,
    postVote:postVote,
    updateCourse:updateCourse,
    getPolls:getPolls,
    getSessions:getSessions,
    getBioData:getBioData
}
