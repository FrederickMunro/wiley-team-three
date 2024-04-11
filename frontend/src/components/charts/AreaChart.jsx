import { Line } from 'react-chartjs-2';

const AreaChart = () => {

  const labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
  const data = {
    labels,
    datasets: [
      {
        fill: true,
        label: 'Dataset 2',
        data: labels.map(() => Math.floor(Math.random() * 1000)),
        borderColor: 'rgb(53, 162, 235)',
        backgroundColor: 'rgba(53, 162, 235, 0.5)',
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Chart.js Line Chart',
      },
    },
  };

  return (
    <Line data={data} options={options} />
  );
}

export default AreaChart;