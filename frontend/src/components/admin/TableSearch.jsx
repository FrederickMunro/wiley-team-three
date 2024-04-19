import { useEffect, useState } from 'react';
import './Admin.css';

const TableSearch = ({ setDisplayedStocks, allStocks, setCurrentPage, type }) => {
  
  const [search, setSearch] = useState("");
  
  useEffect(() => {
    setDisplayedStocks(allStocks.filter(stock => {
      if (type === 'stock') {
        return stock.symbol.toUpperCase().includes(search.toUpperCase());
      } else if (type === 'crypto') {
        return stock.ticker.toUpperCase().includes(search.toUpperCase());
      }
    }));
    setCurrentPage(1);
  }, [search]);

  return (
    <input
      className='admin-input white-background grey-color'
      type="text"
      placeholder="Search by ticker"
      value={search}
      onChange={(e) => setSearch(e.target.value)}
    />
  );
}

export default TableSearch;