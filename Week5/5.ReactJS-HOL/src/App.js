import React from 'react';
import styles from './CohortDetails.module.css';

const cohorts = [
  {
    name: 'React Basics',
    status: 'ongoing',
    startDate: '10 Jul 2026',
    endDate: '20 Jul 2026',
    mentor: 'Anita',
  },
  {
    name: 'Cloud Foundations',
    status: 'completed',
    startDate: '01 Jun 2026',
    endDate: '30 Jun 2026',
    mentor: 'Ravi',
  },
];

function App() {
  return (
    <main>
      {cohorts.map((cohort) => (
        <section key={cohort.name} className={styles.box}>
          <h3 style={{ color: cohort.status === 'ongoing' ? 'green' : 'blue' }}>
            {cohort.name}
          </h3>
          <dl>
            <dt>Status</dt>
            <dd>{cohort.status}</dd>
            <dt>Start Date</dt>
            <dd>{cohort.startDate}</dd>
            <dt>End Date</dt>
            <dd>{cohort.endDate}</dd>
            <dt>Mentor</dt>
            <dd>{cohort.mentor}</dd>
          </dl>
        </section>
      ))}
    </main>
  );
}

export default App;
