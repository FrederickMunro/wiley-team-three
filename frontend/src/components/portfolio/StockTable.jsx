import { useEffect, useState } from 'react';
import StockT from './StockT';
import TableNav from '../admin/TableNav';
import TableSearch from '../admin/TableSearch';

const StockTable = ({ allStocks, setSupportedStocks }) => {

  const [currentPage, setCurrentPage] = useState(1);
  const [displayedStocks, setDisplayedStocks] = useState([]);
  const stocksPerPage = 25;
  const totalPages = Math.ceil(displayedStocks.length / stocksPerPage);
  const firstStockInList = (currentPage - 1) * stocksPerPage;
  const lastStockInList = Math.min(firstStockInList + stocksPerPage - 1, displayedStocks.length - 1);
  const stocksInPage = displayedStocks.slice(firstStockInList, lastStockInList + 1);

  useEffect(() => {
    setDisplayedStocks(allStocks);
  }, [allStocks]);

  return (
    <div className='admin-table-container grey-background'>
      <div className="admin-nav-container">
        <TableSearch
          setDisplayedStocks={setDisplayedStocks}
          allStocks={allStocks}
          setCurrentPage={setCurrentPage}
        />
        <TableNav
          totalpages={totalPages}
          currentpage={currentPage}
          setcurrentpage={setCurrentPage}
        />
      </div>
      <StockT
        stocksInPage={stocksInPage}
      />
    </div>
  );
}


export default StockTable;