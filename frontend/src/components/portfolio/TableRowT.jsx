import axios from 'axios';

import '../admin/Admin.css';

const TableRowT = ({ stock }) => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  return (
    <tr key={stock.symbol}>
      <td className='admin-td'>{stock.symbol}</td>
      <td className='admin-td'>{stock.name}</td>
      <td className='admin-td'>{stock.exchange}</td>
    </tr>
  );
}

export default TableRowT;