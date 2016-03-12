var data = [{"value":4.0,"zone":"GREEN"},{"value":5.0,"zone":"RED"},{"value":10.0,"zone":"YELLOW"}];

    var processed_json = new Array();   

    for (i = 0; i < data.length; i++){
         processed_json.push([data[i].value]);
    } 

function populateTable(data) {
	var table = document.getElementById("jsonData").children[1];

	// Populate series
	for (i = 0; i < data.length; i++){
		var obj = data[i];
		var row = table.insertRow(i);
		
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		
		cell1.innerHTML = obj.zone;
		cell2.innerHTML = obj.value;		
	}
	
	$('#jsonData').show();
}


function drawPieChart(containerId){    
    $('#'+containerId).highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: ''
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            credits: {
                enabled: false
            },
            plotOptions: {
                pie: {
                    size: '75%',
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },                    
                    showInLegend: true,
					point: {
						events: {
                        click: function () {
                            alert('Zone: ' + this.options.y + ', value: ' + this.value);
							populateTable(data);
                        }
					}
                    }
                }
            },
            series: [{
                name: 'Zone',
                colorByPoint: true,
                data: processed_json
            }]
        });   
}

$(function(){
    
    $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
    });
    
})


$(document).ready(function () {   
    $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
    });
    
    drawPieChart("piechart-first");
    drawPieChart("piechart-second");
    drawPieChart("piechart-third");
});       