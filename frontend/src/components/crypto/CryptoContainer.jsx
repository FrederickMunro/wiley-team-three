import axios from 'axios';

import './Crypto.css';
import AreaChart from '../charts/AreaChart';
import { useEffect, useState } from 'react';

const CryptoContainer = ({ crypto, labelSet, dataSet }) => {

  return (
    <div className='crypto-chart-container grey-background'>
      <div className='crypto-section crypto-chart'>
        <AreaChart labelSet={labelSet} dataSet={dataSet} />
      </div>
      <div className='crypto-info-container white-background'>
        <div className='crypto-stock-container'>
          <h2>{crypto.ticker.slice(0, -3).toUpperCase()}</h2>
          <h3>{crypto.ticker.slice(-3).toUpperCase()}</h3>
          <p>{(new Date()).toLocaleString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })}</p>
        </div>
        <div className='crypto-number-container'>
          <div>
            <p>Open</p>
            <p>Close</p>
            <p>High</p>
            <p>Low</p>
          </div>
          <div>
            {
              dataSet &&
              <>
                <p>{`$${dataSet[0]}`}</p>
                <p>{`$${dataSet[dataSet.length-1]}`}</p>
                <p>{`$${Math.max(...dataSet)}`}</p>
                <p>{`$${Math.min(...dataSet)}`}</p>
              </>
            }
          </div>
        </div>
      </div>
    </div>
  );
}

export default CryptoContainer;