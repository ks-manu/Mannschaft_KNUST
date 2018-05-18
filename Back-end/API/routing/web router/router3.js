//THIS IS SUPPOSED TO BE A MORE FLEXIBLE SERVER
//IT SERVE ALL REQUESTED FILES AS LONG AS THEY EXIST IN THE PROJECT DIRECTORY
var http = require('http');
var fs = require('fs');

var server = http.createServer(function (request, response) {
    var url = request.url
    var params = request.params
    
    console.log("url: " + url);
    console.log("params: " + params);
    
    fs.readFile('./' + request.url, function(err, data) {
        if (!err) {
            var dotoffset = request.url.lastIndexOf('.');
            var mimetype = dotoffset == -1
                            ? 'text/plain'
                            : {
                                '.html' : 'text/html',
                                '.ico' : 'image/x-icon',
                                '.jpg' : 'image/jpeg',
                                '.png' : 'image/png',
                                '.gif' : 'image/gif',
                                '.css' : 'text/css',
                                '.js' : 'text/javascript'
                                }[ request.url.substr(dotoffset) ];
            response.setHeader('Content-type' , mimetype);
            response.end(data);
            console.log( request.url, mimetype );
        } else {
            console.log ('file not found: ' + request.url);
            response.writeHead(404, "Not Found");
            response.end();
        }
    })}).listen(3000);
   
