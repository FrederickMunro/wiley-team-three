import axios from 'axios';

import Minus from '../../assets/minus.svg';
import Plus from '../../assets/plus.svg';

import './Admin.css';

const TableRow = ({ stock, title, allStocks, setSupportedStocks }) => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  let svg = null;

  if (title === 'Supported Stocks') {
    svg = Minus;
  } else {
    svg = Plus;
  }

  const handleClick = () => {
    if (title === 'Supported Stocks') {
      const stockToRemove = allStocks.filter(s=> s.symbol === stock.symbol)[0];
      axios.delete(`${API_URL}/admin/stocks/${stockToRemove.id}`, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Successfully removed:', stockToRemove);
        setSupportedStocks(allStocks.filter(s => s.symbol !== stockToRemove.symbol));
      })
      .catch(err => {
        console.log('Unable to remove stock', err);
      })
    } else {
      axios.post(`${API_URL}/admin/stocks?symbol=${stock.symbol}`, null, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Successfully added:', res.data);
        setSupportedStocks(prev => [...prev, res.data]);
      })
      .catch(err => {
        console.log('Unable to remove stock', err)
      })
    }
  }

  return (
    <tr key={stock.symbol}>
      <td className='admin-td'>{stock.symbol}</td>
      <td className='admin-td'>{stock.name}</td>
      <td className='admin-td'>{stock.exchange}</td>
      <td className='admin-add-remove-container'>
        <button className='admin-add-remove' onClick={() => handleClick()}>
          <img className='admin-add-remove-img' src={svg} />
        </button>
      </td>
    </tr>
  );
}

export default TableRow;