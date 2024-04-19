import TableRowT from './TableRowT';


const StockT = ({ stocksInPage, total, userId, handleEditStock }) => {
  return (
    <table className='stock-table grey-background white-color'>
      <thead>
        <tr>
          <th className='admin-td-header'>Ticker</th>
          <th className='admin-td-header'>Name</th>
          <th className='admin-td-header'>Shares</th>
          <th className='admin-td-header no-small'>Purchase date</th>
          <th className='admin-td-header no-small'>Purchase price</th>
          <th className='admin-td-header no-small'>Current price</th>
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
              handleEditStock={handleEditStock}
            />
          ))
        }
      </tbody>
    </table>
  );
}

export default StockT;