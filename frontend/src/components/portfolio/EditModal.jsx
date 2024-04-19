import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';

const EditModal = ({ isOpen, handleClose, stock, userId, handleEditStock, handleRemoveStock }) => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];
  const [editedStock, setEditedStock] = useState(stock); // Initialize edited stock state with current stock
  const modalRef = useRef(null); // Reference to modal content element

  useEffect(() => {
    setEditedStock(stock);
  }, [stock])

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
    if (stock.exchange) {
      axios.put(`${API_URL}/portfolio/update`, {
        id: editedStock.portfolioStockId,
        userId: userId,
        symbol: editedStock.symbol,
        quantity: editedStock.quantity,
        purchasePrice: editedStock.purchasePrice
      }, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        handleClose();
        const newStock = {
          symbol: stock.symbol,
          name: stock.name,
          exchange: stock.exchange,
          lastPrice: stock.lastPrice,
          quantity: res.data.quantityOwned,
          purchasePrice: res.data.purchasePrice,
          purchaseDate: stock.purchaseDate,
          portfolioStockId: stock.id
        }
        handleEditStock(newStock);
        console.log('Successfully edited stock holding.', res);
      })
      .catch(err => {
        console.error('Error updating stock holding.', err);
      });
    } else {
      axios.put(`${API_URL}/portfolio/crypto/update`, {
        id: editedStock.portfolioStockId,
        userId: userId,
        ticker: editedStock.ticker,
        quantity: editedStock.quantity,
        purchasePrice: editedStock.purchasePrice,
        purchaseDate: new Date(editedStock.purchaseDate).toISOString(),
      }, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        handleClose();
        const newStock = {
          symbol: stock.symbol,
          lastPrice: stock.lastPrice,
          quantity: res.data.quantityOwned,
          purchasePrice: res.data.purchasePrice,
          purchaseDate: stock.purchaseDate,
          portfolioStockId: stock.id
        }
        handleEditStock(newStock);
        console.log('Successfully edited crypto holding.');
      })
      .catch(err => {
        console.error('Error updating crypto holding.', err);
      });
    }
  };

  const handleDelete = () => {
    if (stock.exchange) {
      axios.delete(`${API_URL}/portfolio/delete/${stock.portfolioStockId}`, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Successfully deleted stock.');
        const newStock = {
          symbol: stock.symbol,
          lastPrice: stock.lastPrice,
          quantity: stock.quantityOwned,
          purchasePrice: stock.purchasePrice,
          purchaseDate: stock.purchaseDate,
          portfolioStockId: stock.id
        }
        handleRemoveStock(newStock);
      })
      .catch(err => {
        console.log('Unable to delete stock.', err);
      })
    } else {
      axios.delete(`${API_URL}/portfolio/crypto/delete/${stock.portfolioStockId}`, {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Successfully deleted crypto.');
        handleClose();
        const newStock = {
          symbol: stock.symbol,
          lastPrice: stock.lastPrice,
          quantity: stock.quantityOwned,
          purchasePrice: stock.purchasePrice,
          purchaseDate: stock.purchaseDate,
          portfolioStockId: stock.id
        }
        handleRemoveStock(newStock);
      })
      .catch(err => {
        console.log('Unable to delete crypto.', err);
      })
    }
  }

  return (
    isOpen &&
    <div className="modal-overlay">
      <div ref={modalRef} className="modal-content grey-color">
        <span className="close" onClick={handleClose}></span>
        <h2>Edit Stock</h2>
        <h3>{stock.symbol}</h3>
        {
          stock.exchange ? (
            <p>{stock.name}</p>
          ) : (
            <p>{stock.currency}</p>
          )
        }
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
        <button className='modal-button-delete grey-background white-color' type="button" onClick={handleDelete}>Delete</button>
      </div>
    </div>
  );
}

export default EditModal;