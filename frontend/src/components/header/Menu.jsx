import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import './Header.css';
import '../auth/Auth.css';

import HamburgerMenu from './HamburgerMenu';
import MenuItem from './MenuItem';
import LogoutButton from '../auth/LogoutButton';
import CookieContext from '../CookieProvider';

const Menu = () => {
  const navigate = useNavigate();

  const [isOpen, setIsOpen] = useState(false);
  const { cookieExists } = useContext(CookieContext);

  const handleMenuClick = () => {
    setIsOpen(prev => !prev);
  }

  const menuTitles = [
    'Home',
    'Portfolio',
    'Market',
  ]

  const goToLogin = () => {
    navigate('/login', { replace: true });
  }

  return(
    <>
      <div className={`menu ${isOpen ? 'menu-visible' : 'menu-invisible'}`}>
        <nav className='menu-nav'>
          {
            menuTitles.map((item, index) => {
              if (!cookieExists && item === 'Portfolio') {
                return null;
              }
              return <MenuItem key={index} title={item.toUpperCase()} handlemenuclick={handleMenuClick} />
            })
          }
        </nav>
        {
          cookieExists ? (
            <LogoutButton setIsOpen={setIsOpen} />
          ) : (
            <button className='logout-button white-background grey-color' onClick={() => goToLogin()}>
              Login
            </button>
          )
        }
      </div>
      <HamburgerMenu isopen={isOpen} handlemenuclick={handleMenuClick} />
    </>
  );
}

export default Menu;