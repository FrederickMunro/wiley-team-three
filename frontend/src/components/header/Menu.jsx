import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import './Header.css';
import '../auth/Auth.css';

import HamburgerMenu from './HamburgerMenu';
import MenuItem from './MenuItem';
import LogoutButton from '../auth/LogoutButton';
import CookieContext from '../CookieProvider';
import axios from 'axios';

const Menu = () => {
  const API_URL = import.meta.env.VITE_API_URL;
  const navigate = useNavigate();

  const [isOpen, setIsOpen] = useState(false);
  const [role, setRole] = useState();
  const { cookieExists } = useContext(CookieContext);
  const BEARER_TOKEN = document.cookie.split('=')[1];

  const handleMenuClick = () => {
    setIsOpen(prev => !prev);
  }

  const menuTitles = [
    'Home',
    'Portfolio',
    'Market',
    'Admin',
  ]

  const goToLogin = () => {
    navigate('/login', { replace: true });
  }

  useEffect(() => {
    if (cookieExists) {
      axios.get(`${API_URL}/user-info?token=${BEARER_TOKEN}`)
      .then(res => {
        setRole(res.data.role);
      })
      .catch(err => {
        console.log('User information retrieval unsuccessful.', err);
      })
    }
  }, [])

  return(
    <>
      <div className={`menu ${isOpen ? 'menu-visible' : 'menu-invisible'}`}>
        <nav className='menu-nav'>
          {
            menuTitles.map((item, index) => {
              if ((!cookieExists || role !== 'TRADER') && item === 'Portfolio') {
                return null;
              } else if ((!cookieExists || role !== 'ADMIN') && item === 'Admin') {
                return null;
              } else if ((!cookieExists || (role !== 'ANALYST' && role !== 'TRADER')) && item === 'Market') {
                return null;
              } else {
                return <MenuItem key={index} title={item.toUpperCase()} handlemenuclick={handleMenuClick} />
              }
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