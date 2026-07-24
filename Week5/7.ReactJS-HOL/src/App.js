import React from 'react';

class Cart {
  constructor(itemname, price) {
    this.itemname = itemname;
    this.price = price;
  }
}

class OnlineShopping extends React.Component {
  constructor(props) {
    super(props);
    this.items = [
      new Cart('Laptop', 65000),
      new Cart('Phone', 45000),
      new Cart('Headphones', 5000),
      new Cart('Keyboard', 3000),
      new Cart('Mouse', 1500),
    ];
  }

  render() {
    return (
      <section>
        <h2>Online Shopping Items</h2>
        <ul>
          {this.items.map((item) => (
            <li key={item.itemname}>
              {item.itemname} - {item.price}
            </li>
          ))}
        </ul>
      </section>
    );
  }
}

function App() {
  return <OnlineShopping />;
}

export default App;
