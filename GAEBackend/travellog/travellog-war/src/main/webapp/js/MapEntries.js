MapHome = (function(){
  "use strict";
  var markers;
  var userKey = Util.getQueryVariable("userKey");
  if(userKey != null) {
    console.log("user key was not null, setting param")
  }
  var tripKey = Util.getQueryVariable("tripKey");
  var body = $(document.getElementById("body"));
  var main = $(document.getElementById("main"));
  main.css({
    'height':'100%',
    'width':'100%',
  })
  var tripbutton=$(document.getElementById("trips_button"))
  tripbutton.attr("href", "/MapHome.html?userKey=" + userKey);
  var entriesbutton=$(document.getElementById("entries_list"))
  entriesbutton.attr("href","/tripview.jsp?tripKey=" + tripKey)

  var contentDiv = $(document.getElementById("contentDiv"));
  contentDiv.css({
    'padding-top':'10px',
    'padding-bottom':'100px'
  });
  var titleDiv = $(document.createElement('div'));
  
  titleDiv.addClass('page-header col-md-12');
  contentDiv.append(titleDiv);

  var title = $(document.createElement('h1'));
  title.addClass('font-effect-fragile');
  title.css({
    'padding-top':'5px',
    'font-family':"Rancho', serif",
    'font-size':'100px',
    "display" : "block"
  });


  titleDiv.append(title);
  var date=$(document.createElement('h2'));

  titleDiv.append(date);

  var location=$(document.createElement('small'));
  //TODO: uncomment this when we can get the location
  // var location = $(document.getElementById('tripLocation'));
  location.css({
    "font-family": "serif",
    "font-size":"14",
    "display" : "block"
  });
  titleDiv.append(location);
  //TODO: uncomment this when we can get the description
  // var description = $(document.getElementById('tripDescription'));
  var description=$(document.createElement('small'));
  description.css({
    "font-family": "serif",
    "font-size":"12",
    "display" : "block"
  });
  titleDiv.append(description);
  titleDiv.css({
    'padding-top':'10px'
  });
  var tripspec={
    title:title,
    location:location,
    description:description,
    date:date,
  }
  Util.getTrip(tripKey, userKey, tripspec);

  var buttonDiv = $(document.createElement('div'));
  buttonDiv.addClass('col-md-4');
  buttonDiv.css({'padding-bottom':'10px'});
  var addEntryForm = $(document.createElement("form"));
  addEntryForm.attr({
    'method':'post',
    'action':"/addentry.jsp?tripKey="+tripKey,
  });
  var addbtn = $(document.createElement('button'));
  addbtn.addClass('btn btn-primary');
  addbtn.attr({
    'data-target':"#addEntryDiv",
    'data-toggle':"modal",
    'left':'0px', 
    'position':'relative'
  });
  addbtn.text("Add Entry");
  addbtn.css({
    'background-color':Util.blue,
  });


  //TODO: Uncomment below and remove buttonDiv when addEntryForm is ready
  addEntryForm.append(addbtn);
  buttonDiv.append(addEntryForm)
  contentDiv.append(buttonDiv);
    // console.log("action of form is: " + addEntryForm.attr("action"));
    // contentDiv.append(addEntryForm);

  //here goes the map outtermost div
 var mapCanvas = document.createElement('div');
 var $mapCanvas = $(mapCanvas);
 $mapCanvas.addClass("col-md-10 col-md-offset-1");
 $mapCanvas.css({
  'height':'500px',
  'margin-bottom':'20px'
 })
 contentDiv.append($mapCanvas);
 var entries=[];
 markers = [];


  /*
function that initialize the map in the page
*/
  function initialize() {
     markers = [];
    var myOptions = {
      zoom: 10,
      center: new google.maps.LatLng(-34.397, 150.644),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    markers = [];
    var map = new google.maps.Map(
      mapCanvas,
      myOptions);
    //setMarkers(map, beaches);
    //loadTripsEx(map); //loads one trip at 0, 0
    var userKey = Util.getQueryVariable("userKey");
    if(userKey != null) {
        // console.log("user key was not null, setting param")
        var tripbutton = $(document.getElementById("trips_button"));
        tripbutton.attr("href", "/homepage.jsp?userKey=" + userKey);
        var mapbutton = $(document.getElementById("maps_button"));
        mapbutton.attr("href", "/MapHome.html?userKey=" + userKey);
    }
    $.getJSON('getEntries?tripKey='+tripKey, function(data) {
      var entries = [];
      var i = 0;
      for (var i =0; i < data.entries.length; i++) {
       // var link = "/tripview.jsp?tripKey=" + data.trips[i].key;
       // var src = "/getTripImage?tripKey=" + data.trips[i].key;
       console.log("entry json title"+data.entries[i].title);
       console.log("entry json key"+data.entries[i].key);

       var photos = [];
       for(var j = 0; j < data.entries[i].photos.length; j++) {
          var photo = {
            title: data.entries[i].photos[j].title,
            description: data.entries[i].photos[j].description,
            photoKey: data.entries[i].photos[j].key,
            blobKey: data.entries[i].photos[j].blobKey,
            index: j,
            link: "/getImageFromBlobKey?blobKey="+data.entries[i].photos[j].blobKey,
          };
          photos.push(photo);
       }
        //var location = getLatandLngFromInput(data.entries[i].location); //TODO make sure there is location for entries
      var entry_obj = {
            title: data.entries[i].title,
            description: data.entries[i].description,
            location: data.entries[i].location,
            link: '/entryPage.jsp?entryKey='+data.entries[i].key,

            tripkey: data.entries[i].tripKey,
            entrykey: data.entries[i].key, //???this is coming as undefined...
            index: i,
            photos: photos,
            lat: data.entries[i].latitude,
            lon: data.entries[i].longitude,
            img: photos.length>0?photos[0].link:"http://imx.solid-run.com/wiki/images/7/75/No_image_available.png"
      };
      console.log("entryobj json title"+entry_obj.title);
      console.log("entryobj json key"+entry_obj.entryKey);
      console.log("size of photos in this entry is"+entry_obj.photos.length);
      entries.push(entry_obj);
    }
    setMarkers(map, entries, "entry");
    createSearchBar(entries);
    addEntriesSearchResults(entries);
  });

    var interval = setInterval(function(){
        if (entries != undefined){
        console.log("not undefined yay");
        clearInterval(interval);
      }
    }, 200);

    function createSearchBar(entries){
      /**HERE IS THE SEARCH BAR~~**/
      var searchForm = $(document.createElement('form'));
      searchForm.attr('role','search');
      searchForm.attr('name','selfsubmit');

      searchForm.addClass('row col-md-10 col-md-offset-1')
      var searchdiv = $(document.createElement('div'));
      searchdiv.addClass('form-group col-md-4');
      var searchInput=$(document.createElement('input'));
      searchInput.attr({
        'id':'searchInput',
        'type':'text',
        'placeholder':'Search by Title/Place',
      });
      searchInput.addClass('form-control col-md-4');
      searchInput.attr("autocomplete", "on");
      var searchbtn = $(document.createElement('button'));
      searchbtn.addClass("btn btn-default");
      searchbtn.attr('type','submit');
      searchbtn.text("Show search results on the Map");
      searchbtn.css({
        'background-color':Util.blue,
        'color':'white'
      });
      searchdiv.append(searchInput);
      searchForm.append(searchdiv).append(searchbtn);
      contentDiv.append(searchForm);
      searchInput.focus(function(e){
      e.preventDefault(); //prevent it from scrolling the page (maybe??)
      });
      // $("#searchInput").autocomplete({
      //   source:tripTitles.concat(tripPlaces)
      // });
      // $(".ui-autocomplete").attr({
      //   'z-index':10000000000,
      //   'display':'block'
      // })
      //TODO: show up the match pins on map:
      searchbtn.click(function(e){
        e.preventDefault();
        var searchString = searchInput.val();
        var results = searchEntries(entries, searchString);
        addEntriesSearchResults(results);
        console.log("added search results");
        setMarkers(map, results, "entries");
        
      });
      searchInput.keyup(function () {
        //hide all div and show the right ones
        var searchString = searchInput.val();
        var results = searchEntries(entries, searchString);
        addEntriesSearchResults(results);
        //setMarkers(map, results, "trips");
      });
      searchInput.change(function () {
        var searchString = searchInput.val();
        var results = searchEntries(entries, searchString);
        addEntriesSearchResults(results);
        //setMarkers(map, results, "trips");
      });
    }
   //loadTripsEx(map);
  }
  
  function addEntriesSearchResults(entriesresults) {
    // console.log("trip results size is" + tripResults.length);
  //clear previous results div
  $("#results").remove();
  var resultsDiv = $(document.createElement("div"));
  resultsDiv.attr("id", "results");
  resultsDiv.attr("class", "list-group");
  for(var i = 0; i < entriesresults.length; i++) {
    var link = $(document.createElement("a"));
    link.attr("href", entriesresults[i].link);
    link.addClass("col-md-12");
    link.css({
      "padding" : "10px",
      "min-height":"20px",
      "background-color" : "white",
      "border-radius" : "10px",
      "margin-bottom" : "10px",
    });
    $('a:hover').css('text-decoration','none');
    var heading = $(document.createElement("h4"));
    heading.attr("class", "list-group-item-heading");
    var purple = "#504552";
    heading.css({
      "background-color" : Util.mid_blue,
      "padding" : "10px",
      "height" : "100%",
      "color" : "white",
    });
    //Why are neither of these working: ???
    heading.text(entriesresults[i].title);
    var text= $(document.createElement("p"));
    text.attr("class", "list-group-item-text");
    text.css({
      "padding" : "10px",
      "color" : purple,
    });

    //Why are none of these working???:
    text.text(entriesresults[i].location);
    //TODO: image thumbnail and view button
    var thumb = $(document.createElement("img"));
    thumb.attr("src", entriesresults[i].img);
    thumb.css({
      "height" : "70px",
      "width" : "auto", 
      "float" : "right"
    });
   text.append(thumb); //TODO
   link.append(heading);
   link.append(text);
   resultsDiv.append(link);
  }
  resultsDiv.addClass('row col-md-10 col-md-offset-1');
  contentDiv.append(resultsDiv);
  }

  function searchEntries(entries, searchString) {
    var results = [];
    //remove all markers
    // Deletes all markers in the array by removing references to them.
    deleteMarkers();

    for(var i = 0; i < entries.length; i++) {
      var curentry = entries[i];
      //make uppercase (case insensitive)
      var place = curentry.title.toUpperCase();
      var title = curentry.location.toUpperCase();
      //check for a match
      if(place.indexOf(searchString.toUpperCase()) > -1) {
       results.push(curentry);
     }
     else if(title.indexOf(searchString.toUpperCase()) > -1) {
      results.push(curentry);
     }
    }

    return results
  }

    google.maps.event.addDomListener(window, "load", initialize);


  function getLatandLngFromInput(input){
    var autocomplete = new google.maps.places.Autocomplete(input);
    var place = autocomplete.getPlace();
    var latitude = place.geometry.location.lat();
    var longitude = place.geometry.location.lng();
    var location = [latitude, longitude];
    return location;
  }

  function setMarkers(map, locations, type) {
  //TODO: custom pins
  var iconurl;//we should have different icons for new trip, trip, entry, and new entry.
  var bounds = new google.maps.LatLngBounds();
  for (var i = 0; i < locations.length; i++) {
    if(locations[i].lat === " " || locations[i].lon === " ") continue; //skip empty lat/longitudes, markers won't show up for those
    if(locations[i].lat == null || locations[i].lon == null) continue; 
    if(locations[i].location == null || locations[i].location === "") continue;
    console.log("marker again");
  if(type == "entry") { /* anything special for entries? */ }
  else if(type == "trip") { /* anything special for trips? */ }
  else if(type == "newTrip"){/**/};
  var myLatLng = new google.maps.LatLng(locations[i].lat, locations[i].lon);
  var marker = new google.maps.Marker({
    position: myLatLng,
    map: map,
    icon: {url: "./images/pushpin.png", scaledSize: new google.maps.Size(40, 40) },
    title: locations[i].title,
    zIndex: 1,
    animation: google.maps.Animation.DROP,
  });
  bounds.extend(myLatLng);
   console.log("setting info window");
  setInfoWindow(map, marker, locations[i], "google.com");
  console.log("set info window");
  }

  map.fitBounds(bounds);
}
  // Sets the map on all markers in the array.
  function setAllMap(map) {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(map);
    }
  }

  // Removes the markers from the map, but keeps them in the array.
  function clearMarkers() {
    setAllMap(null);
  }

  // Shows any markers currently in the array.
  function showMarkers() {
    setAllMap(map);
  }

  // Deletes all markers in the array by removing references to them.
  function deleteMarkers() {
    clearMarkers();
    markers = [];
  }
  //TODO: link or button to trip or entry, make title, img, and description as a spec so we
  //don't need to pass a long list of params 
  //TODO: maybe add photo previews
  /*
  the info window for each pin.
  */
  function setInfoWindow(map, marker, spec, link) {
    var thumbW,thumbH;

    var modal = Util.editBtn("Trip",spec);//modal id = spec.title+spec.location
      function openModal(id){
        console.log("open modal");
        $(document.getElementById(id)).modal({show:true});
      }
    var id = spec.title+spec.location;

 var endSnippetIndex = Math.min(500, spec.description.length);
  var description_snippet = (spec.description);
 /* var description_snippet = description_snippet.substring(0, endSnippetIndex);
  description_snippet+="...";*/
    var contentString = '<link rel="stylesheet" type="text/css" href="./3DHoverEffects/css/style1.css" />'+
            '<script type="text/javascript" src="./3DHoverEffects/js/modernizr.custom.69142.js"></script>' +
    ' <script src="js/util/bootstrap/js/bootstrap.min.js"></script>'+
    '<h1 id="firstHeading" class="firstHeading" style="font:1em">'+spec.title+'</h1>'+
    '<div id="grid" class="main">'+
        '<div class="view">'+
            '<div class="view-back">'+
               '<a href='+spec.link+'>'+
            '<button class="btn btn-primary" style="margin-left: 93px; margin-top: 20px;background-color:rgb(110, 130,170)">View</button>'+
            '</a>' +
               // '<a><button class="btn btn-primary" id=editBtn'+spec.img+' style="margin-left: 90px; margin-top: 30px" onclick="openModal()">Edit</button></a>'+
               // '<a><button class="btn btn-primary" id=deleteBtn'+spec.img+' style="margin-left: 90px; margin-top: 40px" onclick="openModal()">Delete</button></a>'+
            '</div>'+
       '<img src='+ spec.img + ' style="width: 338"/>'+
        '</div>'+
        //'<p>' + description_snippet + '</p></div>'//+
        '</div>' //end of grid
                
        var infowindow = new google.maps.InfoWindow({
         content: contentString
       });


    google.maps.event.addListener(marker, 'click', function() {
      function openModal(id){
        $(document.getElementById(id)).modal({show:true});
      }

      Modernizr.load({
            test: Modernizr.csstransforms3d && Modernizr.csstransitions,
            yep : ["http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js","3DHoverEffects/js/jquery.hoverfold.js"],
            nope: "3DHoverEffects/css/fallback.css",
            callback : function( url, result, key ) {
              if( url === "3DHoverEffects/js/jquery.hoverfold.js" ) {
            $( "#grid" ).hoverfold();
              }
            }
          }); 
    infowindow.open(map,marker);

    });

  }



})();