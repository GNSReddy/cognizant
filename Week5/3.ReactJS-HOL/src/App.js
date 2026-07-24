import React from 'react';
import CalculateScore from './Components/CalculateScore';
import './Stylesheets/mystyle.css';

function App() {
  return (
    <CalculateScore
      name="John"
      school="ABC Higher Secondary School"
      total={270}
      goal="A"
    />
  );
}

export default App;
