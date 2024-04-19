import { useEffect, useState } from 'react';
import axios from 'axios';

import './Market.css';
import StockContainer from './StockContainer';
import CryptoContainer from './CryptoContainer';

const Market = () => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const [selectedOption, setSelectedOption] = useState('stocks'); // Default to stocks
  const [data, setData] = useState(null);
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
        let endpoint = selectedOption === 'stocks' ? 'trader/stocks/get-all-db' : 'crypto/all';
        axios.get(`${API_URL}/${endpoint}`, {
          headers: {
            Authorization: `Bearer ${BEARER_TOKEN}`
          }
        })
        .then(res => {
          setData(res.data);
          console.log(`Successfully retrieved supported ${selectedOption}.`, res.data);
        })
        .catch(err => {
          console.log(`Unable to retrieve supported ${selectedOption}.`, err);
        })
      } else if (user.role === 'ANALYST') {
        // Implement for analyst role if needed
      }
    }
  }, [user, selectedOption])

  const handleOptionChange = (option) => {
    setSelectedOption(option);
  };

  return (
    <div className='market-container white-background'>
      <div className='dropdown'>
        <select className='market-dropdown white-background grey-color' value={selectedOption} onChange={(e) => handleOptionChange(e.target.value)}>
          <option value='stocks'>Stocks</option>
          <option value='crypto'>Crypto</option>
        </select>
      </div>
      {
        selectedOption.toLowerCase() === 'stocks' ? (
          data && data.map(item => {
            return <StockContainer key={item.id} stock={item} userId={user.userId} />
          })
        ) : (
          data && data.map(item => {
            return <CryptoContainer key={item.id} crypto={item} userId={user.userId} />
          })
        )
      }
    </div>
  );
}

export default Market;