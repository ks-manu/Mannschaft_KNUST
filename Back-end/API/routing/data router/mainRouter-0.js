//BASED ON RESPONSE FROM STACK EXCHANGE
//USES SWITCH-CASE BLOCK TO HELP ME LOAD STYLESHEETS AND OTHER NEEDED FILES
var http = require('https');
var database = require('mysql')
var fs = require('fs');
var authenticate = require("../authentication/authenticator.js");
var authorise = require("../authentication/authoriser.js");

var server = http.createServer(function (request, response) {
    console.log('request was made: ' + request.url);
    switch (request.url) {

// LOGIN
        case "/users/lecturers/req?=sign_in":
            var token;
            
            
            
            response.writeHead(200, {'Content-Type': 'text/html'});
            fs.createReadStream(__dirname + '/index.html', 'utf8').pipe(response);  
            break;  

//TEMPLATES
        case "/templates" :
            response.writeHead(200, {"Content-Type": "text/html"});
            fs.createReadStream(__dirname + '/templates.html', 'utf8').pipe(response);
            break;

//PROLOGUE TEMPLATE
//ABLE TO SERVE FILES WITH CASE
//UNABLE TO SERVE FILES WITH EMBEDDED IF
        case "/html5up-prologue/index.html":
            response.writeHead(200, {'Content-Type': 'text/html'});
            fs.createReadStream(__dirname + '/html5up-prologue/index.html', 'utf8').pipe(response);
            break;
/*        case "/html5up-prologue/assets/css/main.css":
            response.writeHead(200, {'Content-Type': 'text/css'});
            fs.createReadStream(__dirname + '/html5up-prologue/assets/css/main.css').pipe(response);
            break;
*/            
//LOADS 404 BY DEFAULT
        default :    
            response.writeHead(404, {"Content-Type": "text/html"});
            fs.createReadStream(__dirname + '/404.html', 'utf8').pipe(response);
    };
//    response.end();
}).listen(3000);
