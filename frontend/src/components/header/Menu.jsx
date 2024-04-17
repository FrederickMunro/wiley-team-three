import { useContext, useState } from 'react';

import './Header.css';

import HamburgerMenu from './HamburgerMenu';
import MenuItem from './MenuItem';
import LogoutButton from '../auth/LogoutButton';
import CookieContext from '../CookieProvider';

const Menu = () => {

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
        <LogoutButton setIsOpen={setIsOpen} />
      </div>
      <HamburgerMenu isopen={isOpen} handlemenuclick={handleMenuClick} />
    </>
  );
}

export default Menu;