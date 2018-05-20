import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import paperStyle from './PaperStyle';


const Post = props =>
  <div>
    <Card style={paperStyle}>
      
            <CardTitle/>
            
            <CardText>
              {props.Message}
              <br/>

              <br/>
              votable:{props.votable}    
              <br/>
              attachment:{props.attachment}
              <br/>
              time Sent:{props.Time_sent}
            </CardText>
          </Card>
  </div>



  export default Post;