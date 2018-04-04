import React,{Component} from 'react';
import {cyan500} from 'material-ui/styles/colors';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import Login from './Login';
import SignUp from './SignUp';
import MenuItem from 'material-ui/MenuItem';
import Drawer from 'material-ui/Drawer';
import { Toolbar, ToolbarTitle } from 'material-ui';
import Paper from 'material-ui/Paper';
import AddAnnouncement from './AddAnnouncement';

// This replaces the textColor value on the palette
// and then update the keys for each component that depends on it.
// More on Colors: http://www.material-ui.com/#/customization/colors
const muiTheme = getMuiTheme({
  palette: {
    textColor: cyan500,
  },
  appBar: {
    height: 50,
  },
});

const paperStyle={
  height:'85%',
  width:'50%',
  margin:'7%',
  textAlign:'center',
  backgroundColor:'#E0F7FA'
}

// MuiThemeProvider takes the theme as a property and passed it down the hierarchy.
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
  <MuiThemeProvider muiTheme={muiTheme}>
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
          <MenuItem >Timetable</MenuItem>
          <MenuItem>Announcements</MenuItem>
          <MenuItem>Account</MenuItem>
          <MenuItem>Logout</MenuItem>
        </Drawer>
        <Paper style={paperStyle} >

          <AddAnnouncement/>
          
        </Paper>
            
        
        
  </MuiThemeProvider>
    );

  }
}
export default Main;
