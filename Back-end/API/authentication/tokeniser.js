var hashids = require('hashids');

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

// Token creator: invoked externally and returns Token
    
function Creator(UserID, Password){
//obtain current time
    var currentTime = new Date().getTime();     //time in seconds
    var Token = hash(UserID+Password+currentTime);
    return Token;       //return tested and successful
}

module.exports = {
    Creator : Creator,
}
