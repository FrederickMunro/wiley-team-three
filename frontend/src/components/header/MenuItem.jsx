import { Link } from 'react-router-dom';

import './Header.css';

const MenuItem = ({ title, handlemenuclick }) => {

  return(
    <Link
      to={title.toLowerCase() === 'home' ? '/' : `/${title.toLowerCase()}`}
      className='menu-button'
      onClick={() => handlemenuclick()}
    >
      {title}
    </Link>
  );
}

export default MenuItem;