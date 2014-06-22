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

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.1/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
          <li class="active"><a href="#">Home</a></li>
          <li><a href="#">Locations</a></li>
        </ul>
      </div>
    </div><!--/.nav-collapse -->
    <div id="main" class="container-fluid"></div>
    <div id="footer" class="footer"><p>Copyright (c) Caterpillar</p></div>

    <div class="modal" id="myModal" role="dialog">
      <div class="modal-dialog">
      <div class="modal-content">
      <div class="modal-header">
        <button class="close" type="button" data-dismiss="modal">×</button>
        <h3 class="modal-title"></h3>
      </div>
      <div class="modal-body">
        <div id="modalCarousel" class="carousel">
              <div class="carousel-inner"></div>
              <a class="carousel-control left" href="#modaCarousel" data-slide="prev"><i class="glyphicon glyphicon-chevron-left"></i></a>
              <a class="carousel-control right" href="#modalCarousel" data-slide="next"><i class="glyphicon glyphicon-chevron-right"></i></a>
            </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
       </div>
      </div>
    </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
   <!--  // <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
    <script src="../js/util/jquery-1.10.2.min.js"></script>
    <!-- // <script src="../js/util/jquery.tagsinput.js"></script> -->
    <script src="../js/util/bootstrap/js/bootstrap.js"></script>
    <script src="../js/util/bootstrap/js/bootstrap.min.js"></script>

    <!-- <link rel="stylesheet" type="text/css" href="../js/util/jquery.tagsinput.css"/> -->

    <!-- rich text editor required doc -->
    <!-- // <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js"></script> -->
    <!-- <link href="../js/util/bootstrap-wysiwyg-master/summernote.css" /> -->
    // <!-- <script src="../js/util/bootstrap-wysiwyg-master/bootstrap-wysiwyg.js"></script>
    // <script src="../js/util/bootstrap-wysiwyg-master/external/jquery.hotkeys.js"></script> -->
    
    <script src="../js/Util.js"></script>
    <script src="../js/entryPage.js"></script>
  </body>
</html>
