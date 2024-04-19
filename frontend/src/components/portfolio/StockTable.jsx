import { useEffect, useState } from 'react';
import StockT from './StockT';
import TableNav from '../admin/TableNav';
import TableSearch from '../admin/TableSearch';

const StockTable = ({ allStocks, userId, handleEditStock }) => {

  const [currentPage, setCurrentPage] = useState(1);
  const [displayedStocks, setDisplayedStocks] = useState([]);
  const [total, setTotal] = useState(1);
  const stocksPerPage = 5;
  const totalPages = Math.ceil(displayedStocks.length / stocksPerPage);
  const firstStockInList = (currentPage - 1) * stocksPerPage;
  const lastStockInList = Math.min(firstStockInList + stocksPerPage - 1, displayedStocks.length - 1);
  const stocksInPage = displayedStocks.slice(firstStockInList, lastStockInList + 1);

  useEffect(() => {
    setDisplayedStocks(allStocks);
    setTotal(allStocks.reduce((sum, stock) => sum + (stock.quantity * stock.lastPrice), 0));
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
        total={total}
        userId={userId}
        handleEditStock={handleEditStock}
      />
    </div>
  );
}


export default StockTable;