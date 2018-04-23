import React,{Component} from 'react';
import Timetable from 'react-timetable-events';



export default class viewTimeTable extends Component {
 
    render(){
        return (
            <Timetable events={this.state.events}/>
        )
    }
}
