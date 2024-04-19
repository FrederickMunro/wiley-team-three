import { Line } from 'react-chartjs-2';

const AreaChart = ({ labelSet, dataSet }) => {

  const data = {
    labels: labelSet,
    datasets: [
      {
        data: dataSet,
        tension: 0.3,
        borderColor: '#FFFFFF',
        pointBackgroundColor: '#FFFFFF',
      },
    ],
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        border: {
          display: false,
        },
        ticks: {
          color: '#FFFFFF',
        },
        grid: {
          display: false,
        }
      },
      y: {
        border: {
          color: 'rgba(255, 255, 255, .3)',
          dash: [5,5],
        },
        ticks: {
          color: '#FFFFFF',
        },
        grid: {
          color: 'rgba(255, 255, 255, .3)',
        }
      }
    },
    plugins: {
      legend: {
        display: false,
      },
    }
  }

  return (
    <Line data={data} options={options} />
  );
}

export default AreaChart;