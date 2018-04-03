import React,{Component} from 'react'
import TextField from 'material-ui/TextField';
import FlatButton from 'material-ui/FlatButton'



export default class Login extends Component{
    render(){
        return(
            <div>
                <TextField
                    floatingLabelText="Index Number" type="text" maxlength="7"
                /><br />
                
                <TextField  floatingLabelText="Password" type="password" />
                <br />
                <FlatButton label="Login"  />
                New here? Sign Up
            </div>    
        );
    }
}