import { Link } from 'react-router-dom';

import AreaChart from '../charts/AreaChart';

import './Home.css';

const Home = () => {

  return (
    <div className='home-container'>
      <div className='home-section-container white-background'>
        <div className='home-section'>
          <h1 className='home-section-title'>Invest. Trade. Track. Grow.</h1>
          <p className='home-section-text'>
            Stock Trader provides a secure environment for acquiring, trading, and monitoring financial market assets.
            Our platform enables seamless trading of stocks, ETFs, and cryptocurrencies through an intuitive and efficient interface.
          </p>
          <Link to='/signup'>
            <button className='home-signup-button'>
              Sign up
            </button>
          </Link>
        </div>
      </div>
      <div className='home-section-container grey-background'>
        <div className='home-section chart'>
          <AreaChart />
        </div>
        <div className='snp-info-container white-background'>
          <div className='snp-stock-container'>
            <h2>S&P 500</h2>
            <p>^GSPC</p>
            <p>{(new Date()).toLocaleString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })}</p>
          </div>
          <div className='snp-number-container'>
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
    </div>
  );
}

export default Home;