import React, { Component } from 'react';
import {cyan500} from 'material-ui/styles/colors';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
//import Button from 'antd/lib/button';
import './App.css';
import Main from './Main';
import PageRouter from './PageRoutes';

const muiTheme = getMuiTheme({
  palette: {
    textColor: cyan500,
  },
  appBar: {
    height: 50,
  },
});

export default class App extends Component {
  render() {
    return (
      <MuiThemeProvider muiTheme={muiTheme}>
       <PageRouter/>
      </MuiThemeProvider>
      
    );
  }
}