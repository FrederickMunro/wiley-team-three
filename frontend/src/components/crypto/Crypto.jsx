import { useEffect, useState } from 'react';
import './Crypto.css';
import axios from 'axios';
import CryptoContainer from './CryptoContainer';

const Crypto = () => {
  const API_URL = import.meta.env.VITE_API_URL;
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const [crypto, setCrypto] = useState([]);
  const [allTickers, setAllTickers] = useState([]);

  const [labels, setLabels] = useState([]);
  const [data, setData] = useState([]);

  const today = new Date();
  const yesterday = new Date(today);
  yesterday.setDate(today.getDate() - 1);
  const isoDateString = yesterday.toISOString().split('T')[0];


  useEffect(() => {
    axios.get(`${API_URL}/crypto/all`,  {
      headers: {
        Authorization: `Bearer ${BEARER_TOKEN}`
      }
    })
    .then(res => {
      console.log('Get supported crypto successful.');
      setCrypto(res.data);
    })
    .catch(err => {
      console.error('Error fetching supported crypto:', err);
    })
  }, [])

  useEffect(() => {
    let tempTickers = '';
    if (crypto && crypto.length > 0) {
      crypto.forEach(c => {
        tempTickers = tempTickers + c.ticker + ',';
      })
    }
    setAllTickers(tempTickers.slice(0, -1));
  }, [crypto])

  useEffect(() => {
    if (allTickers && allTickers.length > 0) {
      axios.get(`${API_URL}/analyst/crypto/historical?tickers=${allTickers}&startDate=${isoDateString}&resampleFreq=${'60min'}`,  {
        headers: {
          Authorization: `Bearer ${BEARER_TOKEN}`
        }
      })
      .then(res => {
        console.log('Get crypto historical data successful.');
        const info = res.data.map(item => {
          return item.priceData.map(pd => {
            return {
              time: pd.date.split('T')[1].slice(0, 5),
              price: pd.close.toFixed(2),
            }
          })
        })
        info.forEach(item => {
          labels.push(item.map(i => {
              return i.time;
            })
          )
          data.push(item.map(i => {
            return i.price;
          }))
        });
      })
      .catch(err => {
        console.error('Error fetching crypto historical data.', err);
        const tempLabels = [];
        const tempData = [];
        crypto.forEach(i => {
          tempLabels.push(['14:00', '14:05', '14:10', '14:15',
                          '14:20', '14:25', '14:30', '14:35',
                          '14:40', '14:45', '14:50', '14:55',
                          '15:00', '15:05', '15:10', '15:15',
                          '15:20', '15:25', '15:30', '15:35',
                          '15:40', '15:45', '15:50', '15:55',
                          '16:00', '16:05', '16:10', '16:15',
                          '16:20', '16:25', '16:30', '16:35',
                          '16:40', '16:50', '16:55', '17:10',
                          '17:30', '17:55', '18:10', '18:20',
                          '18:30', '18:35', '18:40', '18:45',
                          '18:55', '19:00', '19:15', '19:20',
                          '19:50', '19:55', '20:00'])
          tempData.push([533.11, 532.80, 533.50, 532.85, 532.69, 534.38, 535.09, 534.01,
                        533.40, 532.97, 532.83, 533.18, 533.37, 533.40, 534.17, 534.66,
                        534.85, 534.62, 534.76, 534.25, 534.37, 534.32, 534.40, 535.08,
                        535.83, 534.66, 534.60, 534.25, 533.80, 534.18, 533.85, 534.00,
                        534.08, 534.25, 534.50, 533.91, 533.76, 534.26, 534.50, 534.50,
                        534.50, 535.00, 535.00, 534.50, 534.96, 535.00, 535.00, 535.59,
                        535.67, 535.06, 534.54])
        })
        setLabels(tempLabels);
        setData(tempData);
      })
    }
  }, [allTickers])

  useEffect(() => {
    console.log('labels',labels);
  }, [labels])
  
  useEffect(() => {
    console.log('labels',data);
  }, [data])

  return (
    <div className='crypto-container white-background'>
      {
        crypto && crypto.map((item, i) => {
          return <CryptoContainer key={item.id} crypto={item} labelSet={labels[i]} dataSet={data[i]} />
        })
      }
    </div>
  );
}

export default Crypto;