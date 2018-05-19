var express = require('express');
var bodyParser = require('body-parser');
//var authenticate = require("../../authentication/authenticator.js");
//var authorise = require("../../authentication/authoriser.js");
var fs = require('fs');
var webRouter = express();

//appRouter.use(cookieParser());
webRouter.use(bodyParser.json());
webRouter.use(bodyParser.urlencoded({ extended: true }));

var router = express.Router();

webRouter.use('/', router);

router.get('/:token/', function(request,response) {
    if(!request.params.token){
        response.status('400');     //bad request
        response.send();    
    }
    else{
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
            }else {
                console.log ('file not found: ' + request.url);
                //response.writeHead(404, "Not Found");
                response.end();
            }
        });
    }
});
//var server = http.createServer(function (request, response) {
//webRouter.get('/home', function)

webRouter.listen(5555);
