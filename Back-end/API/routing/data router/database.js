//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//POST REQUEST HANDLERS
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
function updateCourse(Token, Operation, CourseCode, StartingTime, EndingTime, Techmail, Venue, ProgrammeAndYear, Day, OldDay, dbConn, fs, response){
    
    var updateSessionQuery = 'UPDATE CourseSession SET StartingTime ="'+StartingTime+'", EndingTime="'+EndingTime+'", Venue="'+Venue+'", Day="'+Day+'" WHERE CourseCode="'+CourseCode+'" AND Techmail="'+Techmail+'" AND ProgrammeAndYear="'+ProgrammeAndYear+'" AND Day="'+OldDay'";';
    var clearSessionQuery = 'DELETE FROM CourseSession WHERE CourseCode="'+CourseCode+'" AND Techmail="'+Techmail+'" AND ProgrammeAndYear="'+ProgrammeAndYear+'" AND StartingTime ="'+StartingTime+'" AND EndingTime="'+EndingTime+'" AND Venue="'+Venue+'" AND Day="'+Day+'";';  
    
    switch(Operation){
        case "Update":
            dbConn.query(updateSessionQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Session-Update by '+Token+' @ '+new Date+' #UpdateAllFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.status('500');
                    response.end();
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Session-Update by '+Token+' @ '+new Date+' #UpdateAllOK');
                    response.status('200');        //OK>success
                    response.end();
                }
            });
            break
        case "Clear":
            dbConn.query(clearSessionQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Session-Delete by '+Token+' @ '+new Date+' #DeleteFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.status('500');
                    response.end();
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Session-Update by '+Token+' @ '+new Date+' #DeleteSessionOK');
                    response.status('200');        //OK>>success
                    response.end();
                }
            });
            break
        default:
            response.status('400');
            response.end();
    }
}

function postVote(Token, PostID, IndexNumber, Vote, dbConn, fs, response){
    var voteIntegrity = 'SELECT Vote FROM Vote WHERE PostID ="'+PostID+'" AND IndexNumber ="'+IndexNumber+'";';      //double voting check query
    var voteQuery = 'INSERT INTO Vote VALUES("'+PostID+'", "'+IndexNumber+'", "'+Vote+'")';     //accepted Vote query
    
    dbConn.query(voteIntegrity, function(err, result, fields){
        if(err || result != 0){
            fs.appendFileSync('serverlog', '\nFAILURE: Vote-Log by '+Token+' @ '+new Date+' #DoubleVoteAttempt');        //log activities
            if (err) fs.appendFileSync('serverlog', err);
            response.status('400');        //error code: bad request   ---> TENDS TO RETURN 200 but messages are unchanged
            response.end();
        }
        else{
            dbConn.query(voteQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Vote-Log by '+Token+' @ '+new Date);     //log activities
                    if (err) fs.appendFileSync('serverlog', err);
                    response.status('500');        //internal server error
                    response.end();
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Vote-Log by '+Token+' @ '+new Date);     //log activities
                    response.status('200');        //ok
                    response.end("OK");
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
            response.status('500');        //internal server error
            response.end();
        }
        else{
            fs.appendFileSync('serverlog', '\nSUCCESS: Post-Store by '+Token+' '+new Date);
            response.status('200');        //success
            response.end();
        }
    });


}
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//GET REQUEST HANDLERS
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
function getBioData(Token, UserType, UserID, response, dbConn, fs){
    var lcrSessQuery = 'SELECT Techmail, FirstName, LastName, Title FROM Lecturer WHERE Techmail ="'+UserID+'";';
    var stdSessQuery = 'SELECT IndexNumber, FirstName, LastName, ProgrammeAndYear FROM CourseSession WHERE IndexNumber="'+UserID+'";';
    
    switch(UserType){
        case "Lecturer":
            dbConn.query(lcrSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on Bio by '+Token+' for '+UserID+' @ '+new Date+' #BadParams');
                    if(err) fs.appendFileSync('serverlog', err);
                    response.status('400');
                    response.end();
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on Bio by '+Token+' for '+UserID+'@ '+new Date);
                    response.status('200');
                    response.end();
                }
            });
            break
        case "Student":
            dbConn.query(stdSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on Bio by '+Token+' for '+UserID+' @ '+new Date+' #BadParams');
                    response.status('400');
                    response.end();
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on Bio by '+Token+' for '+UserID+' @ '+new Date);
                    response.status('200');
                    response.end();
                }
            });
            break
        default:
            response.status("400");
            response.end();
            fs.appendFileSync('serverlog', '\nFAILURE: GET Data on Bio by'+Token+' for '+UserID+' @ '+new Date+' #BadUsrParams');
    }
}

