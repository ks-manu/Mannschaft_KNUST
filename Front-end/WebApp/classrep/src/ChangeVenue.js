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
    backgroundColor:'#E0F7FA',
  
  }



  export default class ChangeVenue extends React.Component {
    
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
                <CardTitle title="Change Class Venue"  />
                
                <CardText>
                  <p>Change the location where the class will be held.</p>
                  <p>There may be another class occupying the class you choose.</p><p>Check from the school's timetable before making your decision.</p>
                </CardText>
              </Card>
            </div>
        
    
            <Paper style={paperStyle}>
                        <h1>Choose the venue for the class</h1>
          <div>
          <DropDownMenu value={this.state.value} onChange={this.handleChange}>
            <MenuItem value={1} primaryText="Lecture Theatre" />
            <MenuItem value={2} primaryText="Room A" />
            <MenuItem value={3} primaryText="Room B" />
            <MenuItem value={4} primaryText="Levine Hall" />
            <MenuItem value={3} primaryText="PB" />
          </DropDownMenu>
          </div>
          </Paper>
    
    
          
    
          <div>
           <RaisedButton label="Change Class Venue"/>
         </div>
         </div>
      );
     }
    }