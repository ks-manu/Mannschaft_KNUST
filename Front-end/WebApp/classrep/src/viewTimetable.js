import React,{Component} from 'react';

import Paper from 'material-ui/Paper';
import Timetable from "./Timetable";
import 'react-calendar-timeline/lib/Timeline.css'
import './App.css';
import TimeTable from './Timetable';
import MenuBar from './MenuBar'




const paperStyleTimetable={
    height: '85%',
    width:'90%',
    margin:'7%',
    
  }



class viewTimetable extends Component{
  


  
 

  render(){
    
    
    return(
      <div>
        <MenuBar/>
        <Paper style={paperStyleTimetable} >

          
          <Timetable/>
          
        </Paper>
            
              
            
      </div>
    );

  }
}
export default viewTimetable;
