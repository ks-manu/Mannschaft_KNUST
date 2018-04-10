//connect to db on page request, carry out an activity and output data
//var http = require('http');
connected = false;

var mysql = require('mysql'); // if you want to access sql services use sqlDB

//create a connection to be used for desired communication
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "classrep"
});

//test connection to database
con.connect(function(err) {
  if (err){
    console.log("Database is inaccessible! Is it up?"+"\n\n");
    console.log(err);
  }
  else {
    console.log("Connected! Database is accessible."+"\n\n");
//    connected = true;
  }
  
  
//select the desired database
/*
    //DATABASE SPECIFIED IN CON VARIABLE!!
       con.query(use_db, function(err, result){
            if(err){
                console.log(err);
            }
            else{
                console.log("selected "+db_name+"!");
                console.log("\n\n"+result+"\n\n");
            }
        });
*/

//query the database
var db_name = "my_db";                                  //specify database name
var table_name = "my_table";                            //specify table name
var var1 = "'kwaku'"; var2 = "'manu'";

var db_creator = "CREATE DATABASE IF NOT EXISTS "+db_name+";"; //create database query
var db_destroyer;                                       //DROP or DELETE TABLE
var use_db = "USE "+db_name+";";                        //query to select a particular database
var create_table = "CREATE TABLE "+table_name+";";      //query to create particular table
var show_tables = "SHOW TABLES;";                       //show tables in selected database
var describe_table = "DESCRIBE "+table_name+";";           //obtain schema of table
var insert = "INSERT INTO "+table_name+" VALUES("+var1+","+var2+");"; //insert var1 and var2 into table_name
var show_records = "SELECT * FROM "+table_name+";";     //obtain records in table_name
var boot_db = [use_db, show_tables];

//show tables
        con.query(show_tables, function(err,result,fields){
            if(err){
                console.log(err);
            }
            else{
                console.log("Loading tables:\n");
                console.log(JSON.stringify(result));
                console.log("\n\n");
            }
        });

//describe table
        con.query(describe_table, function(err,result,fields){
            if(err){
                console.log(err);
            }
            else{
                console.log("Loading table descrioption:\n");
                console.log(JSON.stringify(result));
                console.log("\n\n");
            }
        });

//insert values
 /*        con.query(insert, function(err,result){
            if(err){
                console.log(err);
            }
            else{
                var affected_rows = result.affectedRows, warnings = result.warningCount;
                
                console.log("Values inserted!\n");
                console.log("\n"+"Warning Count:"+warnings+"\n"+"Affected Rows:"+affected_rows   +"\n\n");
            }
        });
*/
//view records
        con.query(show_records, function(err,result,fields){
            if(err){
                console.log(err);
            }
            else{
//                console.log("Loading records:\n");
                JSON.stringify(result);                     //convert database response to json string
                for (i = 0; i<2; i++){
                    var username = result[i].name;              //store [nth] value of attribute name in variable
                    var password = result[i].password;          //store [nth] value of attribute password in variable
                    console.log(username+"\n"+password);
                    console.log("\n\n");
                }
            }
        });
});

/*switch(connected){
    case "false":
        console.log("Not connected to Database!");
    break;
     
    case "true":
        console.log("Connected to Database!");
*/
//create database if not exists
        //, function(err, result
        
/*
 //BLOCKING MODE NOT WORKING       
        con.query(db_creator, if(connected === true){
            function(err, result){
            
            if(err){
                console.log(err);
            }
            else{
                console.log("created "+db_name+"!");
                console.log(result);
            }
            }
        });
        

con.query(db_creator, function(err, result){
            
            if(err){
                console.log(err);
            }
            else{
                console.log("created "+db_name+"!");
                console.log(result);
            }
            }
        );

    break;
*/
