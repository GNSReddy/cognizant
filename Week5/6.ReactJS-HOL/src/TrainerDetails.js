import React from 'react';
import { useParams } from 'react-router-dom';
import trainers from './TrainersMock';

function TrainerDetail() {
  const { id } = useParams();
  const trainer = trainers.find((item) => String(item.trainerId) === id);

  if (!trainer) {
    return <h2>Trainer not found</h2>;
  }

  return (
    <section>
      <h2>Trainer Details</h2>
      <p>Id: {trainer.trainerId}</p>
      <p>Name: {trainer.name}</p>
      <p>Email: {trainer.email}</p>
      <p>Phone: {trainer.phone}</p>
      <p>Technology: {trainer.technology}</p>
      <p>Skills: {trainer.skills}</p>
    </section>
  );
}

export default TrainerDetail;
