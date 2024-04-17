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
    axios.get(API_URL + '/admin/stocks',  {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log('Get supported stocks successful');
      setSupportedStocks(res.data.content);
    })
    .catch(err => {
      console.error('Error fetching supported stocks:', err);
    })

    axios.get(API_URL + '/admin/allstocks', {
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
      setAllStocks(filteredData);
    })
    .catch(err => {
      console.error('Error fetching all stocks:', err);
    });
  }, []);

  return (
    <div className='admin-container white-background'>
      <AdminTable title={'Supported Stocks'} allStocks={supportedStocks} setAllStocks={setAllStocks} />
      <AdminTable title={'All Stocks'} allStocks={allStocks} setAllStocks={setSupportedStocks} />
    </div>
  );
}

export default Admin;