import React,{Component} from 'react'
import TextField from 'material-ui/TextField';
import FlatButton from 'material-ui/FlatButton'
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';




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
}import React,{Component} from 'react';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
import {Card, CardTitle, CardText} from 'material-ui/Card';
import Paper from 'material-ui/Paper';



const paperStyle={
  height:'100%',
  width:'40%',
  margin:'4%',
  textAlign:'center',
  backgroundColor:'#E0F7FA'
}



export default class ChangeTime extends React.Component {

 constructor(props) {
   super(props);
   this.state = {value: 1
   };
   this.handleChange=this.handleChange.bind(this);
 }
 handleChange(event, index, value) {this.setState({value});}

render() {
 return (
    <div className="container">

<div>  
          <Card>
            <CardTitle title="Change Class Time"  />
            
            <CardText>
              Changes in Class Time will be reflected in the class timetable.
            </CardText>
          </Card>
        </div>
    

        <Paper style={paperStyle}>
                    <h1>Choose the day for the class</h1>
      <div>
      <DropDownMenu value={this.state.value} onChange={this.handleChange}>
        <MenuItem value={1} primaryText="Monday" />
        <MenuItem value={2} primaryText="Tuesday" />
        <MenuItem value={3} primaryText="Wednesday" />
        <MenuItem value={4} primaryText="Thursday" />
        <MenuItem value={3} primaryText="Friday" />
      </DropDownMenu>
      </div>
      </Paper>


      <Paper style={paperStyle}>
                    <h1>Class Starts at</h1>
      <div>
      <DropDownMenu value={this.state.value} onChange={this.handleChange}>
        <MenuItem value={1} primaryText="8:00" />
        <MenuItem value={2} primaryText="9:00" />
        <MenuItem value={3} primaryText="10:00" />
        <MenuItem value={4} primaryText="11:00" />
        <MenuItem value={5} primaryText="12:00" />
        <MenuItem value={6} primaryText="1:00" />
        <MenuItem value={7} primaryText="2:00" />
        <MenuItem value={8} primaryText="3:00" />
        <MenuItem value={9} primaryText="4:00" />
        <MenuItem value={10} primaryText="5:00" />
        <MenuItem value={11} primaryText="6:00" />
      </DropDownMenu>
      </div>
      </Paper>



      <Paper style={paperStyle}>
                    <h1>And Ends at</h1>
      <div>
      <DropDownMenu value={this.state.value} onChange={this.handleChange}>
        <MenuItem value={1} primaryText="8:00" />
        <MenuItem value={2} primaryText="9:00" />
        <MenuItem value={3} primaryText="10:00" />
        <MenuItem value={4} primaryText="11:00" />
        <MenuItem value={5} primaryText="12:00" />
        <MenuItem value={6} primaryText="1:00" />
        <MenuItem value={7} primaryText="2:00" />
        <MenuItem value={8} primaryText="3:00" />
        <MenuItem value={9} primaryText="4:00" />
        <MenuItem value={10} primaryText="5:00" />
        <MenuItem value={11} primaryText="6:00" />
      </DropDownMenu>
      </div>
      </Paper>

      <div>
       <RaisedButton label="Change Class Time"/>
     </div>
     </div>
  );
 }
}