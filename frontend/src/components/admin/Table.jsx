import TableRow from "./TableRow";


const Table = ({ stocksInPage }) => {
  return (
    <table className='stock-table grey-background white-color'>
      <thead>
        <tr>
          <th>Ticker</th>
          <th>Name</th>
          <th>Exchange</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {
          stocksInPage.map((stock, i) => (
            <TableRow key={i} stock={stock} />
          ))
        }
      </tbody>
    </table>
  );
}

export default Table;