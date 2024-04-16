const SignupInput = ({ title, placeholder, value, setValue, type, borderColor }) => {

  const handleValueChange = (e) => {
    setValue(e.target.value);
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
      />
    </div>
  );
}

export default SignupInput;