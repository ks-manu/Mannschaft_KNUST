import React,{Component} from 'react';
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
    this.state = {
      value1: 1,
      value2: 1,
      value3: 1
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
  <DropDownMenu
                value={this.state.value1}
                onChange={(e, i, value) => this.setState({value1: value})}>
         <MenuItem value={1} primaryText="Monday" />
         <MenuItem value={2} primaryText="Tuesday" />
         <MenuItem value={3} primaryText="Wednesday" />
         <MenuItem value={4} primaryText="Thursday" />
         <MenuItem value={5} primaryText="Friday" />
       </DropDownMenu>
       </div>
       </Paper>

    
 



 


       <Paper style={paperStyle}>
                     <h1>Class Starts at</h1>
       <div>
  <DropDownMenu
                value={this.state.value2}
                onChange={(e, i, value) => this.setState({value2: value})}>
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
  <DropDownMenu
                value={this.state.value3}
                onChange={(e, i, value) => this.setState({value3: value})}>
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
