
// sql driver
//var mysql = require('mysql');

var express = require('express');
var app = expre
var authenticator = require("./authenticator-1.js");


//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//create a connection to be used for desired communication
/* !!!!!!! var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "classrep"
});

//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//test connection to database
con.connect(function(err) {
  if (err){
    console.log("Database Inaccessible! Is it up?"+"\n\n");
    console.log(err);
//    connected = false;
  }
  else {
    console.log("Connected to ClassRep Database!"+"\n\n"+"Listening"+"\n\n");
//    connected = true;
  }
});

//provide SQL connection with external scope
/*module.exports = {
    con : con
}*/

reqData = "mrkwaku@gmail.com";
reqdata = "mrkwaku";

var token = null;

token = authenticator.lecturerLogin(reqData, reqdata);


console.log("done: "+token);
