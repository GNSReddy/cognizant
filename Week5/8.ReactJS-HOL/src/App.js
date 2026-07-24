import React from 'react';

class CountPeople extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      entrycount: 0,
      exitcount: 0,
    };
  }

  UpdateEntry = () => {
    this.setState((prevState) => ({ entrycount: prevState.entrycount + 1 }));
  };

  UpdateExit = () => {
    this.setState((prevState) => ({ exitcount: prevState.exitcount + 1 }));
  };

  render() {
    return (
      <section>
        <h2>Mall Entry and Exit</h2>
        <p>People entered: {this.state.entrycount}</p>
        <p>People exited: {this.state.exitcount}</p>
        <button onClick={this.UpdateEntry}>Login</button>
        <button onClick={this.UpdateExit}>Exit</button>
      </section>
    );
  }
}

function App() {
  return <CountPeople />;
}

export default App;
