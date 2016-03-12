var fromDate, toDate;


function populateLineChart(startDate, endDate){
    
    $.ajax({
        url: 'http://localhost:8080/stayfit/dailyCalories',
        method: "GET",
        dataType: "json",
        data: {'from': startDate.format("MM/DD/YYYY"), 'to': endDate.format("MM/DD/YYYY"), 'empId':localStorage.id},
        success: function(responseData){
            console.log(responseData);
            drawLineChart(responseData);
        }
    });
    
}


function drawLineChart(jsonData){
    
    var i, categories = new Array(), data = new Array();
	for (i =0; i< jsonData.length; i++) {
		categories.push(jsonData[i].date);
		data.push({y:jsonData[i].calories,
				color:jsonData[i].zone});
	}
    $('#calories-chart').highcharts({
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: categories,
			title: {
				text: 'Day of Month'
			}
        },
        
        yAxis: {
            title: {
                text: 'Calories'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: 'Calories',
			
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Calories',
            data: data
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
          startDate: moment().subtract(29, 'days'),
          endDate: moment()
        },
    function (startDate, endDate) {
      $('#reportrange').html(startDate.format('MMMM D, YYYY') + ' - ' + endDate.format('MMMM D, YYYY'));
	  
	  if(startDate && endDate) {
		 populateLineChart(startDate, endDate); 
	  }
			
    });
	populateLineChart(moment().subtract(7, 'days'), moment()); 
	
    
});       