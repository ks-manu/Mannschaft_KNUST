import React,{Component} from 'react';
import CourseDetails from './CourseDetails';
import Course from './Course';
import MenuBar from './MenuBar';
import './App.css';


export default class viewCourse extends Component{
    componentWillMount(){
        this.setState({
          Course:CourseDetails,
        })
      }


    render(){
        return(
            <div>
                <MenuBar/>
                {this.state.Course.map(info=>
                    <Course key={info.CourseID} {...info}/>
                )}


            </div>    
        )
    }
}