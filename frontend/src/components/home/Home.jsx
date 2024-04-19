import { Link } from 'react-router-dom';

import AreaChart from '../charts/AreaChart';

import './Home.css';
import { useContext, useEffect, useState } from 'react';
import CookieContext from '../CookieProvider';

import CryptoSupport from '../../assets/crypto-support.svg';
import Arrow from '../../assets/arrow.svg';

const Home = () => {

  const { cookieExists } = useContext(CookieContext);

  return (
    <div className='home-container'>
      <div className='home-section-container white-background home-main two'>
        <div className='home-section'>
          <h1 className='home-section-title'>Invest. Trade. Track. Grow.</h1>
          <p className='home-section-text'>
            Welcome to our platform designed to empower your financial journey. Dive into a 
            world of possibilities where you can invest, trade, and grow your wealth with confidence.
          </p>
          {
            cookieExists ? null : (
              <Link to='/signup'>
                <button className='home-signup-button'>
                  Sign up
                </button>
              </Link>
            )
          }
        </div>
      </div>
      <div className='home-section-container grey-background home-chart one'>
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
      <div className='home-section-container grey-background home-main three'>
        <img className='home-crypto-support' src={CryptoSupport} />
      </div>
      <div className='home-section-container white-background home-chart second-row four'>
        <h1 className='home-section-title'>We support cryptocurrencies</h1>
        <p className='home-section-text'>
          Embrace the future of finance with us. Explore our platform's comprehensive support 
          for cryptocurrencies and unlock new opportunities in the digital asset space.
        </p>
      </div>
      <div className='home-section-container white-background home-main six'>
        <h1 className='home-section-title'>Track your progress</h1>
        <p className='home-section-text'>
          Stay informed, stay ahead. Effortlessly track your investment progress and visualize your 
          financial growth over time with our intuitive tracking tools.
        </p>
      </div>
      <div className='home-section-container grey-background home-chart second-row five'>
        <img className='home-crypto-support' src={Arrow} />
      </div>
    </div>
  );
}

export default Home;