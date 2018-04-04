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

export default class AddAnnouncement extends Component {

  constructor(props) {
    super(props);
    this.state = {
      value: 3,
    };
  }

  handleChange = (event, index, value) => this.setState({value});

  render() {
    return (
    <div>        
        <Toolbar>
            <ToolbarGroup firstChild={false}>
            
            </ToolbarGroup>
            <ToolbarGroup>
            <ToolbarTitle text="Add Announcement" />
            <FontIcon className="muidocs-icon-custom-sort" />
            <ToolbarSeparator />
            <RaisedButton label="Create Announcement" primary={true} />
            <IconButton><NavigationClose /></IconButton>
            
            </ToolbarGroup>
        </Toolbar>

            <TextField className="Description" maxLength="50" fullWidth="true" floatingLabelText="Description" />
            <br />
            <TextField className="Message" multiLine={true} rows={6} fullWidth="true" rowsMax={10} floatingLabelText="Message" />
            <br />
    </div>        
    );
  }
}