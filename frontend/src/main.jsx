import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from './App.jsx';

import './main.css';
import Header from './components/header/Header.jsx';
import Home from './components/home/Home.jsx';
import Signup from './components/signup/Signup.jsx';


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
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
);
