import React from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';

const _value: string = "";

function App() {
  const [value, setValue]: [string, (value: string) => void] = React.useState(_value);
    
  const handleValueChange = (event: React.ChangeEvent<HTMLInputElement>) => {
      const newValue = event.target.value;
      setValue(newValue);
      axios.put<string>("http://localhost:8080/hello-world", newValue, {headers: {"Content-Type": "text/plain"}});
  };

    
  React.useEffect(() => {
      axios
          .get<string>("http://localhost:8080/hello-world")
          .then(res => setValue(res.data));
  }, []);
    
  return (
    <div className="App">
        <p>
            <form>
                <input type="text" value={value} onChange={handleValueChange}/>
            </form>
        </p>
    </div>
  );
}

export default App;
