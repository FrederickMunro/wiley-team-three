import { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

import HomeButton from '../header/HomeButton';
import SignupInput from '../signup/SignupInput';

import '../signup/Signup.css';

const Login = () => {
  const API_URL = import.meta.env.VITE_API_URL;

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = () => {
    axios.post(API_URL + '/login', {
      username: username,
      password: password
    })
    .then(res => {
      console.log('Login successful:', res.data);
    })
    .catch(err => {
      console.log('Login unsuccessful', err);
    })
  }

  return(
    <div className='signup-container white-background'>
      <div className='signup-header grey-background'>
        <HomeButton />
        <Link to='/signup'>
          <button className='auth-redirect-button white-background grey-color'>Sign up</button>
        </Link>
      </div>
      <div className='signup-body grey-color'>
        <h1 className='signup-title'>Log in</h1>
        <SignupInput
          title='Username'
          placeholder={'Username'}
          value={username}
          setValue={setUsername}
          type='text'
          borderColor='grey-border'
        />
        
        <SignupInput
          title='Password'
          placeholder={'Password'}
          value={password}
          setValue={setPassword}
          type='password'
        />
        <button className='signup-submit-button grey-background white-color' onClick={handleSubmit}>Log in</button>
      </div>
    </div>
  );
}

export default Login;