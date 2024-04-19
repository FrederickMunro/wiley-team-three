const SignupInput = ({ title, placeholder, value, setValue, type, borderColor, handleSubmit }) => {

  const handleValueChange = (e) => {
    setValue(e.target.value);
  }

  const handleEnterPress = (e) => {
    if (e.key === 'Enter') {
      handleSubmit();
    }
  }

  return (
    <div className='signup-input-container'>
      <h3 className='signup-input-title'>{title}</h3>
      <input
        className={`signup-input white-background grey-color ${borderColor}`}
        placeholder={placeholder}
        value={value}
        onChange={handleValueChange}
        type={type}
        onKeyDown={handleEnterPress}
      />
    </div>
  );
}

export default SignupInput;