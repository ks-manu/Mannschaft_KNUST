var hashids = require('hashids');
/*
Updates on hashids as of 1.0.0. Several public functions are renamed to be more appropriate: Function encrypt() changed to encode(), Function decrypt() changed to decode(), Function encryptHex() changed to encodeHex(), Function decryptHex() changed to decodeHex()
*/

function hash(string){
	var hash = 0;
	
	if (string.length == 0) return hash; // Don't hash null entries
	
	for (i = 0; i < string.length; i++) {
		char = string.charCodeAt(i);
		hash = ((hash<<5)-hash)+char;
		
		hash = hash & hash; // Convert to 32bit integer
	}
	
	hash = hashUp(hash);
	
	return hash;
}

function hashUp(alphanumeric){
    var hash = new hashids("Mannschaft KNUST - ClassRep", 10);
    
    var tokenFinal = hash.encode(alphanumeric);
    return tokenFinal;
}

// token creator: to be called externally and should store and return token
    
function Creator(userName, UserPassword){
//credentials
    var User_ID = userName;
    var Password = UserPassword;
//obtain current time
    var currentTime = new Date;
//token insertion query
//    var tokenQuery = 'INSERT INTO `Token Table` VALUES("'+token+'","'+User_ID+'", "CURRENT_TIME");';
    
// hash credentials and store as token
    var token = hash(User_ID+Password+currentTime);
//              console.log(token);     //token creates successfully
/*=================
    STORE TOKEN
  =================*/
            /*con.query(tokenQuery, function(err, result, fields){

                ENSURE SUCCESS BEFORE PROCEEDING
              }*/
//    (token).toString();
    return token;       //return tested and successful
}

// token deletor: to be called when user logs out
function Deletor(token){

}

module.exports = {
    Creator : Creator,
}
