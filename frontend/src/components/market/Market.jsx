import { useEffect, useState } from 'react';
import axios from 'axios';

import './Market.css';
import StockContainer from './StockContainer';

const Market = () => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const [stocks, setStocks] = useState(null);
  const [user, setUser] = useState();

  useEffect(() => {
    axios.get(`${API_URL}/user-info?token=${BEARER_TOKEN}`)
    .then(res => {
      console.log('Successfully retrieved user information');
      setUser({
        username: res.data.username,
        userId: res.data.userId,
        role: res.data.role,
      })
    })
    .catch(err => {
      console.log('Unable to retrieve user information.', err);
    })
  }, [])

  useEffect(() => {
    if (user) {
      if (user.role === 'TRADER') {
        axios.get(`${API_URL}/trader/stocks/get-all-db`, {
          headers: {
            Authorization: `Bearer ${BEARER_TOKEN}`
          }
        })
        .then(res => {
          setStocks(res.data);
          console.log('Successfully retrieved supported stocks.', res.data);
        })
        .catch(err => {
          console.log('Unable to retrieve supported stocks.', err);
        })
      } else if (user.role === 'ANALYST') {

      }
    }
  }, [user])

  return (
    <div className='market-container white-background grey-color'>
      {
        stocks && stocks.map(stock => {
          return <StockContainer key={stock.id} stock={stock} />
        })
      }
    </div>
  );
}

export default Market;