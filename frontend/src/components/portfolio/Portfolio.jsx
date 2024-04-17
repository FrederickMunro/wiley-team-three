import { useState, useEffect } from 'react';
import axios from 'axios';

import './Portfolio.css';

const Portfolio = () => {
  const API_URL = import.meta.env.VITE_API_URL;

  const [user, setUser] = useState(null);

  useEffect(() => {
    axios.get(API_URL + '/user-info?token=' + document.cookie.split('=')[1])
          .then(res => {
            setUser({
              username: res.data.username,
              userId: res.data.userId,
              role: res.data.role
            });
            console.log(res.data);
          })
          .catch(err => {
            console.log('Unable to retrieve account information' , err);
          })
  }, []);

  return (
    <div className='portfolio-container'>
      <h1>{user.username}</h1>
    </div>
  );
}

export default Portfolio;