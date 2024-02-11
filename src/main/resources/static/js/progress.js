    var chart = null;

    function showMeasurement(measurementId, listId) {
        $.get('/progress/measurement', { id: measurementId})
          .done(function(data) {
            const elements = document.querySelectorAll('.chosen-result');
            elements.forEach(element => {
                element.classList.remove('chosen-result');
            });

            document.getElementById("left-biceps-value").textContent=data.leftBiceps;
            document.getElementById("right-biceps-value").textContent=data.rightBiceps;
            document.getElementById("chest-value").textContent=data.chest;
            document.getElementById("waist-value").textContent=data.waist;
            document.getElementById("left-thigh-value").textContent=data.leftThigh;
            document.getElementById("right-thigh-value").textContent=data.rightThigh;
            document.getElementById("weight-value").textContent=data.weight;
            document.getElementById("result-id-" + listId).className += " chosen-result";
          })
          .fail(function(error) {
            console.error('Błąd:', error);
          });
    }

    function clickAndDraw(size, orderBy, sortOrder, whatToDisplay) {
        $.get('/progress/measurements', { page: 0, size: size, orderBy: orderBy, sortOrder: sortOrder })
          .done(function(data) {
            var mappedData = data.map(function(item) {
                let yValue;
                switch(whatToDisplay) {
                    case 'leftBiceps':
                        yValue = item.leftBiceps;
                        break;
                    case 'rightBiceps':
                        yValue = item.rightBiceps;
                        break;
                    case 'chest':
                        yValue = item.chest;
                        break;
                    case 'waist':
                        yValue = item.waist;
                        break;
                    case 'leftThigh':
                        yValue = item.leftThigh;
                        break;
                    case 'rightThigh':
                        yValue = item.rightThigh;
                        break;
                    case 'weight':
                    default:
                        yValue = item.weight;
                        break;
                }
                return {x: item.createdAt, y:yValue}
            });
            drawChart(mappedData);
          })
          .fail(function(error) {
            console.error('Błąd:', error);
          });
    }

    function drawChart(map) {
        let mapAsMap = new Map(Object.entries(map));
        let keys = Array.from(mapAsMap.keys());
        let values = Array.from(mapAsMap.values());
        let maxValue = Math.max(...values);

        if(chart != null)
            chart.destroy();

         chart = new Chart("myChart", {
            type: "line",
            data: {
                datasets: [{
                    borderColor: "rgba(0,0,255,0.1)",
                    data: map
                }]
            },
            options: {
                animation: false,
                plugins: {
                    legend: {
                        display: false
                    },
                },
                scales: {
                    x: {
                        title: {
                            text: 'Data pomiaru',
                            display: true,
                            font: {
                                size: 20,
                                style: 'bold'
                            }
                        }
                    },
                    y: {
                        title: {
                            text: 'Wartość pomiaru',
                            display: true,
                            font: {
                                size: 20,
                                style: 'bold'
                            }
                        }
                    }
                }
            }
        });
    }

    clickAndDraw(10, 'id', 'asc');
