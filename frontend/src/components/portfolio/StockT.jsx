import TableRowT from './TableRowT';


const StockT = ({ stocksInPage, total, userId, handleEditStock, handleRemoveStock }) => {
  return (
    <table className='stock-table grey-background white-color'>
      <thead>
        <tr>
          <th className='admin-td-header'>Ticker</th>
          {
            stocksInPage && stocksInPage.length > 0 && stocksInPage[0].exchange ? (
              <th className='admin-td-header'>Name</th>
            ) : (
              <th className='admin-td-header'>Currency</th>
            )
          }
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
              handleRemoveStock={handleRemoveStock}
            />
          ))
        }
      </tbody>
    </table>
  );
}

export default StockT;