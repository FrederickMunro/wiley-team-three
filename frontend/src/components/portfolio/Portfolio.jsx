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
  const [editStocks, setEditStocks] = useState([]);
  const [stocks, setStocks] = useState([]);
  const [labels, setLabels] = useState([]);
  const [data, setData] = useState([]);
  const maxStocksInChart = 20;

  const handleEditStock = (newStock) => {
    const stocksEdited = [...stocks];
    stocksEdited.forEach(stock => {
      if (stock.portfolioStockId === newStock.portfolioStockId) {
        return newStock;
      }
    })
    setEditStocks(stocksEdited);
  }

  useEffect(() => {
    axios.get(`${API_URL}/user-info?token=${BEARER_TOKEN}`)
    .then(res => {
      console.log('Successfully retrieved user information');
      setUserId(res.data.userId);
      setUsername(res.data.username);
    })
    .catch(err => {
      console.log('Unable to retrieve user information.', err);
    })

    const tempLabels = [];
    const tempData = [];
    stocks.forEach(stock => {
      tempLabels.push(stock.symbol);
      tempData.push(stock.quantity * stock.lastPrice);
    })
    setLabels(tempLabels.slice(0, maxStocksInChart));
    setData(tempData.slice(0, maxStocksInChart));
  }, [stocks]);
  

  useEffect(() => {
    if (userId) {
      axios.get(`${API_URL}/portfolio/view/${userId}`, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Successfully retrieved user stock portfolio.');
        const options = { month: 'short', day: 'numeric', year: 'numeric' };
        const tempStocks = res.data.map(stock => {
          const date = new Date(stock.purchaseDate);
          return {
            symbol: stock.stock.symbol,
            name: stock.stock.name,
            exchange: stock.stock.exchange,
            lastPrice: stock.stock.lastPrice,
            quantity: stock.quantityOwned,
            purchasePrice: stock.purchasePrice,
            purchaseDate: date.toLocaleDateString('en-US', options),
            portfolioStockId: stock.id
          }
        });
        tempStocks.sort((a, b) => (b.quantity * b.lastPrice) - (a.quantity * a.lastPrice));
        setStocks(tempStocks);
      })
      .catch(err => {
        console.log('Unable to retrieve user stock portfolio.', err);
      })
    }
  }, [userId, editStocks])

  return (
    <div className='portfolio-container white-background grey-color'>
      <h1 className='portfolio-title'>Your Portfolio</h1>
      <div className='portfolio-section grey-background white-color'>
        <div className='portfolio-section-container'>
          <div className='portfolio-section-info'>
            <h2 className='portfolio-section-title'>Stocks</h2>
            <StockTable allStocks={stocks} userId={userId} handleEditStock={handleEditStock} />
          </div>
          <div className='portfolio-section-chart'>
            <h2 className='portfolio-section-title'>Top Holdings</h2>
            <DonutChart labelSet={labels} dataSet={data} />
          </div>
        </div>
      </div>
      <div className='portfolio-section grey-background white-color'>
        <div className='portfolio-section-container'>
          <div className='portfolio-section-info'>
            <h2 className='portfolio-section-title'>Crypto</h2>
            <StockTable allStocks={stocks} handleEditStock={handleEditStock} />
          </div>
          <div className='portfolio-section-chart'>
            <h2 className='portfolio-section-title'>Top Holdings</h2>
            <DonutChart />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Portfolio;