import React, { useState, useRef, useEffect } from 'react';
import './Market.css';
import Add from '../../assets/plus.svg';
import AddModal from './AddModal';

const StockContainer = ({ stock, userId }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const modalRef = useRef(null);

  const handleAddButtonClick = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        setIsModalOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div className='market-stock-container white-color grey-background'>
      <div className='market-stock-container-section-left'>
        <h2 className='market-stock-name'>{stock.name}</h2>
        <p className='market-stock-symbol'>{stock.symbol}</p>
        <p className='market-stock-exchange'>{stock.exchange}</p>
      </div>
      <div className='market-stock-container-section-right'>
        <p className='market-stock-last-price'>{`$${stock.lastPrice.toFixed(2)}`}</p>
        <button className='market-add-button' onClick={handleAddButtonClick}>
          <img className='market-add-image' src={Add} alt="Add" />
        </button>
      </div>
      {isModalOpen && (
        <div className="modal-overlay">
          <div ref={modalRef} className="modal-content">
            <h2>Add Stock</h2>
            <AddModal isOpen={isModalOpen} handleClose={handleCloseModal} userId={userId} stock={stock} />
          </div>
        </div>
      )}
    </div>
  );
}

export default StockContainer;