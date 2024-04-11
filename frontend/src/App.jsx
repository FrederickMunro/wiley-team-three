import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Filler
} from 'chart.js';

import AreaChart from './components/charts/AreaChart';
import DonutChart from './components/charts/DonutChart';
import PieChart from './components/charts/PieChart';

import './App.css';

ChartJS.register(
  ArcElement,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Filler
);

function App() {

  return (
    <div className='container'>
      <div className='test'>
        <DonutChart />
      </div>
      <div className='test'>
        <PieChart />
      </div>
      <div className='test'>
        <AreaChart />
      </div>
    </div>
  );
}

export default App;
