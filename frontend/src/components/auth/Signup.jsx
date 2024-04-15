import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';


import HomeButton from '../header/HomeButton';
import SignupInput from './SignupInput';

import './Auth.css';

const Signup = () => {
  const API_URL = import.meta.env.VITE_API_URL;

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [passwordBorderColor, setPasswordBorderColor] = useState("");
  const [usernameBorderColor, setUsernameBorderColor] = useState("");
  const [usernameDuplicate, setUsernameDuplicate] = useState("");
  const [notification, setNotification] = useState("");

  useEffect(() => {
    if (password !== "") {
      const passwordRegex = /^(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?/~`\-|\\])(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$/;
      if (!passwordRegex.test(password)) {
        setPasswordError("Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one digit, and one special character.");
        setPasswordBorderColor('red-border');
      } else {
        setPasswordError("");
        setPasswordBorderColor('green-border');
      }
    } else {
      setPasswordError("");
      setPasswordBorderColor('');
    }
  }, [password]);

  useEffect(() => {
    setUsernameBorderColor("");
    setUsernameDuplicate("");
  }, [username])

  const handleSubmit = () => {
    const passwordRegex = /^(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?/~`\-|\\])(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$/;
    if (!username || !email || !password) {
      setNotification("Please fill in all fields");
    } else if (!passwordRegex.test(password)) {
      setNotification('Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one digit, and one special character.');
    } else {
      axios.post(API_URL + '/register', {
        username: username,
        password: password,
        email: email.toLowerCase(),
      })
      .then(res => {
        setNotification('Registration successful.');
        setUsernameBorderColor('');
        console.log('Successfully registered:', res);
      })
      .catch(err => {
        setUsernameDuplicate('Username already in use.');
        setNotification("");
        setUsernameBorderColor('red-border');
        console.log('Register unsuccessful:', err.response.data.message);
      })
    }
  }

  return (
    <div className='signup-container white-background'>
      <div className='signup-header grey-background'>
        <HomeButton />
        <Link to='/login'>
          <button className='auth-redirect-button white-background grey-color'>Log in</button>
        </Link>
      </div>
      <div className='signup-body grey-color'>
        <h1 className='signup-title'>Sign up</h1>
        <SignupInput
          title='Username'
          placeholder={'Username'}
          value={username}
          setValue={setUsername}
          type='text'
          borderColor={usernameBorderColor}
        />
        {
          usernameDuplicate !== "" &&
          <p className="signup-input-error-message">{usernameDuplicate}</p>
        }
        <SignupInput
          title='Email'
          placeholder={'Email'}
          value={email}
          setValue={setEmail}
          type='email'
          borderColor=''
        />
        <SignupInput
          title='Password'
          placeholder={'Password'}
          value={password}
          setValue={setPassword}
          type='password'
          borderColor={passwordBorderColor}
        />
        {
          passwordError !== "" &&
          <p className="signup-input-error-message">{passwordError}</p>
        }
        {
          notification !== "" &&
          <p className="signup-input-error-message">{notification}</p>
        }
        <button className='signup-submit-button grey-background white-color' onClick={handleSubmit}>Create account</button>
      </div>
    </div>
  );
}

export default Signup;