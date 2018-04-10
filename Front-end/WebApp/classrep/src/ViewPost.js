import React,{Component} from 'react';
import {Card, CardTitle, CardText} from 'material-ui/Card';


export default class ViewPost extends Component{
  render(){
      return(
        <div>  
          <Card>
            <CardTitle title="Post" subtitle="Post Description" />
            
            <CardText>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit.
              Donec mattis pretium massa. Aliquam erat volutpat. Nulla facilisi.
              Donec vulputate interdum sollicitudin. Nunc lacinia auctor quam sed pellentesque.
              Aliquam dui mauris, mattis quis lacus id, pellentesque lobortis odio.
            </CardText>
          </Card>
        </div>
        );
  }
}
