import React,{Component} from 'react';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
//import {Card, CardTitle, CardText} from 'material-ui/Card';
import Paper from 'material-ui/Paper';
import paperStyle from './PaperStyle';
import MenuBar from './MenuBar';






  export default class ChangeVenue extends Component {
    
     constructor(props) {
       super(props);
       this.state = {value:1 
       };
       this.handleChange=this.handleChange.bind(this);
     }
     handleChange(event, index, value) {this.setState({value});
    
    }
    
    render() {
     return (
  <div> 
    <MenuBar/>
    
    <Paper style={paperStyle}>
      <h1>Choose the venue for the class</h1>
      <div>
        <DropDownMenu value={this.state.value} onClick={this.handleChange}>
          <MenuItem value={1} primaryText="Lecture Theatre" />
          <MenuItem value={2} primaryText="Room A" />
          <MenuItem value={3} primaryText="Room B" />
          <MenuItem value={4} primaryText="Levine Hall" />
          <MenuItem value={5} primaryText="PB" />
        </DropDownMenu>
        <RaisedButton label="Change Class Venue"/>

      </div>
    </Paper>
    
    
  </div>
      );
     }
    }