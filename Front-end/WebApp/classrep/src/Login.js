import React,{Component} from 'react'
import TextField from 'material-ui/TextField';
import FlatButton from 'material-ui/FlatButton';
import Paper from 'material-ui/Paper';
import {Link} from 'react-router-dom';
import './App';

const paperStyle={
    height:'85%',
    width:'50%',
    margin:'7%',
    textAlign:'center',
    backgroundColor:'#E0F7FA'
  }

export default class Login extends Component{
    constructor(){
        super();
        this.state = {
            email: '',
            password: ''
        }
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(e){
        e.preventDefault();
        console.log(this.state)
    }

    render(){
        return(
            <div>
                
                <Paper style={paperStyle}>
                    <h1>Class Rep</h1>
                    <TextField
                        floatingLabelText="Techmail" type="email" 
                        onChange={e => this.setState({email: e.target.value})}
                    /><br />
                    
                    <TextField  floatingLabelText="Password" type="password" 
                    onChange={e => this.setState({password: e.target.value})}
                    />
                    <br />
                    <Link to="/main"><FlatButton label="Login"  /></Link>
                </Paper>
         </div>       
        );
    }
}