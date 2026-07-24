import React from 'react';
import Post from './Post';

class Posts extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      posts: [new Post(1, 'Loading posts...', 'Please wait while we fetch the latest content.')],
      error: null,
    };
  }

  componentDidMount() {
    this.loadPosts();
  }

  loadPosts() {
    fetch('https://jsonplaceholder.typicode.com/posts')
      .then((response) => response.json())
      .then((data) => {
        this.setState({
          posts: data.slice(0, 5).map((item) => new Post(item.id, item.title, item.body)),
        });
      })
      .catch((error) => {
        this.setState({ error });
      });
  }

  componentDidCatch(error) {
    alert(error.message);
  }

  render() {
    if (this.state.error) {
      return <h2>Unable to load posts</h2>;
    }

    return (
      <section className="posts">
        {this.state.posts.map((post) => (
          <article key={post.id}>
            <h2>{post.title}</h2>
            <p>{post.body}</p>
          </article>
        ))}
      </section>
    );
  }
}

export default Posts;
