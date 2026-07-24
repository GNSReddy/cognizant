import React from 'react';

function CalculateScore(props) {
  const average = props.total / 3;

  return (
    <section className="score-card">
      <h2>Student Score Details</h2>
      <p>Name: {props.name}</p>
      <p>School: {props.school}</p>
      <p>Total: {props.total}</p>
      <p>Goal: {props.goal}</p>
      <p>Average Score: {average.toFixed(2)}</p>
    </section>
  );
}

export default CalculateScore;
