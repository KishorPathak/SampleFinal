var startDate, endDate;
function populatePieChartData(sdate, edate){
    $.ajax({
        url: 'http://10.222.120.66:8080/stayfit/workoutZones',
        method: "GET",
        dataType: "json",
        data: {'from': sdate, 'to':edate},
        success: function(responseData){            
            drawPieChart(responseData);           
        }
    });    
}



function populateTable(sdate, edate, zone) {
    $.ajax({
        url: 'http://10.222.120.70:8080/stayfit/zonedata',
        method: "GET",
        dataType: "json",
        data: {'from': sdate, 'to':edate, 'zone':zone},
        success: function(responseData){            
            drawUserRecordData(responseData);           
        }
    });    
    
    //$('#table_user_records').html();
	
}

function drawUserRecordData(jdata){
    var htmlStr = "";
    $('#togglable-table').show(); 
    if(jdata.length){
        
        for(var i=0; i<jdata.length; i++){
            htmlStr += "<tr>";
           
           htmlStr += "<td>"+jdata[i].employeeName+"</td>";
           htmlStr += "<td>"+jdata[i].calories+"</td>"; 
            htmlStr += +"</tr>";
        }
        
        
        
    }else{
        htmlStr = '<tr><td colspan="3">Sorry no records have been found.</td></tr>';
    }
    
    $('#table_user_records').html(htmlStr);
}

function populateDailyGraph(sdate, edate){
    
    $.ajax({
        url: 'http://10.222.120.66:8080/stayfit/dailyWorkoutZones',
        method: "GET",
        dataType: "json",
        data: {'from': sdate, 'to':edate},
        success: function(responseData){ 
			console.log(responseData);		
            drawDailyGraph(responseData);           
        }
    }); 
    
}

function drawDailyGraph(jdata){
    
    var i , categories = new Array(), dataGreen = new Array(), dataYellow = new Array(), dataRed = new Array();
	for(i =0; i< jdata.length; i++) {
		if(jdata[i].name === 'YELLOW') {
			dataYellow.push({y: parseInt(jdata[i].count), color: jdata[i].name});
		} else if(jdata[i].name === 'GREEN') {
			dataGreen.push({y: parseInt(jdata[i].count), color: jdata[i].name});
		} else {
			dataRed.push({y: parseInt(jdata[i].count), color: jdata[i].name});
		}
		if(categories.indexOf(jdata[i].date) === -1) {
			categories.push(jdata[i].date);
		}
	}
    
    $('#daily-calories-chart').highcharts({
        title: {
            text: '',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
		colors:["Green", "#ffc61e","Red"],
        xAxis: {
            categories: categories
        },
        yAxis: {
            title: {
                text: 'Number of Employees'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: ' Number of Employees'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Adhering',
            data: dataGreen
        }, {
            name: 'Improving',
            data: dataYellow
        }, {
            name: 'Not Adhering',
            data: dataRed
        }]
    });
}


function drawPieChart(jdata){   
    var newArrayData = [];
    for(var i=0; i<jdata.length; i++){        
        var yvalue =  parseInt(jdata[i].y);
		var legend ;
		if(jdata[i].name === 'GREEN') {
			legend = 'Adhering';
		} else if(jdata[i].name === 'YELLOW') {
			legend = 'Improving';
		} else {
			legend = 'Not Adhering';
		}
        var obj = {"name":legend, "y":yvalue};
        newArrayData.push(obj);
    }
    
    if(newArrayData.length){
   
    $('#piechart-first').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: ''
            },
            colors:["Green", "Red", "#ffc61e"],
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
                                //alert('Zone: ' + this.options.y + ', value: ' + this.options.name);
								var zone ;
								if(this.options.name === 'Adhering') {
									zone = 'GREEN';
								} else if(this.options.name === 'Not Adhering') {
									zone = 'RED';
								} else {
									zone = 'YELLOW';
								}
                                populateTable(startDate, endDate, zone);
                            }
					    }
                    }
                }
            },
            series:[{              
                data:newArrayData
            }]
        });
        
    }else{
        $('#piechart-first').html("<h3>Sorry no data available</h3>")
    }
        
}

$(document).ready(function () {
    $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
    });
	
	 $('.slimScrollDiv').slimScroll({
        height: '250px'
    });
    
    //Date range as a button
    $('#daterange-btn').daterangepicker(
        {
         
          startDate: moment().subtract(29, 'days'),
          endDate: moment()
        },
        function (start, end) {
          //alert(start, end);
          startDate = start.format('MM/DD/YYYY');
          endDate = end.format('MM/DD/YYYY'); 

          populatePieChartData(startDate, endDate);   
			populateDailyGraph(startDate, endDate);		  
     
           
          // display data table
          //$('.togglable-table').show();    
              
          $('#display_date_range').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        }
    );
    
        startDate =  moment().subtract(7, 'days').calendar();
    endDate = moment().format('MM/DD/YYYY');
    $('#display_date_range').html(startDate+" - "+endDate);
  
    populatePieChartData(startDate, endDate);
    populateDailyGraph(startDate, endDate);
    $('#togglable-table').hide();
    //drawPieChart("piechart-second");
    //drawPieChart("piechart-third");
});       