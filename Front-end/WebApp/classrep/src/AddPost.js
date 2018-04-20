import React,{Component} from 'react';
import IconMenu from 'material-ui/IconMenu';
import IconButton from 'material-ui/IconButton';
import FontIcon from 'material-ui/FontIcon';
import NavigationExpandMoreIcon from 'material-ui/svg-icons/navigation/expand-more';
import MenuItem from 'material-ui/MenuItem';
import DropDownMenu from 'material-ui/DropDownMenu';
import RaisedButton from 'material-ui/RaisedButton';
import {Toolbar, ToolbarGroup, ToolbarSeparator, ToolbarTitle} from 'material-ui/Toolbar';
import NavigationClose from 'material-ui/svg-icons/navigation/close';
import TextField from 'material-ui/TextField';
import { Router, Route, Link } from 'react-router-dom'

export default class AddPost extends Component {

  constructor(props) {
    super(props);
    this.state = {
      description: '',
      message: ''
    };
    this.onSubmit = this.onSubmit.bind(this);
  }

  handleChange = (event, index, value) => this.setState({value});

  onSubmit(e){
    e.preventDefault();
    console.log(this.state)
}

  render() {
    return (
    <div>        
        <Toolbar>
            <ToolbarGroup firstChild={false}>
            
            </ToolbarGroup>
            <ToolbarGroup>
            <ToolbarTitle text="Add Post" />
            <FontIcon className="muidocs-icon-custom-sort" />
            <ToolbarSeparator />
            <RaisedButton label="Create Post" primary={true} onClick={this.onSubmit}/>
            
              <IconButton><Link to="/addPost"><NavigationClose /></Link></IconButton>
            
            </ToolbarGroup>
        </Toolbar>

            <TextField className="Description" maxLength="50" fullWidth="true" floatingLabelText="Description" onChange={e => this.setState({description: e.target.value})} />
            <br />
            <TextField className="Message" multiLine={true} rows={6} fullWidth="true" rowsMax={10} floatingLabelText="Message" onChange={e => this.setState({message: e.target.value})}/>
            <br />
    </div>        
    );
  }
}