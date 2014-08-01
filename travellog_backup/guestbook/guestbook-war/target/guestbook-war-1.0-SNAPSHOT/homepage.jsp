<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!--<link rel="icon" href="../assets/ico/favicon.ico">-->

    <title>Welcome to TravelLog</title>
    <!-- Tags Input-->


    <!-- Bootstrap core CHS -->
    <link href="../js/util/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../css/starter.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../assets/js/ie-emulation-modes-warning.js"></script>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../assets/js/ie10-viewport-bug-workaround.js"></script>
    
  </head>

  <body id='body'>
    <div id="header" class='navbar navbar-default navbar-fixed-top'>
      <div id='navbarTop' class='navbar navbar-default'>

      <!-- top bar for title and icon -->
        <div id='ownerContainer'>
          <label id='owner'>Caterpillar's</label>
        </div>
        <div id='logoDiv'>
          <img id='stampLogo'  src='../images/stamp.png'></img>
        </div>
        <div id='whiteLogoDiv'>
          <img id='whiteLogo' src='../images/whitelogo.png'></img>
        </div>
      </div>
      <div id="stripnavbar" class="navbar-default navbar">
        <ul class="nav nav-pills">
          <li class="active"><a href="homepage.jsp" id="trips_button">Trips</a></li>
          <li><a href="MapHome.html" id="maps_button">View on Map</a></li>
        </ul>
      </div>
    </div><!--/.nav-collapse -->
    <div id="main" class="container">

<script>
      // check if storage is available for this browser

      if(typeof(Storage) !== "undefined") {
        console.log("there is session storage");
      } else {
        console.log("browser does not support storage");
      }


</script>

    </div>
    <div id="footer" class="footer navbar-fixed-bottom"><p>Copyright (c) Caterpillar</p></div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
   <!--  // <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
   <script src="../js/util/jquery-1.10.2.min.js"></script>
    <script src="../js/util/tagsinputs/jquery.tagsinput.js"></script>
    <script src="../js/util/bootstrap/js/bootstrap.js"></script>
    <script src="../js/util/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../js/util/tagsinputs/jquery.tagsinput.css" />
        <!-- src for datepicker -->
    <script type="text/javascript" src="../js/util/node_modules/moment/moment.js"></script>
    <link rel="stylesheet" href="../js/util/datepicker/css/bootstrap-datetimepicker.min.css" />
    <script type="text/javascript" src="../js/util/datepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
    <script src="../js/jquery.validate.js"></script>
    <script src="../js/Util.js"></script>
    <script src="../js/Homepage.js"></script>
  </body>
</html>
