import React,{Component} from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import PostDetails from './PostDetails';
import Paper from 'material-ui/Paper';
import paperStyle from './PaperStyle';

const style = {
  margin: 12,
};


export default class AddPost extends Component {

  componentWillMount(){
    this.setState({
      posts:PostDetails,
    })
  }
  
  

  
 

 newPost=(e)=>{
     e.preventDefault();
    
      const posts=this.state.posts;
      const newId=posts[posts.length-1].id+1;
      this.setState({
        posts:posts.concat({id: newId,post:`Post ${newId}`,upvotes:0, downvotes:0})
      })

    
        console.log(posts[posts.length-1].id+1)

  
    
}

  render() {
    return(
      <div>        
        
         
      </div>
    )
  }
}