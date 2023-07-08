
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import {useState} from "react";
import './App.css';
import Header from "./component/header/Header";
import Signals from "./component/signals/Signals";

function App() {
  return (
      <Router>
        <div className="main_container">
          <nav>
            <Header/>
          </nav>
          <Routes>
            <Route path="/" element={
              <div className="main_div">
                  <Signals/>
              </div>
            }>
            </Route>
          </Routes>
        </div>
      </Router>
  );
}

export default App;
