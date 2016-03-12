<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>StayFit | Dashboard</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="${contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${contextPath}/resources/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${contextPath}/resources/dist/css/stayfit.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${contextPath}/resources/dist/css/skins/_all-skins.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${contextPath}/resources/plugins/iCheck/flat/blue.css">
    <link rel="stylesheet" href="${contextPath}/resources/plugins/daterangepicker/daterangepicker-bs3.css">  
   
    <!-- Bootstrap time Picker -->
    <link rel="stylesheet" href="${contextPath}/resources/plugins/timepicker/bootstrap-timepicker.min.css">  
      
   
    <!-- jvectormap
    <link rel="stylesheet" href="plugins/jvectormap/jquery-jvectormap-1.2.2.css"> -->
    <!-- Date Picker 
    <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">-->
    <!-- Daterange picker 
    <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">-->
    <!-- bootstrap wysihtml5 - text editor 
    <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">-->

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery 2.1.4 -->
    <script src="${contextPath}/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/js/highcharts.js"></script>    
    <!-- jQuery UI 1.11.4 -->
    <script src="${contextPath}/resources/plugins/jQueryUI/jquery-ui.min.js"></script>
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
    <script>
      $.widget.bridge('uibutton', $.ui.button);
    </script>
    <!-- Bootstrap 3.3.5 -->
    <script src="${contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
          
    <!--<script src="plugins/sparkline/jquery.sparkline.min.js"></script>-->
    <!-- jvectormap -->
    <script src="${contextPath}/resources/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <!--<script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>-->
    <!-- jQuery Knob Chart 
    <script src="plugins/knob/jquery.knob.js"></script>-->
    <!-- daterangepicker -->
    <script src="${contextPath}/resources/js/moment.min.js"></script>
    <script src="${contextPath}/resources/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- datepicker -->
    <script src="${contextPath}/resources/plugins/datepicker/bootstrap-datepicker.js"></script>
    <!-- Bootstrap WYSIHTML5-->
    <script src="${contextPath}/resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    <!-- Slimscroll -->
    <script src="${contextPath}/resources/plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <!-- FastClick -->
    <script src="${contextPath}/resources/plugins/fastclick/fastclick.min.js"></script>  
    <script src="${contextPath}/resources/plugins/iCheck/icheck.min.js"></script>    
        
   
    <script src="${contextPath}/resources/dist/js/app.js"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <script src="${contextPath}/resources/dist/js/pages/dashboard.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="${contextPath}/resources/dist/js/demo.js"></script>  
    <script type="text/javascript" src="${contextPath}/resources/js/hr.js"></script>    
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">

      <header class="main-header">
        <!-- Logo -->
        <a href="index2.html" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>S</b>F</span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>Stay</b>Fit</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                        
              <!-- Tasks: style can be found in dropdown.less -->
              
              <!-- User Account: style can be found in dropdown.less -->
              <li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <img src="${contextPath}/resources/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                  <span class="hidden-xs">${userName}</span>
                </a>
                <ul class="dropdown-menu">
                  <!-- User image -->
                  <li class="user-header">
                    <img src="${contextPath}/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                    <p>
                      ${userName}
                    </p>
                  </li>
                  <!-- Menu Body -->
                  
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a href="#" class="btn btn-default btn-flat">Profile</a>
                    </div>
                    <div class="pull-right">
                    
					<c:if test="${pageContext.request.userPrincipal.name != null}">
        				<form id="logoutForm" method="POST" action="${contextPath}/logout">
            				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        				</form>
						<a onclick = "document.forms['logoutForm'].submit()" href="#" class="btn btn-default btn-flat">Sign out</a>
    				</c:if>
                    
                      
                    </div>
                  </li>
                </ul>
              </li>
                  
              <!-- Control Sidebar Toggle Button -->
              <!--
              <li>
                <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
              </li>-->
            </ul>
          </div>
        </nav>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          <!-- Sidebar user panel -->
          <div class="user-panel">
            <div class="pull-left image">
              <img src="${contextPath}/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
              <p>${userName}</p>
              
            </div>
          </div>
          <!-- search form -->
          
          <!-- /.search form -->
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="active">
              <a href="#">
                <i class="fa fa-dashboard"></i> <span>Dashboard</span>
              </a>
             
            </li>     
                  
          
		</ul>
        </section>
        <!-- /.sidebar -->
      </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Dashboard
            <small>HR View</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
            <li class="active">Dashboard</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
                 
       
          <!-- Main row -->
          <div class="row">
         
            <!-- section for pie charts -->
            <section class="col-lg-12 connectedSortable">
                <div class="row">
                    <div class="col-sm-12">
                         <div class="box box-primary">
                            <div class="box-header with-border">
                              <h3 class="box-title">Overall Calories</h3>
                            
                                 <div class="form-group">
                                    <label>Please select Date range to filter the data:</label>
                                    <div class="input-group">
                                      <button id="daterange-btn" class="btn btn-default pull-right">
                                        <i class="fa fa-calendar"></i> Select Date Range
                                        <i class="fa fa-caret-down"></i>
                                      </button>                                      
                                    </div>
                                    <span id="display_date_range"></span>   
                                </div><!-- /.form group -->              
                                
                              <div class="piechart-container chart tab-pane" id="piechart-first">
                                    
                              </div>       
                            </div><!-- /.box-header -->
                            <!-- form start -->
                           
                        </div>
                    </div>                   
                  
                </div> 
                
                
                <!-- Now display table here -->
                <div class="row" id="togglable-table">
            <div class="col-xs-12">
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">Zonewise Employees Data</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
            
                  <div id="example2_wrapper" class="dataTables_wrapper form-inline dt-bootstrap slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 200px;">
                      <div class="row"><div class="col-sm-6"></div><div class="col-sm-6"></div></div>
                      
                      <div class="row">
                          <div class="col-sm-12">
                            
                              <table class="table table-bordered table-hover dataTable" id="example2" role="grid" aria-describedby="example2_info">
                            <thead>
                              <tr role="row">

                                  <th class="sorting_asc" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending">#</th>
                                  <th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="Browser: activate to sort column ascending">Employee Name</th>
                                  <th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending">Calories</th>

                                </tr>
                            </thead>
                            <tbody id="table_user_records"> 
                                  
                            </tbody>
                            
                            </table>
                          
                          </div>
                      
                      </div>
                    
                    
                    </div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->

              
            </div><!-- /.col -->
          </div>
                
                
                
            </section>  
              
            <!-- Left col -->
            <section class="col-lg-12 connectedSortable">
              <!-- Custom tabs (Charts with tabs)-->
             

              <!-- Chat box
              <div class="box box-success">
        
              </div>-->

             
              <div class="box box-primary">
                
                   <div class="row">
                    <div class="col-sm-12">
                         <div class="box box-primary">
                        
                            <div class="box-header with-border">
                              <h3 class="box-title">Zonal Calories Graph</h3>
                                
                           
                                
                              <div class="piechart-container chart tab-pane" id="daily-calories-chart">
                                    
                              </div>       
                            </div><!-- /.box-header -->
                            <!-- form start -->
                           
                        </div>
                    </div>
                                
                </div> 
                
              </div>

              <!-- quick email widget
              <div class="box box-info">
        
              </div> -->

            </section><!-- /.Left col -->
            <!-- right col (We are only adding the ID to make the widgets sortable)-->
            <section class="col-lg-12 connectedSortable">

              <!-- solid sales graph
              <div class="box box-solid bg-teal-gradient">                
               
              </div> /.box -->

              <!-- Calendar 
              <div class="box box-solid bg-green-gradient">         
             
                
              </div><!-- /.box -->

            </section><!-- right col -->
          </div><!-- /.row (main row) -->

        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          <b>Version</b> 2.3.0
        </div>
        <strong>Copyright &copy; 2016 
      </footer>

      <!-- Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
      <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper -->

    
  </body>
</html>
