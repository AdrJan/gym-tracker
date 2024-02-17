var chart = null;
var chosenMeasurementSize = 1;
var chosenMeasurementType = 'weight';

var selectedMeasurementId = null;

//Setting default selection.
document.getElementById("default-stat-range").classList.add("underlined");
document.getElementById("default-stat").classList.add("underlined");
getDataAndDrawChart(5, 'id', 'desc', 'weight');

//TODO: Naming should be better.
//TODO: Could be refactored.
function showMeasurement(measurementId, listId) {
    $.get('/progress/measurement', { id: measurementId})
      .done(function(data) {
        const elements = document.querySelectorAll('.chosen-result');
        elements.forEach(element => {
            element.classList.remove('chosen-result');
        });
        selectedMeasurementId = measurementId;
        document.getElementById("change-button").classList.remove("disabled");

        document.getElementById("left-biceps-value").textContent=data.leftBiceps;
        document.getElementById("right-biceps-value").textContent=data.rightBiceps;
        document.getElementById("chest-value").textContent=data.chest;
        document.getElementById("waist-value").textContent=data.waist;
        document.getElementById("left-thigh-value").textContent=data.leftThigh;
        document.getElementById("right-thigh-value").textContent=data.rightThigh;
        document.getElementById("weight-value").textContent=data.weight;
        document.getElementById("result-id-" + listId).className += " chosen-result";

        document.getElementById("change-left-biceps-input").placeholder=data.leftBiceps;
        document.getElementById("change-right-biceps-input").placeholder=data.rightBiceps;
        document.getElementById("change-chest-input").placeholder=data.chest;
        document.getElementById("change-waist-input").placeholder=data.waist;
        document.getElementById("change-left-thigh-input").placeholder=data.leftThigh;
        document.getElementById("change-right-thigh-input").placeholder=data.rightThigh;
        document.getElementById("change-weight-input").placeholder=data.weight;
        document.getElementById("modal-title").textContent=data.createdAt;
      })
      .fail(function(error) {
        console.error('Błąd:', error);
      });
}

//TODO: Przerobić to wyżej na modłę tego.
function deleteMeasurementOnModal() {
    fetch('/progress/deleteMeasurement/' + selectedMeasurementId, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(function(response) {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      location.reload();
    })
    .catch(function(error) {
      console.error('Wystąpił błąd podczas usuwania:', error.message);
    });
}

function updateMeasurementOnModal() {
    fetch('/progress/deleteMeasurement/' + selectedMeasurementId, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(function(response) {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      location.reload();
    })
    .catch(function(error) {
      console.error('Wystąpił błąd podczas usuwania:', error.message);
    });
}

function clickAndDraw(obj, size, orderBy, sortOrder, whatToDisplay) {
    if(size === 0) {
        const elements = document.querySelectorAll('.measurement-stat');
        elements.forEach(element => {
            element.classList.remove('underlined');
        });
        size = chosenMeasurementSize;
    }
    if(whatToDisplay.length === 0) {
        const elements = document.querySelectorAll('.measurement-stat-range');
        elements.forEach(element => {
            element.classList.remove('underlined');
        });
        whatToDisplay = chosenMeasurementType;
    }
    obj.classList.add('underlined');

    chosenMeasurementSize = size;
    chosenMeasurementType = whatToDisplay;

    getDataAndDrawChart(size, orderBy, sortOrder, whatToDisplay);
}

function getDataAndDrawChart(size, orderBy, sortOrder, whatToDisplay) {
    $.get('/progress/measurements', { page: 0, size: size, orderBy: orderBy, sortOrder: sortOrder })
      .done(function(data) {
        var mappedData = data.map(function(item) {
            let yValue;
            chosenMeasurementSize = size;
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
        console.log(mappedData);
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
            animation: true,
            plugins: {
                legend: {
                    display: false
                },
            },
            scales: {
                x: {
                    reverse: true,
                    title: {
                        text: 'Data pomiaru',
                        display: false,
                        font: {
                            size: 5,
                            style: 'bold'
                        }
                    }
                },
                y: {
                    title: {
                        text: 'Wartość pomiaru',
                        display: false,
                        font: {
                            size: 5,
                            style: 'bold'
                        }
                    }
                }
            }
        }
    });
}
