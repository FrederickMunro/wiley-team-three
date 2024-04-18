import './Admin.css';

const TableNav = ({ totalpages, currentpage, setcurrentpage }) => {

  const nextPage = () => {
    setcurrentpage(currentpage + 1);
  };

  const prevPage = () => {
    setcurrentpage(currentpage - 1);
  };

  return (
    <div className='admin-page-container'>
      <button className='admin-page-button white-background grey-color' onClick={prevPage} disabled={currentpage === 1}>Prev</button>
      <p className='admin-page-count white-color'>{currentpage} / {totalpages}</p>
      <button className='admin-page-button white-background grey-color' onClick={nextPage} disabled={currentpage === totalpages}>Next</button>
    </div>
  );
}

export default TableNav;