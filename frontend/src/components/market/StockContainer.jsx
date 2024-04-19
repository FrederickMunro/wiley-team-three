import './Market.css';

import Add from '../../assets/plus.svg';

const StockContainer = ({ stock }) => {
  return (
    <div className='market-stock-container white-color grey-background'>
      <div className='market-stock-container-section-left'>
        <h2 className='market-stock-name'>{stock.name}</h2>
        <p className='market-stock-symbol'>{stock.symbol}</p>
        <p className='market-stock-exchange'>{stock.exchange}</p>
      </div>
      <div className='market-stock-container-section-right'>
        <p className='market-stock-last-price'>{`$${stock.lastPrice.toFixed(2)}`}</p>
        <button className='market-add-button'>
          <img className='market-add-image' src={Add} />
        </button>
      </div>
    </div>
  );
}

export default StockContainer;