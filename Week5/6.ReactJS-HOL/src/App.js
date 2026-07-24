import React from 'react';
import { BrowserRouter, Link, Navigate, Route, Routes } from 'react-router-dom';
import Home from './Home';
import TrainersList from './TrainersList';
import TrainerDetail from './TrainerDetails';

function App() {
  return (
    <BrowserRouter>
      <nav>
        <Link to="/">Home</Link> | <Link to="/trainers">Trainers</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Navigate to="/home" replace />} />
        <Route path="/home" element={<Home />} />
        <Route path="/trainers" element={<TrainersList />} />
        <Route path="/trainers/:id" element={<TrainerDetail />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
