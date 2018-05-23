import React,{Component} from 'react';
import paperStyle from './PaperStyle';
import Paper from 'material-ui/Paper';
import { Router, Route, Link, NavLink } from 'react-router-dom'
import './App.css';
import MenuBar from './MenuBar'
import {Card, CardTitle, CardText,CardMedia} from 'material-ui/Card';
import GridList from 'material-ui/GridList';


var cardStyle = {
    display: 'block',
    width: '30vw',
    transitionDuration: '0.3s',
    height: '20vw'
}

class Main extends Component{
  
  

  
 

  render(){
    
    
    return(
      <div>
        <MenuBar/>
        <Paper style={paperStyle} >
          <h1>Welcome to Class Rep</h1> 
        </Paper>

<GridList cols={3} cellHeight={'auto'}>
      
        <Card style={cardStyle}>
           
            
             <CardText>
              <a href="/ViewPost"> <CardMedia><img  src="https://images.pexels.com/photos/265076/pexels-photo-265076.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260" alt=""/></CardMedia></a>

             </CardText>
        <Paper style={paperStyle} >
          <Link to="/ViewPost"><li><h1>View Posts</h1> </li></Link>
        </Paper>
           </Card>
            
        
            

            <Card style={cardStyle}>
            
             <CardText>
<a href="/changeTime"><CardMedia><img  src="https://images.pexels.com/photos/745365/pexels-photo-745365.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260" alt=""/></CardMedia></a>
             </CardText>
              <Paper style={paperStyle} >
          <Link to="/changeTime"><li><h1>Change Time</h1> </li></Link>
        </Paper>
           </Card>


            <Card style={cardStyle}>
            
             <CardText>
              <a href="/changeVenue"> <CardMedia><img src="https://images.pexels.com/photos/256395/pexels-photo-256395.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260" alt=""/></CardMedia></a>
             </CardText>
             <Paper style={paperStyle} >
          <Link to="/changeVenue"><li><h1>Change Venue</h1> </li></Link>
        </Paper>
           </Card>
            
      </GridList>
            
      </div>
    );

  }
}
export default Main;
