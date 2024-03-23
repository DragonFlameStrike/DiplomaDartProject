import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import './App.css';
import Header from "./component/header/Header";
import Signals from "./component/signals/Signals";
import Events from "./component/events/Events";
import Stations from "./component/stations/Stations";

function App() {
    return (
        <Router>
            <div>
                <div>
                    <Header/>
                </div>
                <div style={{margin: '20px'}}>
                    <Routes>
                        <Route path="/" element={
                            <div>
                                <Signals/>
                            </div>
                        }/>
                        <Route path="/config/" element={
                            <div>
                                <Events/>
                            </div>
                        }/>
                        <Route path="/stations/" element={
                            <div>
                                <Stations/>
                            </div>
                        }/>
                        <Route path="/events/" element={
                            <div>
                                <Events/>
                            </div>
                        }/>
                    </Routes>
                </div>
            </div>
        </Router>

    );
}

export default App;
