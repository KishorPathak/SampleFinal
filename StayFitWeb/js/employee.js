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

function populateLineChart(){
    
    $.ajax({
        url: 'http://10.222.120.66:8080/stayfit/workoutZones',
        method: "GET",
        dataType: "json",
        data: {'from': '02/01/2016', 'to':'02/01/2016'},
        success: function(responseData){
            console.log(responseData);
            drawLineChart(jsonData);
        }
    });
    
}


function drawLineChart(jsonData){
    
    var jsonFormattedData = JSON.parse(jsonData);
    
   
   
    $('#calories-chart').highcharts({
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        colors: ['GREEN', 'RED', 'YELLOW'],
        yAxis: {
            title: {
                text: 'Zones'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }]
    });
}


$(document).ready(function () {   
    $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
    });
    
    //Date range as a button
    $('#daterange-btn').daterangepicker(
        {
         /* ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
          },*/
          startDate: moment().subtract(29, 'days'),
          endDate: moment()
        },
    function (start, end) {
      $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
    }
    );
    
    
    drawPieChart("piechart-first");
    drawPieChart("piechart-second");
    drawPieChart("piechart-third");
    populateLineChart();
});       