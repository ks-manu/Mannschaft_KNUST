import React,{Component} from 'react'
import TextField from 'material-ui/TextField';
import FlatButton from 'material-ui/FlatButton'
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import './LoginCard.css';



const styles = {
   marginLeft:20,
  };

export default class SignUp extends Component{
    constructor(props) {
        super(props);
        this.state = {value: 1};
      }
    
      handleChange = (event, index, value) => this.setState({value});
    
    checkIndexNumber
   
    render(){
        return(
            <div>
                    <TextField className="indexNumber" style={styles} maxlength="7" underlineShow={false} floatingLabelText="Index Number"
                    /><br />
                    
                    <TextField className="firstName" floatingLabelText="First Name" />
                    <br />
                    <TextField className="lastName" floatingLabelText="Last Name" />
                    <br />

                    <DropDownMenu value={this.state.value}  onChange={this.handleChange} >
                       <MenuItem value={1} primaryText="Department" /> 
                        <MenuItem value={2} primaryText="Computer Engineering" /> 
                        <MenuItem value={3} primaryText="Biomedical Engineering" />
                        <MenuItem value={4} primaryText="Electrical Engineering" />
                        
                    </DropDownMenu>
                    <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                        <MenuItem value={1} primaryText="Year" />
                        <MenuItem value={2} primaryText="100" />
                        <MenuItem value={3} primaryText="200" />
                        <MenuItem value={4} primaryText="300" />
                        <MenuItem value={5} primaryText="400" />
                        
                    </DropDownMenu>
                    <br />  
                    <DropDownMenu value={1} >
                        <MenuItem value={1} primaryText="Gender" />
                        <MenuItem value={2} primaryText="Male" />
                        <MenuItem value={2} primaryText="Female" />
                    </DropDownMenu> 
                    <br/>   
                    

                    <TextField className="password" floatingLabelText="Password" type="password"/>
                    <br />
                    <TextField className="passwordConfirm" floatingLabelText="Confirm Password" type="password"/>
                    <br />

                  
                    <FlatButton label="Join the team"  />
                   
            </div>    
        );
    }
}