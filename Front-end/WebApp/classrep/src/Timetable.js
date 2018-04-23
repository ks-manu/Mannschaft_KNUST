import React,{Component} from 'react';
import DatePicker from 'material-ui/DatePicker';

function disableWeekends(date) {
  return date.getDay() === 0 || date.getDay() === 6;
}



export default class Timetable extends Component {
   
    constructor(){
        super();
        this.state = {
            date: ''
        }
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(e){
        e.preventDefault();
        console.log(this.state)
    }

    render(){
        return(
            <div>
                <h1>Select a new date for the class</h1>  
                <DatePicker hintText="Calendar" shouldDisableDate={disableWeekends} 
                onChange={e => this.setState({date: e.target.value})}/>
                
            </div>
        );
    }
}

