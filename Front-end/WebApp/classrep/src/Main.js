import React,{Component} from 'react';
import {cyan500} from 'material-ui/styles/colors';
import paperStyle from './PaperStyle';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import Login from './Login';

import MenuItem from 'material-ui/MenuItem';
import Drawer from 'material-ui/Drawer';
import { Toolbar, ToolbarTitle } from 'material-ui';
import Paper from 'material-ui/Paper';
import AddPost from './AddPost';
import { Router, Route, Link } from 'react-router-dom'
import ViewPost from './ViewPost';
import './App.css';


const muiTheme = getMuiTheme({
  palette: {
    textColor: cyan500,
  },
  appBar: {
    height: 50,
  },
});



class Main extends Component{
  
  constructor(props){
      super(props);
      this.state={
        "open":false,
        "show":null
      };
  }

  handleToggle=()=> this.setState({open:!this.state.open});

  
 

  render(){
    
    
    return(
      <div>
        <AppBar 
          title="Class Rep"
          onLeftIconButtonClick={this.handleToggle}  />
        <Drawer
          docked={false} 
          width={200}
          open={this.state.open}
          onRequestChange={(open)=> this.setState({open})}

        >

        <AppBar title="Menu"/>
          <Link to="./viewTimeTable"><MenuItem >Timetable</MenuItem></Link>
          <MenuItem>Posts</MenuItem>
          <MenuItem>Account</MenuItem>
          <Link to="./Login"><MenuItem>Logout</MenuItem></Link>
          
        </Drawer>
        <Paper style={paperStyle} >

          
          <h1>Welcome to Class Rep</h1>
          <ul>
            <Link to="/ViewPost"><li>ViewPost</li></Link>
            <Link to="/changeVenue"><li>Change Venue</li></Link>
            <Link to="/changeTime"><li>Change Time</li></Link>
            
          </ul>
          
        </Paper>
            
              
            
      </div>
    );

  }
}
export default Main;
