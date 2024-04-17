import { all } from "axios";
import TableRow from "./TableRow";


const Table = ({ stocksInPage, title, allStocks, setSupportedStocks }) => {
  return (
    <table className='stock-table grey-background white-color'>
      <thead>
        <tr>
          <th className='admin-td-header'>Ticker</th>
          <th className='admin-td-header'>Name</th>
          <th className='admin-td-header'>Exchange</th>
          <th className='admin-add-remove-container'></th>
        </tr>
      </thead>
      <tbody>
        {
          stocksInPage.map((stock, i) => (
            <TableRow
              key={i}
              stock={stock}
              title={title}
              allStocks={allStocks}
              setSupportedStocks={setSupportedStocks} 
            />
          ))
        }
      </tbody>
    </table>
  );
}

export default Table;