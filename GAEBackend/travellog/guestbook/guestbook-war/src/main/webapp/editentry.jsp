<!DOCTYPE html>
<html lang="en">
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.net.URL"%>
<%@ page import="com.google.appengine.api.blobstore.BlobKey" %>
<%@ page import="com.google.appengine.api.datastore.EntityNotFoundException" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%-- //[END imports]--%>
<%
BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
String entryKeyString = request.getParameter("entryKey");
if(entryKeyString == null) {
System.out.println("no entry"); 
}

Entity entry = datastore.get(KeyFactory.stringToKey(entryKeyString));
   pageContext.setAttribute("entryKey",
        entryKeyString);
pageContext.setAttribute("entryTitle",
  entry.getProperty("title"));
pageContext.setAttribute("entryDescription",
  entry.getProperty("description"));
pageContext.setAttribute("entryLocation",
  entry.getProperty("location"));
pageContext.setAttribute("entryPoster",
  entry.getProperty("poster"));
pageContext.setAttribute("entryTags",
  entry.getProperty("tags"));
pageContext.setAttribute("entryTripPoster",
  entry.getProperty("tripPoster"));
pageContext.setAttribute("entryDateCreated",
  entry.getProperty("dateCreated"));
  List<String> photos = (List<String>) entry.getProperty("photos");
  pageContext.setAttribute("photolength", photos.size());
  %>

<script>
  var photos = new Array(${fn:escapeXml(photolength)});
</script>

<%
for(int i = 0; i < photos.size(); i++) {
    Entity photo = null;
    try {
      photo = datastore.get(KeyFactory.stringToKey(photos.get(0)));
    } catch (EntityNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String blobKey = ((BlobKey) photo.getProperty("blobKey")).getKeyString();
    String title = (String) photo.getProperty("title");
    String description = (String) photo.getProperty("description");

    pageContext.setAttribute("blobKey", blobKey);
    pageContext.setAttribute("title", title);
    pageContext.setAttribute("description", description);
%>
<script>
console.log("title: ${fn:escapeXml(title)} blobKey is: ${fn:escapeXml(blobKey)}");
var photo = {
  title: "${fn:escapeXml(title)}",
  description: "${fn:escapeXml(description)}",
  url: "/getImageFromBlobKey?blobKey=${fn:escapeXml(blobKey)}",
};

photos.push(photo);
</script>
<%
}
%>

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
            <li class="active"><a href="#" id="trips_button">Trips</a></li>
            <li><a href="#">Locations</a></li>
          </ul>
        </div>
      </div><!--/.nav-collapse -->
      <div id="main" class="container-fluid">
        <form id="editEntry" action="/editEntry?entryKey=${fn:escapeXml(entryKey)}" method="post">
        <input type="file" id="fileElem" class="multi" accept="gif|jpg" name="fileUpload" > <!--add multiple="" to have multiple at once, add accept="image/*" if this isn't working-->
        </form>
        <!-- hidden file selector for customized button -->
        <!-- <input type="file" id="fileElem" multiple accept="image/*" style="display:none" > testing something, not sure if should comment out or not-->

      </div>
      <div id="footer" class="footer navbar-fixed-bottom"><p>Copyright (c) Caterpillar</p></div>
      <script src="../js/util/jquery-1.10.2.min.js"></script>
      <script src="../js/util/bootstrap/js/bootstrap.js"></script>
      <script src="../js/util/bootstrap/js/bootstrap.min.js"></script>
      <script src="../js/js/jquery.MultiFile.js" type="text/javascript" language="javascript"></script>
      <!-- src for tags -->
      <script src="../js/util/tagsinputs/jquery.tagsinput.js"></script>
      <link rel="stylesheet" type="text/css" href="../js/util/tagsinputs/jquery.tagsinput.css"/>
      <!-- src for datepicker -->
      <script type="text/javascript" src="../js/util/node_modules/moment/moment.js"></script>
      <link rel="stylesheet" href="../js/util/datepicker/css/bootstrap-datetimepicker.min.css" />
      <script type="text/javascript" src="../js/util/datepicker/js/bootstrap-datetimepicker.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
      <script type="../js/jQueryUI/js/jquery-1.11.0.js"></script>
      <script type="../js/jQueryUI/js/jquery-1.8.16.custom.min.js"></script>
      <script src="../js/Util.js"></script>
      <script src="../js/editEntry.js"></script>
      <script>
      console.log("making entry");

      var entry = {
       /* title: ${fn:escapeXml(entryTitle)},
        description: ${fn:escapeXml(entryDescription)},
        location: ${fn:escapeXml(entryLocation)},
        tags: ${fn:escapeXml(entryTags)},*/
        title: "${fn:escapeXml(entryTitle)}",
        description: "${fn:escapeXml(entryDescription)}",
        location: "${fn:escapeXml(entryLocation)}",
        tags: "${fn:escapeXml(entryTags)}",
      };
     // var entry = "poop";

      loadFields(entry, photos);
      </script>
    </body>
    </html>
