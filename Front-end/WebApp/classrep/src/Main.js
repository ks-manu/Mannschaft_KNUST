import React,{Component} from 'react';
//import paperStyle from './PaperStyle';
import Login from './Login';
import Paper from 'material-ui/Paper';
import AddPost from './AddPost';
import { Router, Route, Link } from 'react-router-dom'
import ViewPost from './ViewPost';
import Timetable from "./Timetable";
import './App.css';
import MenuBar from './MenuBar'

const paperStyleMain={
  circle: true,
  margin:'7%',
  textAlign:'center',
  backgroundColor:'#E0F7FA'
}


class Main extends Component{
  
  

  
 

  render(){
    
    
    return(
      <div>
        <MenuBar/>
        <Paper style={paperStyleMain} >

          
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
