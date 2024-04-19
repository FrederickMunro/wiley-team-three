import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';

const AddCryptoModal = ({ isOpen, handleClose, userId, crypto }) => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const modalRef = useRef(null);
  const [inputInfo, setInputInfo] = useState({
    id: crypto.id,
    userId: userId,
    ticker: crypto.ticker,
    quantity: 0,
    purchasePrice: 0,
    purchaseDate: new Date().toISOString(),
  });

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

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setInputInfo({ ...inputInfo, [name]: value });
  };

  const handleSave = () => {
    axios.post(`${API_URL}/portfolio/crypto/add`, inputInfo, {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      handleClose();
      console.log('Successfully added crypto to portfolio.');
    })
    .catch(err => {
      console.error('Error adding cryptos to portfolio:', err);
    });
  };

  return (
    isOpen && (
      <div className="modal-overlay">
        <div ref={modalRef} className="modal-content grey-color">
          <span className="close" onClick={handleClose}>&times;</span>
          <h2>Add Crypto</h2>
          <h3>{crypto.ticker}</h3>
          <div className='input-div'>
            <label htmlFor="quantity">Quantity:</label>
            <input
              type="number"
              id="quantity"
              name="quantity"
              value={inputInfo.quantity}
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
              value={inputInfo.purchasePrice}
              onChange={handleInputChange}
              className='white-background grey-color modal-input'
            />
          </div>
          <div className='button-div'>
            <button className='modal-button grey-background white-color' type="button" onClick={handleSave}>Add</button>
            <button className='modal-button grey-background white-color' type="button" onClick={handleClose}>Cancel</button>
          </div>
        </div>
      </div>
    )
  );
}

export default AddCryptoModal;