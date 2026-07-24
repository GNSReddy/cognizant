import React from 'react';

function ListofPlayers() {
  const players = [
    { name: 'Player1', score: 80 },
    { name: 'Player2', score: 65 },
    { name: 'Player3', score: 71 },
    { name: 'Player4', score: 55 },
    { name: 'Player5', score: 91 },
    { name: 'Player6', score: 69 },
    { name: 'Player7', score: 77 },
    { name: 'Player8', score: 68 },
    { name: 'Player9', score: 88 },
    { name: 'Player10', score: 73 },
    { name: 'Player11', score: 62 },
  ];

  const lowScorers = players.filter((player) => player.score < 70);

  return (
    <section>
      <h2>List of Players</h2>
      <ul>
        {players.map((player) => (
          <li key={player.name}>{player.name} - {player.score}</li>
        ))}
      </ul>
      <h3>Players below 70</h3>
      <ul>
        {lowScorers.map((player) => (
          <li key={player.name}>{player.name} - {player.score}</li>
        ))}
      </ul>
    </section>
  );
}

function IndianPlayers() {
  const players = ['Virat', 'Rohit', 'Rahul', 'Gill', 'Jadeja', 'Pant'];
  const [first, second, third, fourth, ...rest] = players;
  const oddTeamPlayers = [first, third, rest[0]];
  const evenTeamPlayers = [second, fourth, rest[1]];
  const T20players = ['Dhoni', 'Kohli'];
  const RanjiTrophyPlayers = ['Pujara', 'Iyer'];
  const mergedPlayers = [...T20players, ...RanjiTrophyPlayers];

  return (
    <section>
      <h2>Indian Players</h2>
      <p>Odd Team Players: {oddTeamPlayers.join(', ')}</p>
      <p>Even Team Players: {evenTeamPlayers.join(', ')}</p>
      <p>Merged Players: {mergedPlayers.join(', ')}</p>
    </section>
  );
}

function App() {
  const flag = true;
  let content;

  if (flag) {
    content = <ListofPlayers />;
  } else {
    content = <IndianPlayers />;
  }

  return content;
}

export default App;
