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
              Up:{props.upvotes}     Down:{props.downvotes}
            </CardText>
          </Card>
  </div>

  export default Post;