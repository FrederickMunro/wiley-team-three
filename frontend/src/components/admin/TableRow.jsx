import axios from 'axios';

import Minus from '../../assets/minus.svg';
import Plus from '../../assets/plus.svg';

import './Admin.css';

const TableRow = ({ stock, title, allStocks, setSupportedStocks, type }) => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  let svg = null;

  if (title === 'Supported Stocks' || title === 'Supported Crypto') {
    svg = Minus;
  } else {
    svg = Plus;
  }

  const handleClick = () => {
    if (title === 'Supported Stocks') {
      const stockToRemove = allStocks.filter(s=> s.symbol === stock.symbol)[0];
      axios.delete(`${API_URL}/stocks/${stockToRemove.id}`, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Successfully removed stock.');
        setSupportedStocks(allStocks.filter(s => s.symbol !== stockToRemove.symbol));
      })
      .catch(err => {
        console.log('Unable to remove stock', err);
      })
    } else if (title === 'All Stocks') {
      axios.post(`${API_URL}/stocks/?symbol=${stock.symbol}`, null, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Successfully added stock.');
        setSupportedStocks(prev => [...prev, res.data]);
      })
      .catch(err => {
        console.log('Unable to add stock', err)
      })
    } else if (title === 'Supported Crypto') {
      const stockToRemove = allStocks.filter(s=> s.id === stock.id)[0];
      axios.delete(`${API_URL}/crypto/${stockToRemove.id}`, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Successfully removed crypto.');
        const fullCrypto = allStocks.filter(c => (c.id !== stockToRemove.id))
        setSupportedStocks(fullCrypto);
      })
      .catch(err => {
        console.log('Unable to remove crypto', err);
      })
    } else if (title === 'All Crypto') {

    }
  }

  return (
    <tr key={stock.symbol}>
      {
        type == 'stock' &&
        <>
          <td className='admin-td'>{stock.symbol}</td>
          <td className='admin-td'>{stock.name}</td>
          <td className='admin-td'>{stock.exchange}</td>
        </>
      }
      {
        type == 'crypto' &&
        <>
          <td className='admin-td'>{stock.ticker}</td>
          <td className='admin-td'>{stock.currency}</td>
        </>
      }
      <td className='admin-add-remove-container'>
        <button className='admin-add-remove' onClick={() => handleClick()}>
          <img className='admin-add-remove-img' src={svg} />
        </button>
      </td>
    </tr>
  );
}

export default TableRow;