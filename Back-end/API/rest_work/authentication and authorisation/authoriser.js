//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//Lecturer authoriser
function lecturerAuthoriser(tokenString){
    var lecturerToken = tokenString;
    var tokenQuery = 'SELECT * FROM `Lecturer Token` WHERE token = "'+tokenString+'";"
    
    /*
    =====================
        VERIFY TOKEN
    =====================
    */
    con.query(tokenQuery, function(error, result fields){
        if (error){
            return -404; //INVALID TOKEN OR TOKEN EXPIRED
        }
        else{
            return 200; //VALID TOKE OR AUTHORISATION SUCCESS
        }
    }
}

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//Student authoriser
function studentAuthoriser(tokenString){
    var studentToken = tokenString;
    var tokenQuery = 'SELECT * FROM `Student Token` WHERE token = "'+tokenString+'";"
    
    /*
    =====================
        VERIFY TOKEN
    =====================
    */
    con.query(tokenQuery, function(error, result fields){
        if (error){
            return -404; //INVALID TOKEN OR TOKEN EXPIRED
        }
        else{
            return 200; //VALID TOKE OR AUTHORISATION SUCCESS
        }
    }
}

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
