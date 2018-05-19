/*// to be removed
var express = require('express');
var bodyParser = require('body-parser');

var appRouter = express();
appRouter.use(bodyParser.json());
appRouter.use(bodyParser.urlencoded({ extended: true }));
appRouter.listen(5555);

var fs = require('fs');
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
//
*/

function getBioData(token, type, user_id, response, dbConn, fs){
    var lcrSessQuery = 'SELECT Tech_MAil, First_Name, Last_Name, Title FROM Lecturer_table WHERE Tech_MAil ="'+user_id+'";';
    var stdSessQuery = 'SELECT Index_Number, First_Name, Last_Name, Programme_Year FROM Course_sessions WHERE Index_Number="'+user_id+'";';       //what about other dept/yrs courses?
    
    switch(user_type){
        case "lcr":
            dbConn.query(lcrSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on Bio by '+token+' for '+user_id+' @ '+new Date+' #BadParams');
                    response.set('400');
                    response.send('Bad Request or Params');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on Bio by '+token+' for '+user_id+'@ '+new Date);
                    response.set('200');
                    response.send('0K');
                }
            });
            break
        case "std":
            dbConn.query(stdSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on Bio by '+token+' for '+user_id+' @ '+new Date+' #BadParams');
                    response.set('400');
                    response.send('Bad Request or Params');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on Bio by '+token+' for '+user_id+' @ '+new Date);
                    response.set('200');
                    response.send('0K');
                }
            });
            break
        default:
            response.status("400");
            response.send("Invalid Details");
            fs.appendFileSync('serverlog', 'FAILURE: GET Data on Bio by'+token+' for '+user_id+' @ '+new Date+' #BadUsrParams');
    }
}

function getSessions(token, user_type, key, response, dbConn, fs){
    var lcrSessQuery = 'SELECT * FROM Course_sessions WHERE Programme_Year ="'+key+'";';        //what about other lect's teaching same year?
    var stdSessQuery = 'SELECT * FROM Course_sessions WHERE Techmail="'+key+'";';       //what about other dept/yrs courses?
    
    switch(user_type){
        case "lcr":
            dbConn.query(lcrSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on CCA by '+token+' @ '+new Date+' #BadParams');
                    response.set('400');
                    response.send('Bad Request or Params');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on CCA by '+token+' @ '+new Date);
                    response.set('200');
                    response.send('0K');
                }
            });
            break
        case "std":
            dbConn.query(stdSessQuery, function(err, result, field){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Get Data on CCA by '+token+' @ '+new Date+' #BadParams');
                    response.set('400');
                    response.send('Bad Request or Params');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog', '\nSUCCESS: Get Data on CCA by '+token+' @ '+new Date);
                    response.set('200');
                    response.send('0K');
                }
            });
            break
        default:
            response.status("400");
            response.send("Invalid Details");
            fs.appendFileSync('serverlog', 'FAILURE: GET request on CCA by'+token+' @ '+new Date+' #BadUsrParams');
    }
}


//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//GET REQUEST HANDLERS
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
function getPolls(session_token, message_id, response, dbConn, fs){
    var yesCount = 'SELECT Vote, Count(*) number FROM Votes_table WHERE Vote="yes" AND message_ID="'+message_id+'";';
    var noCount = 'SELECT Vote, Count(*) number FROM Votes_table WHERE Vote="no" AND message_ID="'+message_id+'";';
    var yes, no;
    
    dbConn.query(yesCount, function(err, result, fields){
        if(err || result == undefined ){
            fs.appendFileSync('serverlog', '\nFAILURE: ERR No results on YES count for '+session_token+' @ '+ new Date);
            fs.appendFileSync('serverlog', err);
            response.set('500');
            response.send('Database Error');
        }
        else if(result == 0 /*|| result[0].Count(*) = 0*/){
            fs.appendFileSync('serverlog', '\nFAIL[URE]: No results on YES count for '+session_token+' @ '+ new Date);
            yes = "NA";
        }
        else{
            JSON.stringify(result);
            yes = result[0].number;
            fs.appendFileSync('serverlog', '\nSUCCESS: Obtained results on YES count for '+session_token+' @ '+ new Date);
        }
        dbConn.query(noCount, function(err, result, fields){
            if(err || result == undefined ){
                fs.appendFileSync('serverlog', '\nFAILURE: ERR No results on NO count for '+session_token+' @ '+ new Date);
                fs.appendFileSync('serverlog', err);
                response.set('500');
                response.send('Database Error');
            }
            else if (result == 0/*|| result[0].Count(*) = 0*/){
                fs.appendFileSync('serverlog', '\nFAIL[URE]: No results on NO count for '+session_token+' @ '+ new Date);
                no = "NA";
            }
            else{
                JSON.stringify(result);
                no = result[0].number;
                fs.appendFileSync('serverlog', '\nSUCCESS: Obtained results on NO count for '+session_token+' @ '+ new Date);
            }
//            console.log('{"Message_ID":"'+message_id+'","Yes":'+yes+',"No":'+no+'}');
            response.set('200');
            response.send('{"Message_ID":"'+message_id+'","Yes":'+yes+',"No":'+no+'}');
        });
    });
}

