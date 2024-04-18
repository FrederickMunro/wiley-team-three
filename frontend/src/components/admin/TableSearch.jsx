import { useEffect, useState } from 'react';
import './Admin.css';

const TableSearch = ({ setDisplayedStocks, allStocks, setCurrentPage }) => {
  
  const [search, setSearch] = useState("");
  
  useEffect(() => {
    setDisplayedStocks(allStocks.filter(stock => {
      return stock.symbol.toUpperCase().includes(search.toUpperCase());
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