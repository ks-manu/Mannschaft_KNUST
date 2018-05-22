import React, {Component} from 'react';
import {DayPilotScheduler} from "daypilot-pro-react";




class Timetable extends Component {
    render() {
        return (

          
            <DayPilotScheduler


             
             cellWidth = {100}
             eventHeight = {100}
                 businessBeginsHour = {8}
             businessEndsHour = {18}
             showNonBusiness = {false}

                    resources = {[
                    {name: "Monday", id: "A"},
                    {name: "Tuesday", id: "B"},
                    {name: "Wednesday", id: "C"},
                    {name: "Thursday", id: "D"},
                    {name: "Friday", id: "E"}
                ]}

        
                 />
           
        );
    }
}

export default Timetable;
