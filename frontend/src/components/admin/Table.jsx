import TableRow from "./TableRow";


const Table = ({ stocksInPage, title, allStocks, setSupportedStocks, type }) => {
  return (
    <table className='stock-table grey-background white-color'>
      <thead>
        <tr>
          <th className='admin-td-header'>Ticker</th>
          {
            type == 'stock' &&
            <>
              <th className='admin-td-header'>Name</th>
              <th className='admin-td-header'>Exchange</th>
            </>
          }
          {
            type == 'crypto' &&
            <>
              <th className='admin-td-header'>Currency</th>
            </>
          }
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
              type={type}
            />
          ))
        }
      </tbody>
    </table>
  );
}

export default Table;