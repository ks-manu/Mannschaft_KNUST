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
//import AddPost from './AddPost';
import { Router, Route, Link } from 'react-router-dom'
import ViewPost from './ViewPost';
import Timetable from "./Timetable";
import './App.css';


const muiTheme = getMuiTheme({
  palette: {
    textColor: cyan500,
  },
  appBar: {
    height: 50,
  },
});



class MenuBar extends Component{
  
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
      <div id="MenuDiv">
        <AppBar className="AppBar"  
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
          <Link to='./viewPost'><MenuItem>Posts</MenuItem></Link>
          <MenuItem>Account</MenuItem>
          <Link to="./"><MenuItem>Logout</MenuItem></Link>
          
        </Drawer>
        
            
              
            
      </div>
    );

  }
}
export default MenuBar;
