import React, { useState, useRef, useEffect } from 'react';
import './Market.css';
import Add from '../../assets/plus.svg';
import AddCryptoModal from './AddCryptoModal';

const CryptoContainer = ({ crypto, userId }) => {
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
        <h2 className='market-stock-name'>{crypto.ticker}</h2>
      </div>
      <div className='market-stock-container-section-right'>
        <p className='market-stock-last-price'>{`$${crypto.lastPrice.toFixed(2)}`}</p>
        <button className='market-add-button' onClick={handleAddButtonClick}>
          <img className='market-add-image' src={Add} alt="Add" />
        </button>
      </div>
      {isModalOpen && (
        <div className="modal-overlay">
          <div ref={modalRef} className="modal-content">
            <h2>Add Crypto</h2>
            <AddCryptoModal isOpen={isModalOpen} handleClose={handleCloseModal} userId={userId} crypto={crypto} />
          </div>
        </div>
      )}
    </div>
  );
}

export default CryptoContainer;