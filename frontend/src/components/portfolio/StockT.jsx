import TableRowT from './TableRowT';


const StockT = ({ stocksInPage, total, userId }) => {
  return (
    <table className='stock-table grey-background white-color'>
      <thead>
        <tr>
          <th className='admin-td-header'>Ticker</th>
          <th className='admin-td-header'>Name</th>
          <th className='admin-td-header'>Shares</th>
          <th className='admin-td-header'>Purchase date</th>
          <th className='admin-td-header'>Purchase price</th>
          <th className='admin-td-header'>Current price</th>
          <th className='admin-td-header'>Holdings</th>
          <th className='admin-td-header'>% of portfolio</th>
          <th className='admin-td-header'></th>
        </tr>
      </thead>
      <tbody>
        {
          stocksInPage.map((stock, i) => (
            <TableRowT
              key={i}
              stock={stock}
              total={total}
              userId={userId}
            />
          ))
        }
      </tbody>
    </table>
  );
}

export default StockT;