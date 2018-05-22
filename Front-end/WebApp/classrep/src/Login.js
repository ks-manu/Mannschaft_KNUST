import React,{Component} from 'react'
import TextField from 'material-ui/TextField';
import FlatButton from 'material-ui/FlatButton';
import Paper from 'material-ui/Paper';
import {Link} from 'react-router-dom';
import './App';
import paperStyle from './PaperStyle';
import axios from 'axios';


export default class Login extends Component{
    constructor(){
        super();
        this.state = {
            userID: '',
            password: ''
        }
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit=(e) =>{
         e.preventDefault();
         
         const user ={
             UserID:this.state.userID,
             Password:this.state.password
             
         }

         if(user.UserID&&user.Password){
            axios.post(`https://jsonplaceholder.typicode.com/users`,{user})
            .then(res=>{
                console.log(res);
                console.log(res.data);
         }
         
         );
         
        

            console.log(this.state.userID);
            console.log(this.state.Password);
          
          document.getElementById('email').value='';
          document.getElementById('password').value='';
          
        //       });
         //}
         

        
    }
    }
    render(){
        return(
            <div>
                
                <Paper style={paperStyle}>
                    <h1>Class Rep</h1>
                    <TextField
                        floatingLabelText="UserID" type="email" id="email"  errorText="This field is required"
                        onChange={e => this.setState({userID: e.target.value})}
                    /><br />
                    
                    <TextField  floatingLabelText="Password" type="password" id="password" 
                    errorText="This field is required" 
                    onChange={e => this.setState({password: e.target.value})}
                    />
                    <br />
                    <Link to="/main"><FlatButton label="Login" onClick={this.onSubmit}/></Link>
                </Paper>
         </div>       
        );
    }
    }