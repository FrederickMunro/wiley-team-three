import { useState } from 'react';

import './Header.css';

import HamburgerMenu from './HamburgerMenu';
import MenuItem from './MenuItem';

const Menu = () => {

  const [isOpen, setIsOpen] = useState(false);

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
              return <MenuItem key={index} title={item.toUpperCase()} handlemenuclick={handleMenuClick} />
            })
          }
        </nav>
      </div>
      <HamburgerMenu isopen={isOpen} handlemenuclick={handleMenuClick} />
    </>
  );
}

export default Menu;