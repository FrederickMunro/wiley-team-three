import { useState, useEffect } from 'react';
import './Admin.css';
import Table from './Table';
import TableNav from './TableNav';
import TableSearch from './TableSearch';

const AdminTable = ({ title, allStocks, setSupportedStocks }) => {

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
      <h2 className='admin-table-title'>{title}</h2>
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
      <Table
        stocksInPage={stocksInPage}
        title={title}
        allStocks={allStocks}
        setSupportedStocks={setSupportedStocks} 
      />
    </div>
  );
}

export default AdminTable;