function getSessions(Token, UserType, Query, response, dbConn, fs){
    var stdSessQuery = 'SELECT * FROM CourseSession WHERE ProgrammeAndYear ="'+Query+'";';
    var lcrSessQuery = 'SELECT * FROM CourseSession WHERE Techmail="'+Query+'";';
    
    switch(UserType){
        case "Lecturer":
            dbConn.query(lcrSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on CCA by '+Token+' @ '+new Date+' #BadParams');
                    if(err) fs.appendFileSync('serverlog', err);
                    response.status('400');
                    response.end();
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on CCA by '+Token+' @ '+new Date);
                    response.status('200');
                    response.end();
                }
            });
            break
        case "Student":
            dbConn.query(stdSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on CCA by '+Token+' @ '+new Date+' #BadParams');
                    if(err) fs.appendFileSync('serverlog', err);
                    response.status('400');
                    response.end();
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on CCA by '+Token+' @ '+new Date);
                    response.status('200');
                    response.end();
                }
            });
            break
        default:
            response.status("400");
            response.end();
            fs.appendFileSync('serverlog', '\nFAILURE: GET request on CCA by'+Token+' @ '+new Date+' #BadUsrParams');
    }
}

function getPolls(Token, PostID, response, dbConn, fs){
    var yesCount = 'SELECT Vote, Count(*) number FROM Vote WHERE Vote="yes" AND PostID="'+PostID+'";';
    var noCount = 'SELECT Vote, Count(*) number FROM Vote WHERE Vote="no" AND PostID="'+PostID+'";';
    var yes, no;
    
    dbConn.query(yesCount, function(err, result, fields){
        if(err || result == undefined ){
            fs.appendFileSync('serverlog', '\nFAILURE: ERR No results on YES count for '+Token+' @ '+ new Date);
            if(err) fs.appendFileSync('serverlog', err);
            response.status('500');
            response.end();
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
                if(err) fs.appendFileSync('serverlog', err);
                response.status('500');
                response.end();
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
            response.status('200');
            response.send('{"PostID":"'+PostID+'","Result":'+ parseInt(yes-no) +'}');
            response.end();
        });
    });
}

function getPosts(Token, CourseCode, TimeSent, response, dbConn, fs){
    var nPostsQuery = 'SELECT * FROM '+CourseCode+' WHERE TimeSent > '+TimeSent+';';
    var allPostsQuery = 'SELECT * FROM '+CourseCode+';';
    
    switch(TimeSent){
        case "all":
            dbConn.query(allPostsQuery, function(err, result, fields){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Request on '+CourseCode+' Posts Table by '+Token+' @ '+new Date+'#allGETFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.status('400');        //bad request. response unavailable
                    response.end();
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog','\nSUCCESS: Request on '+CourseCode+' Posts Table by '+Token+' @ '+new Date);
                    response.status('200');
                    response.send(result);
                    response.end();
                }
            });
            break
        default:
            dbConn.query(nPostsQuery, function(err, result, fields){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Request on '+CourseCode+' Posts Table by '+Token+' @ '+new Date+'#nGETFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.status('400');        //bad request. response unavailable
                    response.end();
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog','\nSUCCESS: Request on '+CourseCode+' Posts Table by '+Token+' @ '+new Date);
                    response.status('200');
                    response.send(result);
                    response.end();
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
