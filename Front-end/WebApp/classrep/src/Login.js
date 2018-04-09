import React,{Component} from 'react'
import TextField from 'material-ui/TextField';
import FlatButton from 'material-ui/FlatButton'



export default class Login extends Component{
    render(){
        return(
            <div>
                <TextField
                    floatingLabelText="Techmail" type="email" 
                /><br />
                
                <TextField  floatingLabelText="Password" type="password" />
                <br />
                <FlatButton label="Login"  />
                
            </div>    
        );
    }
}