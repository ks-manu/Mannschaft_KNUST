var http = require('http');

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('Simple server\n');
}).listen(8000);

console.log('Server running at port 8000/');
