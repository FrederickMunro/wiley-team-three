import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';

const EditModal = ({ isOpen, handleClose, stock, userId }) => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];
  const [editedStock, setEditedStock] = useState(stock); // Initialize edited stock state with current stock
  const modalRef = useRef(null); // Reference to modal content element

  // Close modal when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        handleClose();
      }
    };

    if (isOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isOpen, handleClose]);

  // Update the edited stock state when input values change
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setEditedStock({ ...editedStock, [name]: value });
  };

  // Save edited stock
  const handleSave = () => {
    axios.put(`${API_URL}/portfolio/update`, {
      userId: userId,
      symbol: editedStock.symbol,
      quantity: editedStock.quantity,
      purchasePrice: editedStock.purchasePrice
    }, {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(() => {
      handleClose();
    })
    .catch(error => {
      console.error('Error updating stock:', error);
    });
  };

  const handleDelete = () => {
    axios.delete(`${API_URL}/portfolio/delete/${stock.portfolioStockId}`, {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log(res);
    })
    .catch(err => {
      console.log('Unable to delete entry.', err);
    })
  }

  return (
    isOpen &&
    <div className="modal-overlay">
      <div ref={modalRef} className="modal-content grey-color">
        <span className="close" onClick={handleClose}>&times;</span>
        <h2>Edit Stock</h2>
        <h3>{stock.symbol}</h3>
        <p>{stock.name}</p>
        <div className='input-div'>
          <label htmlFor="quantity">Quantity:</label>
          <input
            type="number"
            id="quantity"
            name="quantity"
            value={editedStock.quantity}
            onChange={handleInputChange}
            className='white-background grey-color modal-input'
          />
        </div>
        <div className='input-div'>
          <label htmlFor="purchasePrice">Purchase Price:</label>
          <input
            type="number"
            id="purchasePrice"
            name="purchasePrice"
            value={editedStock.purchasePrice}
            onChange={handleInputChange}
            className='white-background grey-color modal-input'
          />
        </div>
        <div className='button-div'>
          <button className='modal-button grey-background white-color' type="button" onClick={handleSave}>Save</button>
          <button className='modal-button grey-background white-color' type="button" onClick={handleClose}>Cancel</button>
        </div>
        <button className='modal-button grey-background white-color' type="button" onClick={handleDelete}>Delete</button>
      </div>
    </div>
  );
}

export default EditModal;