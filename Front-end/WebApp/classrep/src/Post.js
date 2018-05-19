import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import paperStyle from './PaperStyle';


const Post = props =>
  <div>
    <Card style={paperStyle}>
      
            <CardTitle/>
            
            <CardText>
              {props.post}
              <br/>
              <br/>
              votable:{props.votable}     attachment:{props.attachment}
            </CardText>
          </Card>
  </div>



  export default Post;