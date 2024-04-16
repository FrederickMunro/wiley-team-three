import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from './App.jsx';

import './main.css';
import Header from './components/header/Header.jsx';
import Home from './components/home/Home.jsx';
import Signup from './components/auth/Signup.jsx';
import Login from './components/auth/Login.jsx';
import { CookieProvider } from './components/CookieProvider.jsx';


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
        <App />
      </>
    )
  },
  {
    path: '/market',
    element: (
      <>
        <Header />
        <App />
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
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <CookieProvider>
      <RouterProvider router={router} />
    </CookieProvider>
  </React.StrictMode>,
);
