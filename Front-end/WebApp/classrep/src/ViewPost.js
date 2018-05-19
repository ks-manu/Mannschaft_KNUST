import React,{Component} from 'react';
import PostDetails from './PostDetails';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import Post from './Post';
import Paper from 'material-ui/Paper';
import paperStyle from './PaperStyle';
import MenuBar from './MenuBar';

const style = {
  margin: 12,
};







export default class ViewPost extends Component{
  newPost=(e)=>{
    e.preventDefault();
   
     const posts=this.state.posts;
     const newId=posts[posts.length-1].id+1;
     this.setState({
      posts:posts.concat({id: newId, post:document.getElementById('post').value,votable:0,attachment:0})

      
    });
 
console.log(document.getElementById('post').value);
   
     
   
  }
 
 
  componentWillMount(){
    this.setState({
      posts:PostDetails,
    })
  }
  
  
  render(){
  return(
    <div>      
      <MenuBar/>
      <Paper style={paperStyle}>
        <h2>New Post</h2>
        <form >
        
          
          <TextField className="PostHolder" multiLine={false} rows={6} 
          fullWidth="true" rowsMax={10} floatingLabelText="Post" 
          id='post'/>
            
          <RaisedButton label="Submit" className="submitButton" style={style} onClick={this.newPost} />
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