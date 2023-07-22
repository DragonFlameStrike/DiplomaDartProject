
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Header from "./component/header/Header";
import Signals from "./component/signals/Signals";
import Events from "./component/events/Events";

function App() {
  return (
      <Router>
          <div className='app'>
              <nav>
                  <Header/>
              </nav>
              <div style={{ margin: '65px' }}>
                  <Routes>
                      <Route path="/" element={
                          <div>
                              <Signals/>
                          </div>
                      } />
                      <Route path="/config/" element={
                          <div>
                              <Events/>
                          </div>
                      } />
                  </Routes>
              </div>
          </div>
      </Router>

  );
}

export default App;
