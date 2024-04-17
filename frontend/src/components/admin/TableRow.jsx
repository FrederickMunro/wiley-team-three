

const TableRow = ({ stock, allStocks, setAllStocks }) => {

  return (
    <tr key={stock.symbol}>
      <td>{stock.symbol}</td>
      <td>{stock.name}</td>
      <td>{stock.exchange}</td>
    </tr>
  );
}

export default TableRow;