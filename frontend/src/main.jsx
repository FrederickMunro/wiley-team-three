import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from './App.jsx';

import './main.css';
import Header from './components/header/Header.jsx';
import Home from './components/home/Home.jsx';
import Signup from './components/auth/Signup.jsx';
import Login from './components/auth/Login.jsx';
import Portfolio from './components/portfolio/Portfolio.jsx';
import { CookieProvider } from './components/CookieProvider.jsx';
import Admin from './components/admin/Admin.jsx';
import Market from './components/market/Market';


const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <>
        <Header />
        <Home />
      </>
    ),
  },
  {
    path: '/portfolio',
    element: (
      <>
        <Header />
        <Portfolio />
      </>
    )
  },
  {
    path: '/market',
    element: (
      <>
        <Header />
        <Market />
      </>
    )
  },
  {
    path: '/signup',
    element: (
      <>
        <Signup />
      </>
    )
  },
  {
    path: '/login',
    element: (
      <>
        <Login />
      </>
    )
  },
  {
    path: '/admin',
    element: (
      <>
        <Header />
        <Admin />
      </>
    )
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <CookieProvider>
      <RouterProvider router={router} />
    </CookieProvider>
  </React.StrictMode>,
);
