import React from 'react';
import { Link } from 'react-router-dom';
import trainers from './TrainersMock';

function TrainersList() {
  return (
    <section>
      <h2>Trainers List</h2>
      <ul>
        {trainers.map((trainer) => (
          <li key={trainer.trainerId}>
            <Link to={`/trainers/${trainer.trainerId}`}>{trainer.name}</Link>
          </li>
        ))}
      </ul>
    </section>
  );
}

export default TrainersList;