function getPosts(token, session, time, response, dbConn, fs){
    var nPostsQuery = 'SELECT * FROM '+session+' WHERE Time_sent > '+time+';';
    var allPostsQuery = 'SELECT * FROM '+course_code+';';
    
    switch(time){
        case "all":
            dbConn.query(allPostsQuery, function(err, result, fields){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Request on '+session+' Posts Table by '+token+' @ '+new Date+'#allGETFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('400');        //bad request. response unavailable
                    response.send('response unavailable');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog','\nSUCCESS: Request on '+session+' Posts Table by '+token+' @ '+new Date);
                    response.set('200');
                    response.send(result);
                }
            });
            break
        default:
            dbConn.query(nPostsQuery, function(err, result, fields){
                if(err || result == 0 || result == undefined){
                    fs.appendFileSync('serverlog', '\nFAILURE: Request on '+session+' Posts Table by '+token+' @ '+new Date+'#nGETFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('400');        //bad request. response unavailable
                    response.send('response unavailable');
                }
                else{
                    JSON.stringify(result);
                    fs.appendFileSync('serverlog','\nSUCCESS: Request on '+session+' Posts Table by '+token+' @ '+new Date);
                    response.set('200');
                    response.send(result);
                }
            });
    }
}

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//POST REQUEST HANDLERS
//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
function updateCourse(token, alter, course_code, starting_time, ending_time, techmail, venue, programme_year, dbConn, fs, response){
    
    var updateAllQuery = 'UPDATE Course_sessions SET Starting_Time ="'+starting_time+'", Ending_Time="'+ending_time+'", Venue="'+venue+'" WHERE `Course(code)`="'+course_code+'" AND Techmail="'+techmail+'" AND Programme_Year="'+programme_year+'";';
    var updateSNEQuery = 'UPDATE Course_sessions SET Starting_Time ="'+starting_time+'", Ending_Time="'+ending_time+'" WHERE `Course(code)`="'+course_code+'" AND Techmail="'+techmail+'" AND Programme_Year="'+programme_year+'";';
    var updateVENQuery = 'UPDATE Course_sessions SET Venue="'+venue+'" WHERE `Course(code)`="'+course_code+'" AND Techmail="'+techmail+'" AND Programme_Year="'+programme_year+'";';
    
    switch(alter){
        case "all":
            dbConn.query(updateAllQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Session-Update by '+token+' @ '+new Date+' #UpdateAllFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('500');
                    response.send('Internal Server Error');
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Session-Update by '+token+' @ '+new Date+' #UpdateAllOK');
                    response.set('200');        //OK>success
                    response.send("Success");
                }
            });
            break
        case "sne":
            dbConn.query(updateSNEQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Session-Update by '+token+' @ '+new Date+' #UpdateSNEFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('500');
                    response.send('Internal Server Error');
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Session-Update by '+token+' @ '+new Date+' #UpdateSNEOK');
                    response.set('200');        //OK>success
                    response.send("Success");
                }
            });
            break
        case "ven":
            dbConn.query(updateVENQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Session-Update by '+token+' @ '+new Date+' #UpdateVENFail>CheckForERR');
                    if (err) fs.appendFileSync('serverlog', err);
                    response.set('500');
                    response.send('Internal Server Error');
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Session-Update by '+token+' @ '+new Date+' #UpdateVENOK');
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

function postVote(token, messageID, indexNo, vote, dbConn, fs, response){
    var voteIntegrity = 'SELECT vote FROM Votes_table WHERE Message_ID ="'+messageID+'" AND Index_Number ="'+indexNo+'";';      //double voting check query
    var voteQuery = 'INSERT INTO Votes_table VALUES("'+messageID+'", "'+indexNo+'", "'+vote+'")';     //accepted vote query
    
    dbConn.query(voteIntegrity, function(err, result, fields){
        if(err || result != 0){
            fs.appendFileSync('serverlog', '\nFAILURE: Vote-Log by '+token+' @ '+new Date+' #DoubleVoteAttempt');        //log activities
            response.set('400');        //error code: bad request   ---> TENDS TO RETURN 200 but messages are unchanged
            response.send("bad");        //error message
        }
        else{
            dbConn.query(voteQuery, function(err, result, fields){
                if(err || result.rowsAffected == 0){
                    fs.appendFileSync('serverlog', '\nFAILURE: Vote-Log by '+token+' @ '+new Date);     //log activities
                    response.set('500');        //internal server error
                    response.send();
                }
                else{
                    fs.appendFileSync('serverlog', '\nSUCCESS: Vote-Log by '+token+' @ '+new Date);     //log activities
                    response.set('200');        //ok
                    response.send("OK");
                }
            });
        }
    });
}

function postDb(token, course_table, userID, dateTime, postID, text, votable, attachment, dBConn, fs, response){
    var postQuery = "INSERT INTO "+course_table+" VALUES ('"+postID+"','"+text+"','"+attachment+"','"+votable+"','"+userID+"','"+dateTime+"');";
    
    dbConn.query(postQuery, function(err, result, fields){
        if(err || result.rowsAffected == 0){
            fs.appendFileSync('serverlog', '\nFAILURE: Post-Store by '+token+' @ '+new Date);
            response.set('500');        //internal server error
            response.send();
        }
        else{
            fs.appendFileSync('serverlog', '\nSUCCESS: Post-Store by '+token+' '+new Date);
            response.set('200');        //success
            response.send();
        }
    });


}




module.exports = {
    postDb:postDb,
    postVote:postVote,
    updateCourse:updateCourse,
    getPolls:getPolls,
    getSessions:getSessions,
    getBioData:getBioData
}
