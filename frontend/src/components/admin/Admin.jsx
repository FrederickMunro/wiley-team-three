import { useEffect, useState } from 'react';
import axios from 'axios';
import './Admin.css';
import AdminTable from './AdminTable';

const Admin = () => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const [allStocks, setAllStocks] = useState([]);
  const [supportedStocks, setSupportedStocks] = useState([]);
  const [allCrypto, setAllCrypto] = useState([]);
  const [supportedCrypto, setSupportedCrypto] = useState([]);
  const [selectedOption, setSelectedOption] = useState('stocks');
  const [addCrypto, setAddCrypto] = useState({
    ticker: '',
    currency: '',
  });

  const handleOptionChange = (option) => {
    setSelectedOption(option);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setAddCrypto(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleAddCrypto = () => {
    axios.post(`${API_URL}/crypto/?ticker=${addCrypto.ticker}usd`)
    .then(res => {
      console.log('Successfully added crypto.')
      setSupportedCrypto(prev => [...prev, {
        ticker: res.data.baseCurrency.toUpperCase(),
        currency: res.data.quoteCurrency.toUpperCase(),
        id: res.data.id,
        lastPrice: res.data.lastPrice,
        fullTicker: res.data.ticker
      }])
    })
    .catch(err => {
      console.log('Unable to add crypto.', res);
    })
  };

  useEffect(() => {
    axios.get(API_URL + '/stocks/get-all-db',  {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log('Get supported stocks successful.');
      setSupportedStocks(res.data);
    })
    .catch(err => {
      console.error('Error fetching supported stocks:', err);
    })

    axios.get(API_URL + '/crypto/all',  {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log('Get supported crypto successful.');
      const editCrypto = res.data.map(c => {
        return {
          ticker: c.baseCurrency.toUpperCase(),
          currency: c.quoteCurrency.toUpperCase(),
          id: c.id,
          lastPrice: c.lastPrice,
          fullTicker: c.ticker
        }
      });
      setSupportedCrypto(editCrypto);
    })
    .catch(err => {
      console.error('Error fetching supported crypto:', err);
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

  useEffect(() => {
    // axios.get(API_URL + '/stocks/get-all-api', {
    //   headers: {
    //     Authorization: `Bearer ${BEARER_TOKEN}`
    //   }
    // })
    // .then(res => {
    //   console.log('Get all stocks successful');
    //   const sortedData = res.data.sort((a, b) => a.symbol.localeCompare(b.symbol));
    //   const filteredData = sortedData.filter(stock => {
    //     return !supportedStocks.find(supportedStock => supportedStock.symbol === stock.symbol);
    //   });
    //   const againFilteredData = filteredData.filter(stock => /^[a-zA-Z]+$/.test(stock.symbol));
    //   setAllStocks(againFilteredData);
    // })
    // .catch(err => {
    //   console.error('Error fetching all stocks:', err);
    // });
  }, [supportedCrypto])

  return (
    <>
      <div className='adropdown white-background grey-color'>
        <select className='admin-dropdown white-background grey-color' value={selectedOption} onChange={(e) => handleOptionChange(e.target.value)}>
          <option value='stocks'>Stocks</option>
          <option value='crypto'>Crypto</option>
        </select>
      </div>
      {
        selectedOption === 'stocks' ? (
          <div className='admin-container white-background'>
            <AdminTable title={'Supported Stocks'} allStocks={supportedStocks} setSupportedStocks={setSupportedStocks} type='stock' />
            <AdminTable title={'All Stocks'} allStocks={allStocks} setSupportedStocks={setSupportedStocks} type='stock' />
          </div>
        ) : (
          <div className='admin-container white-background'>
            <AdminTable title={'Supported Crypto'} allStocks={supportedCrypto} setSupportedStocks={setSupportedCrypto} type='crypto' />
            <div className='crypto-add-container grey-background'>
              <h2  className='admin-table-title white-color'>Add a cryptocurrency</h2>
              <div className='white-color'>
                <div className='input-cont'>
                  <label className='admin-crypto-add-label'>Ticker</label>
                  <input
                    type="text"
                    name="ticker"
                    value={addCrypto.ticker}
                    onChange={handleInputChange}
                    className='admin-crypto-input white-background grey-color modal-input'
                    placeholder="Ticker"
                  />
                </div>
              </div>
              <button className='admin-crypto-add-button white-background grey-color' onClick={() => handleAddCrypto()}>Add</button>
            </div>
          </div>
        )
      }
    </>
  );
}

export default Admin;