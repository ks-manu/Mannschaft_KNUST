var http = require('http')
var fs = require('fs');

var server = http.createServer(function(req,res){
    console.log('request was made: ' + req.url);
    
//SERVE PAGE BASED ON VALUE OF 'req'
//SEPARATE IF-ELSE STATEMENTS NEEDED TO SERVE VARIOUS FILES
    if(req.url === '/home' || req.url === '/'){
        res.writeHead(200, {'Content-Type': 'text/html'});
        fs.createReadStream(__dirname + '/index.html', 'utf8').pipe(res);
    }
    else if(req.url === '/templates'){
        res.writeHead(200, {'Content-Type': 'text/html'});
        fs.createReadStream(__dirname + '/templates.html', 'utf8').pipe(res);
        
/*      EMBEDDED IF STATEMENTS DON'T WORK
        if(req.url === '/html5up-prologue/index.html'){
            res.writeHead(200, {'Content-Type': 'text/html'});
            fs.createReadStream(__dirname + '/html5up-prologue/index.html', 'utf8').pipe(res);
         }*/
    }
    else if(req.url === '/html5up-prologue/index.html'){
        res.writeHead(200, {'Content-Type': 'text/html'});
        fs.createReadStream(__dirname + '/html5up-prologue/index.html', 'utf8').pipe(res);
        
    }
    else if(req.url === '/html5up-prologue/assets/css/main.css'){
            res.writeHead(200, {'Content-Type': 'text/css'});
            fs.createReadStream(__dirname + '/html5up-prologue/assets/css/main.css').pipe(res);
            //fs.createReadStream(__dirname + '/html5up-prologue/assets/css/main.css').pipe(res);
    }
    else if(req.url === '/html5up-striped/index.html'){
        res.writeHead(200, {'Content-Type': 'text/html'});
        fs.createReadStream(__dirname + '/html5up-striped/index.html', 'utf8').pipe(res);
    }
    else if(req.url === '/templates/whatsapp-web-template/index.html'){}

// RETURN 404 FOR INVALID REQUESTS    
    else{
        res.writeHead(404, {'Content-Type': 'text/html'});
        fs.createReadStream(__dirname + '/404.html').pipe(res);
    }
    
/*    res.writeHead(200, {'Content-Type': 'text/html'});
    
    var myReadStream = fs.createReadStream(__dirname + '/index.html', 'utf8');
    myReadStream.pipe(res);
*/
});

server.listen(3000, '0.0.0.0');
console.log('listening on port 3000');
