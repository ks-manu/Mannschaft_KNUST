import React,{Component} from 'react';
import PostDetails from './PostDetails';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import Post from './Post';
import Paper from 'material-ui/Paper';
import paperStyle from './PaperStyle';
import AddPost from './AddPost';
import { observer } from 'mobx-react';
import PostStore from './store/PostStore';

const style = {
  margin: 12,
};

 mobx-react.observer(['PostStore']);





export default class ViewPost extends Component{
  newPost=(e)=>{
    e.preventDefault();
   
     const posts=this.state.posts;
     const newId=posts[posts.length-1].id+1;
     this.setState({
       posts:posts.concat({id: newId, post:this.refs.message.getValue(), upvotes:0, downvotes:0})

       
     });
  
   console.log(this.refs.message.getValue());
       
   //this.refs.message.getValue=null;
     
   
  }
 
 
  componentWillMount(){
    this.setState({
      posts:PostDetails,
    })
  }
  
  
  render(){
  return(
    <div>      
      <Paper style={paperStyle}>
        <h2>New Post</h2>
        <form >
        
          
          <TextField className="PostHolder" multiLine={false} rows={6} fullWidth="true" rowsMax={10} floatingLabelText="Post" refs='message'/>
            
          <RaisedButton label="Submit" className="submitButton" style={style} onClick={this.newPost} />
          <RaisedButton label="Cancel" className="cancelButton" style={style}/> 
      
        </form>  
      </Paper> 
        {this.props.post.all.slice().map(info=>
        <Post key={info.id} {...info}/>
    
      )}
      
        
        
    
        
        
  
    </div>
        )
  }
}
