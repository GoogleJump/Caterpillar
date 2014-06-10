

<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%-- //[START imports]--%>
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
<%-- //[END imports]--%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!--<link rel="icon" href="../assets/ico/favicon.ico">-->

    <title>Welcome to TravelLog</title>
    <!-- Tags Input-->
    <!-- fonts -->
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Rancho&effect=fragile">

    <!-- Bootstrap core CHS -->
    <link href="../js/util/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../css/starter.css" rel="stylesheet">
<!--     <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
 -->
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
          <li><a href="#">Back to Trips</a></li>
          <li><a href="#">Locations</a></li>

        </ul>
      </div>
    </div>


    <%
   //upon every reload, generate an entry key and set as parameter
    Key entryKey = KeyFactory.createKey("Entry", System.currentTimeMillis()+"");
    pageContext.setAttribute("entryKey", KeyFactory.keyToString(entryKey));

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    String tripKeyString = request.getParameter("tripKey");
    Key tripKey = KeyFactory.stringToKey(tripKeyString);
    pageContext.setAttribute("tripKey", tripKeyString);
    Query query = new Query("Entry").addFilter("tripPoster",
         Query.FilterOperator.EQUAL,
         tripKey).addSort("dateCreated", Query.SortDirection.DESCENDING);
    List<Entity> entries = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(10));


    //CODE TO TEST BLOBS, DELETE THIS ONCE TESTED:
    /*URL imageUrl = new URL("http://www.japantoday.com/images/size/x/2013/10/giraffe.jpg");
     InputStream input = imageUrl.openStream();
     ByteArrayOutputStream bos = new ByteArrayOutputStream();
     int next = input.read();
     while (next > -1) {
         bos.write(next);
         next = input.read();
     }
     bos.flush();
     byte[] result = bos.toByteArray();
     Blob sampleImage = new Blob(result);
     pageContext.setAttribute("blobKeySample",
                entry.getProperty(sampleImage.));*/





    if (entries.isEmpty()) {
%>
   <p>No Entries to Display</p>
  <script>console.log("no trips");</script>

  <%
} else { 
%>

<%
    for (Entity entry : entries) {
        pageContext.setAttribute("entry_title",
                entry.getProperty("title"));
                 pageContext.setAttribute("entry_description",
                entry.getProperty("description"));

              List<BlobKey> imageKeys = (List<BlobKey>) entry.getProperty("imageKeys");
              //String imageKey = imageKeys.get(0).getKeyString();

                //pageContext.setAttribute("entry_images",
                //imageKeys);

     // src="/getImage?blobKey=
        %>
          <script>console.log("entry here");</script>
          <div class="entry" style="padding-top: 100px; display:none">
        <input class="Entrytitle" value = "${fn:escapeXml(entry_title)}"></input>
        <input class="EntryDescription" value="${fn:escapeXml(entry_description)}"></input>
        <!--TESTING IMAGE replace blobKeySample with imageKey and uncomment imageKey-->
        <% 
        for(int i=0;i<imageKeys.size();i++){
          String imageKey = imageKeys.get(i).getKeyString();
          pageContext.setAttribute("entry_image", imageKey);
          %>
          <img class="Entryimages" src="/getImage?blobKey=${fn:escapeXml(entry_image)}"></img>
        <%
        }
        %>
        </div>
        <%
    }
%>
<blockquote></blockquote>
<%
    }
%>
<!--TODO: does this action do what it's supposed to because I doubt it-->
<form action="/addentry.jsp?entryKey=${fn:escapeXml(entryKey)}&tripKey=${fn:escapeXml(tripKey)}" method="post" id="addEntryForm">
  <!--In javascript, add "add entry" button as child to this form-->
</form>

    <script src="../js/util/jquery-1.10.2.min.js"></script>
    <script src="../js/util/bootstrap/js/bootstrap.js"></script>
    <script src="../js/util/bootstrap/js/bootstrap.min.js"></script>
    <!-- src for tags -->
    <script src="../js/util/tagsinputs/jquery.tagsinput.js"></script>
    <link rel="stylesheet" type="text/css" href="../js/util/tagsinputs/jquery.tagsinput.css"/>
    <!-- src for datepicker -->
    <script type="text/javascript" src="../js/util/node_modules/moment/moment.js"></script>
    <link rel="stylesheet" href="../js/util/datepicker/css/bootstrap-datetimepicker.min.css" />
    <script type="text/javascript" src="../js/util/datepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script src = "../js/Util.js"></script>
    <script src="../js/Tripview.js"></script>
  </body>
</html>
