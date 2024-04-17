import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import CookieContext from '../CookieProvider';

import './Auth.css';

const LogoutButton = ({ setIsOpen }) => {
  const navigate = useNavigate();
  const { setCookieExists } = useContext(CookieContext);

  const handleLogout = () => {
    document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    setCookieExists(false);
    navigate('/', { replace: true });
    setIsOpen(false);
  };

  return (
     <button className='logout-button white-background grey-color' onClick={handleLogout}>Logout</button>
  );
};

export default LogoutButton;