//this function checks whether a user has been authenticated or assigned a token
function Authorise(Token, response, dbConn, fs){
    var tokenQuery = 'SELECT * FROM `Token` WHERE Token = "'+Token+'";';
    
    // Verify token
    dbConn.query(tokenQuery, function(error, result, fields){
        if (error | result == 0 | result == undefined){       // invalid token returns empty set
            fs.appendFileSync('serverlog', '\nFAILURE: Access denied for '+Token+' @ '+new Date+' #InvalidToken');
            if (error) fs.appendFileSync('serverlog', error);
            response.set('400');        //bad request
            response.end();
        }
        else{
            fs.appendFileSync('serverlog', '\nSUCCESS: Access granted for '+Token+' @ '+new Date);
        }
    });
}

module.exports = {
    Authorise:Authorise
}
