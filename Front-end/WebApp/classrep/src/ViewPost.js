import React,{Component} from 'react';
import PostDetails from './PostDetails';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import Post from './Post';
import Paper from 'material-ui/Paper';
import paperStyle from './PaperStyle';
import AddPost from './AddPost';
// import  {observer}  from 'mobx-react';


const style = {
  margin: 12,
};







export default class ViewPost extends Component{
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
       posts:posts.concat({id: newId, post:document.getElementById('post').value, upvotes:0, downvotes:0})

       
     });
  
   console.log(document.getElementById('post').value);
       
   //this.refs.message.getValue=null;
     
   
  }
 
 
  
  
  
  render(){
  return(
    <div>      
      <Paper style={paperStyle}>
        <h2>New Post</h2>
        <form >
        
          
          <TextField className="PostHolder" multiLine={false} rows={6} fullWidth="true" 
          onChange={this.newPost} rowsMax={10} floatingLabelText="Post" 
          id="post"/>
            
          <RaisedButton label="Submit" className="submitButton" style={style}  />
          <RaisedButton label="Cancel" className="cancelButton" style={style}/> 
      
        </form>  
      </Paper> 
        {this.state.posts.map(info=>
        <Post key={info.id} {...info}/>
    
      )}
      
        
        
    
        
        
  
    </div>
        )
  }
}
