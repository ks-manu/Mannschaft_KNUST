import React from "react";
import DatePicker from 'material-ui/DatePicker';
import TimePicker from 'material-ui/TimePicker';
import DatePickerDialog from 'material-ui/DatePicker/DatePickerDialog';
import TimePickerDialog from 'material-ui/TimePicker/TimePickerDialog';


export default class ChangeTime extends React.Component{
  constructor(props) {
    super(props);
    this.state = {
      value: new Date()
    }
  }  
  
  handleChange = (event, value) => {
    this.setState({value});
  }
    
  render(){
    return (


     
  
    


      <TimePicker
  value={new Date()} // picker value moment/string/number/js Date
  format='MMM DD, YYYY hh:mm A'
  timePickerDelay={150}
  returnMomentDate={false} // if true will return moment object
  className='datetime-container'
  textFieldClassName='datetime-input'
  name='picker' // form value name
  datePickerMode='portrait' // or landscape
  openToYearSelection={false} 
  disableYearSelection={false}
  hideCalendarDate={false}
  firstDayOfWeek={1}
  minutesStep={1}
  showCurrentDateByDefault={false}
 // clearIcon={<ClearIcon />} // set null to not render nothing
  // available callbacks
  onChange={() => {}}
  onTimePickerShow={() => {}}
  onDatePickerShow={() => {}}
  onDateSelected={() => {}}
  onTimeSelected={() => {}}
  shouldDisableDate={() => {}}
  DatePicker={DatePickerDialog}
  TimePicker={TimePickerDialog}
  // styles
  clearIconStyle={{ }}
  textFieldStyle={{ }}
  style={{ }} // root
  timePickerBodyStyle={{ }}
/>
    );
  }
}