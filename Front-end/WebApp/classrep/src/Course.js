import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import paperStyle from './PaperStyle';


const Course = props =>
  <div>
    <Card style={paperStyle}>
      
            <CardTitle/>
            
            <CardText>
              Course Code:{props.CourseCode}
              <br/>
              CourseID:{props.CourseID}
            </CardText>
          </Card>
  </div>



  export default Course;