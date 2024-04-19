import './Crypto.css';
import AreaChart from '../charts/AreaChart';

const CryptoContainer = ({ crypto }) => {
  return (
    <div className='crypto-chart-container grey-background'>
      <div className='crypto-section crypto-chart'>
        <AreaChart />
      </div>
      <div className='crypto-info-container white-background'>
        <div className='crypto-stock-container'>
          <h2>{crypto.ticker.toUpperCase()}</h2>
          <p>{(new Date()).toLocaleString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })}</p>
        </div>
        <div className='crypto-number-container'>
          <div>
            <p>Open</p>
            <p>Close</p>
            <p>52-Week High</p>
            <p>52-Week Low</p>
            <p>Volume</p>
          </div>
          <div>
            <p>100</p>
            <p>100</p>
            <p>100</p>
            <p>100</p>
            <p>100</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CryptoContainer;