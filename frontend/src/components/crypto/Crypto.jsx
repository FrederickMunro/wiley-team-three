import { useEffect, useState } from 'react';
import './Crypto.css';
import axios from 'axios';
import CryptoContainer from './CryptoContainer';

const Crypto = () => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const [crypto, setCrypto] = useState([]);

  useEffect(() => {
    axios.get(`${API_URL}/crypto/all`,  {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log('Get supported crypto successful.');
      setCrypto(res.data);
    })
    .catch(err => {
      console.error('Error fetching supported crypto:', err);
    })
  }, [])

  return (
    <div className='crypto-container white-background'>
      {
        crypto && crypto.map(item => {
          return <CryptoContainer key={item.id} crypto={item} />
        })
      }
    </div>
  );
}

export default Crypto;