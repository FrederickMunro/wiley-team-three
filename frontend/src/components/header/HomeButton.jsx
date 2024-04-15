import { Link } from 'react-router-dom'

import Icon from '../../assets/crypto2.svg';

const HomeButton = () => {
  return(
    <Link to='/'>
      <button className='home-button'>
        <img src={Icon} className='home-logo' />
      </button>
    </Link>
  );
}

export default HomeButton;