function hash(string){
	var hash = 0;
	
	if (str.length == 0) return hash; // Don't hash null entries
	
	for (i = 0; i < str.length; i++) {
		char = str.charCodeAt(i);
		hash = ((hash<<5)-hash)+char;
		
		hash = hash & hash; // Convert to 32bit integer
	}
	return hash;
}


var username = "credential1";
var password = "credential2";
str = username+password;

/*  THEY BOTH WORK THE SAME

console.log("1 "+hash(username+password));
console.log("2 "+ hash(str));
*/
