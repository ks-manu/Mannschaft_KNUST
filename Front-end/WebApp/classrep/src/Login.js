import React,{Component} from 'react'
import TextField from 'material-ui/TextField';
import FlatButton from 'material-ui/FlatButton';
import Paper from 'material-ui/Paper';
import {Link} from 'react-router-dom';
import './App';
import paperStyle from './PaperStyle';


export default class Login extends Component{
    constructor(){
        super();
        this.state = {
            email: '',
            password: ''
        }
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit=(e) =>{
        // e.preventDefault();
        console.log('logged in')
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
                    <Link to="/main"><FlatButton label="Login" onClick={this.onSubmit} /></Link>
                </Paper>
         </div>       
        );
    }
}