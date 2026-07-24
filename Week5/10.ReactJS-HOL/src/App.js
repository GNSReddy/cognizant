import React from 'react';

const office = {
  name: 'DBS Office',
  rent: 55000,
  address: 'Chennai',
  image: 'https://via.placeholder.com/320x180?text=Office+Space',
};

const offices = [
  { name: 'DBS Office', rent: 55000, address: 'Chennai' },
  { name: 'Skyline Office', rent: 75000, address: 'Bengaluru' },
  { name: 'Harbor Office', rent: 61000, address: 'Hyderabad' },
];

function App() {
  return (
    <main>
      <h1>Office Space, at Affordable Range</h1>
      <img src={office.image} alt="Office space" />
      <p>Name: {office.name}</p>
      <p>
        Rent: <span style={{ color: office.rent < 60000 ? 'red' : 'green' }}>{office.rent}</span>
      </p>
      <p>Address: {office.address}</p>
      <h2>Available Offices</h2>
      {offices.map((item) => (
        <section key={item.name}>
          <h3>{item.name}</h3>
          <p>
            Rent: <span style={{ color: item.rent < 60000 ? 'red' : 'green' }}>{item.rent}</span>
          </p>
          <p>Address: {item.address}</p>
        </section>
      ))}
    </main>
  );
}

export default App;
