

import '../admin/Admin.css';
import './Portfolio.css';

import Edit from '../../assets/edit.svg';
import { useState } from 'react';
import EditModal from './EditModal';

const TableRowT = ({ stock, total, userId, handleEditStock, handleRemoveStock }) => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleEdit = () => {
    setIsModalOpen(true);
  }

  const handleCloseModal = () => {
    setIsModalOpen(false);
  }

  return (
    <>
      <tr key={stock.symbol}>
        <td className='admin-td'>{stock.symbol}</td>
        {
          stock.exchange ? (
            <td className='admin-td'>{stock.name}</td>
          ) : (
            <td className='admin-td'>{stock.currency}</td>
          )
        }
        <td className='admin-td'>{stock.quantity}</td>
        <td className='admin-td no-small'>{stock.purchaseDate}</td>
        <td className='admin-td no-small'>{`$${stock.purchasePrice.toFixed(2)}`}</td>
        <td className='admin-td no-small'>{`$${stock.lastPrice.toFixed(2)}`}</td>
        <td className='admin-td'>{`$${(stock.lastPrice * stock.quantity).toFixed(2)}`}</td>
        <td className='admin-td'>{`${((stock.lastPrice * stock.quantity / total) * 100).toFixed(2)}%`}</td>
        <td className='admin-td'>
          <button className='portfolio-edit'>
            <img src={Edit} className='portfolio-edit-img' onClick={() => handleEdit()}/>
          </button>
        </td>
      </tr>
      <EditModal
        stock={stock}
        isOpen={isModalOpen}
        handleClose={handleCloseModal}
        userId={userId}
        handleEditStock={handleEditStock}
        handleRemoveStock={handleRemoveStock}
      />
    </>
  );
}

export default TableRowT;