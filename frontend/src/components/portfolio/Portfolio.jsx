import { useState, useEffect } from 'react';
import axios from 'axios';

import PieChart from '../charts/PieChart';
import DonutChart from '../charts/DonutChart';
import StockTable from './StockTable';

import './Portfolio.css';

const Portfolio = () => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const [username, setUsername] = useState();
  const [userId, setUserId] = useState();

  const [stocks, setStocks] = useState([]);

  useEffect(() => {
    axios.get(API_URL + '/admin/stocks',  {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log('Get stocks successful');
      setStocks(res.data.content);
    })
    .catch(err => {
      console.error('Error fetching stocks:', err);
    })

    axios.get(`${API_URL}/user-info?token=${BEARER_TOKEN}`)
    .then(res => {
      console.log('Successfully retrieved user information');
      setUserId(res.data.userId);
      setUsername(res.data.username);
    })
    .catch(err => {
      console.log('Unable to retrieve user information.', err);
    })
  }, []);
  

  useEffect(() => {
    axios.get(`${API_URL}/portfolio/view/${userId}`)
    .then(res => {
      console.log(res.data)
    })
    .catch(err => {

    })
  }, [userId])

  return (
    <div className='portfolio-container white-background grey-color'>
      <h1 className='portfolio-title'>Your Portfolio</h1>
      <div className='portfolio-section grey-background white-color'>
        <div className='portfolio-section-container'>
          <div className='portfolio-section-info'>
            <h2 className='portfolio-section-title'>Stocks</h2>
            <StockTable allStocks={stocks} setSupportedStocks={setStocks} />
          </div>
          <div className='portfolio-section-chart'>
            <DonutChart />
          </div>
        </div>
      </div>
      <div className='portfolio-section grey-background white-color'>
        <div className='portfolio-section-container'>
          <div className='portfolio-section-info'>
            <h2 className='portfolio-section-title'>Crypto</h2>
            <StockTable allStocks={stocks} setSupportedStocks={setStocks} />
          </div>
          <div className='portfolio-section-chart'>
            <DonutChart />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Portfolio;