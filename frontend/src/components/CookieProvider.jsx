import { createContext, useState, useEffect } from 'react';

const CookieContext = createContext();

export const CookieProvider = ({ children }) => {
  const [cookieExists, setCookieExists] = useState(false);

  const checkCookie = (name) => {
    let nameEQ = name + '=';
    let ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
      let c = ca[i];
      while (c.charAt(0)==' ') c = c.substring(1,c.length);
      if (c.indexOf(nameEQ) == 0) return true;
    }
    return false;   
  }

  // Check cookie on component mount
  useEffect(() => {
    const cookieExists = checkCookie('token');
    setCookieExists(cookieExists);
  }, []);

  return (
    <CookieContext.Provider value={{ cookieExists, setCookieExists }}>
      {children}
    </CookieContext.Provider>
  );
};

export default CookieContext;