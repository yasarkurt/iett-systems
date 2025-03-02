import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import GaragesPage from './pages/GaragesPage';
import BusesPage from './pages/BusesPage';
import HomePage from './pages/HomePage';
import './styles.css';

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/garages" element={<GaragesPage />} />
        <Route path="/buses" element={<BusesPage />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Router>
  );
};

export default App;