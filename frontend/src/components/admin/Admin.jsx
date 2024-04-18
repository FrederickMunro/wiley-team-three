import { useEffect, useState } from 'react';
import axios from 'axios';
import './Admin.css';
import AdminTable from './AdminTable';

const Admin = () => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const [allStocks, setAllStocks] = useState([]);
  const [supportedStocks, setSupportedStocks] = useState([]);

  useEffect(() => {
    axios.get(API_URL + '/stocks/get-all-db',  {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log('Get supported stocks successful', res);
      setSupportedStocks(res.data);
    })
    .catch(err => {
      console.error('Error fetching supported stocks:', err);
    })
  }, []);

  useEffect(() => {
    axios.get(API_URL + '/stocks/get-all-api', {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log('Get all stocks successful');
      const sortedData = res.data.sort((a, b) => a.symbol.localeCompare(b.symbol));
      const filteredData = sortedData.filter(stock => {
        return !supportedStocks.find(supportedStock => supportedStock.symbol === stock.symbol);
      });
      const againFilteredData = filteredData.filter(stock => /^[a-zA-Z]+$/.test(stock.symbol));
      setAllStocks(againFilteredData);
    })
    .catch(err => {
      console.error('Error fetching all stocks:', err);
    });
  }, [supportedStocks])

  return (
    <div className='admin-container white-background'>
      <AdminTable title={'Supported Stocks'} allStocks={supportedStocks} setSupportedStocks={setSupportedStocks} />
      <AdminTable title={'All Stocks'} allStocks={allStocks} setSupportedStocks={setSupportedStocks} />
    </div>
  );
}

export default Admin;