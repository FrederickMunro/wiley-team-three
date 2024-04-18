import { Doughnut } from 'react-chartjs-2';

const DonutChart = ({ labels,  }) => {

  const data = {
    labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange', 'White'],
    datasets: [
      {
        label: '%',
        data: [12, 19, 3, 5, 2, 3, 9],
        backgroundColor: [
          'rgba(255, 99, 71, 1)',    // Tomato
          'rgba(255, 215, 0, 1)',    // Gold
          'rgba(255, 192, 203, 1)',  // Pink
          'rgba(0, 191, 255, 1)',    // Deep Sky Blue
          'rgba(144, 238, 144, 1)',  // Light Green
          'rgba(255, 165, 0, 1)',    // Orange
          'rgba(173, 216, 230, 1)',  // Light Blue
          'rgba(255, 105, 180, 1)',  // Hot Pink
          'rgba(135, 206, 250, 1)',  // Light Sky Blue
          'rgba(255, 20, 147, 1)',   // Deep Pink
          'rgba(255, 140, 0, 1)',    // Dark Orange
          'rgba(0, 128, 0, 1)',      // Green
          'rgba(255, 192, 0, 1)',    // Gold
          'rgba(65, 105, 225, 1)',   // Royal Blue
          'rgba(128, 0, 128, 1)',    // Purple
          'rgba(128, 128, 0, 1)',    // Olive
          'rgba(128, 0, 0, 1)',      // Maroon
          'rgba(165, 42, 42, 1)',    // Brown
          'rgba(0, 0, 128, 1)',      // Navy
          'rgba(128, 128, 128, 1)',  // Gray
        ],
        borderColor: [
          'rgba(255, 255, 255, 1)',
          'rgba(255, 255, 255, 1)',
          'rgba(255, 255, 255, 1)',
          'rgba(255, 255, 255, 1)',
          'rgba(255, 255, 255, 1)',
          'rgba(255, 255, 255, 1)',
        ],
        borderWidth: 0,
      },
    ],
  };

  const options = {
    plugins: {
      legend: {
        display: false,
      }
    }
  }

  return (
    <Doughnut data={data} options={options} />
  );
}

export default DonutChart;