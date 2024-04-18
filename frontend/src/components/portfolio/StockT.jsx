import TableRowT from './TableRowT';


const StockT = ({ stocksInPage }) => {
  return (
    <table className='stock-table grey-background white-color'>
      <thead>
        <tr>
          <th className='admin-td-header'>Ticker</th>
          <th className='admin-td-header'>Name</th>
          <th className='admin-td-header'>Exchange</th>
        </tr>
      </thead>
      <tbody>
        {
          stocksInPage.map((stock, i) => (
            <TableRowT
              key={i}
              stock={stock}
            />
          ))
        }
      </tbody>
    </table>
  );
}

export default StockT;