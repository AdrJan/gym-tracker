var chart = null;
drawChart(new Map([]));

//TODO: pojawia się error przy usuwaniu elementów. Zweryfikować i usunąć.
$('li.list-group-item').click(function() {
    var exerciseId = $(this).data('exerciseid');

    $.get('/exercise/getExerciseVolume', { exerciseId: exerciseId, limit: 10 })
        .done(function(data) {
            drawChart(data);
        })
        .fail(function(error) {
            console.error('Błąd: ' + exerciseId);
    });
});

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
            labels: keys,
            datasets: [{
                borderColor: "rgba(0,0,255,0.1)",
                data: values
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
                        display: true,
                        font: {
                            size: 20,
                            style: 'bold'
                        }
                    }
                },
                y: {
                    title: {
                        display: true,
                        font: {
                            size: 20,
                            style: 'bold'
                        }
                    },
                    ticks: { min: 0, max: maxValue + 5 }
                }
            }
        }
    });
}

function clickAndDraw(exerciseId, limit) {
    $.get('/getExerciseVolume', { exerciseId: exerciseId, limit: limit })
      .done(function(data) {
        drawChart(data);
      })
      .fail(function(error) {
        console.error('Błąd:', error);
      });
}


var exerciseList = document.querySelectorAll('li.list-group-item');

function filterExercises() {
    var inputValue = document.querySelector('#exercise-search-input').value.toLowerCase().trim();

    for(var i = 0; i < exerciseList.length; i++) {
        var exerciseElement = exerciseList[i];
        exerciseElement.style.display = exerciseElement.querySelector('span').textContent.toLowerCase().includes(inputValue) ? '' : 'none';
    }
}