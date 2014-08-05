MapHome = (function(){
  var markers;
      //don't submit on enter:
     $(window).keydown(function(event){
    if( (event.keyCode == 13) && (validationFunction() == false) ) {
      event.preventDefault();
      return false;
    }
  });
	"use strict";
	var body = $(document.getElementById("body"));
	var main = $(document.getElementById("main"));
	main.css({
		'height':'100%',
		'width':'100%',
	})
	var contentDiv = $(document.getElementById("contentDiv"));
	contentDiv.css({
    'padding-top':'50px',
    'padding-bottom':'100px'
  });

	var addbtnDiv = $(document.createElement('div'));
	addbtnDiv.addClass("col-md-12");
	addbtnDiv.css({
		'padding-bottom':'8px'
	});
	contentDiv.append(addbtnDiv);
	var addbtn = $(document.createElement('button'));
	addbtn.addClass('btn btn-primary');
	addbtn.text("Add a Trip");
	addbtnDiv.append(addbtn);
	addbtn.attr({
		'data-target':"#addTrip",
		'data-toggle':"modal",
	});
  //addbtn.attr("id", "addTrip");
  addbtn.addClass("addTrip");
	addbtn.css({
		'background-color':Util.blue,
	});
   Util.addNewTrip(body);//initial add new trip modal
  // window.onload = function() {
  //   scrollTo(0,0);
  // }
 //here goes the map outtermost div
 var mapCanvas = document.createElement('div');
 var $mapCanvas = $(mapCanvas);
 $mapCanvas.addClass("col-md-10 col-md-offset-1");
 $mapCanvas.attr("id", "map-canvas");
 $mapCanvas.css({
 	'height':'500px',
  'margin-bottom':'20px'
 })
 contentDiv.append($mapCanvas);
 var userKey = Util.getQueryVariable("userKey");
  if(userKey != null) {
      // console.log("user key was not null, setting param")
      var tripbutton = $(document.getElementById("trips_button"));
      tripbutton.attr("href", "/homepage.jsp?userKey=" + userKey);
      var mapbutton = $(document.getElementById("maps_button"));
      mapbutton.attr("href", "/MapHome.html?userKey=" + userKey);
  }
  var tripTitles=[],
      tripPlaces=[];



  /*
function that initialize the map in the page
*/
	function initialize() {
		var myOptions = {
			zoom: 10,
			center: new google.maps.LatLng(-34.397, 150.644),
			mapTypeId: google.maps.MapTypeId.ROADMAP
		}
		var map = new google.maps.Map(
			mapCanvas,
			myOptions);
		//setMarkers(map, beaches);
    // Create the search box and link it to the UI element.
     markers = [];
    var input = (document.getElementById('pac-input'));
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    var searchBox = new google.maps.places.SearchBox((input));
      // Listen for the event fired when the user selects an item from the
    // pick list. Retrieve the matching places for that item.
    google.maps.event.addListener(searchBox, 'places_changed', function() {
      var places = searchBox.getPlaces();

      if (places.length == 0) {
        return;
      }
     /*for (var i = 0, marker; marker = markers[i]; i++) {
        marker.setMap(null);
      }*/

      // For each place, get the icon, place name, and location.
      markers = [];
      var bounds = new google.maps.LatLngBounds();
      for (var i = 0, place; place = places[i]; i++) {
          var location = {
          lat: place.geometry.location.latitude,
          lon: place.geometry.location.longitude,
          title: place.name,
          description: "",
          link: "#",
          depDate: "start",
          retDate: "end",
          tags: "",
          img: "images/stamp.png",
          location: place.name,
          index: i,
          tripKey: "",
          userKey: userKey,
        };
       
        var image = {
          url: "http://maps.google.com/mapfiles/ms/micons/ltblu-pushpin.png",
          size: new google.maps.Size(71, 71),
          origin: new google.maps.Point(0, 0),
          anchor: new google.maps.Point(17, 34),
          scaledSize: new google.maps.Size(40, 40),
          shadow: "http://maps.google.com/mapfiles/ms/micons/ltblu-pushpins.png"
        };

        // Create a marker for each place.
        var marker = new google.maps.Marker({
          map: map,
          icon: image,
          title: place.name,
          position: place.geometry.location,
          animation: google.maps.Animation.DROP,
        });

        markers.push(marker);
        setNewTripInfoWindow(map, marker, location);

        bounds.extend(place.geometry.location);
      }

      map.fitBounds(bounds);
    });

    // Bias the SearchBox results towards places that are within the bounds of the
    // current map's viewport.
    google.maps.event.addListener(map, 'bounds_changed', function() {
      var bounds = map.getBounds();
      searchBox.setBounds(bounds);
    });
    var trips = [];
    // var tripTitles = [];
    // var tripPlaces = [];
	  $.getJSON('getTrips?userKey='+userKey, function(data) {
      var i = 0;
      for (var i =0; i < data.trips.length; i++) {
        var link = "/MapEntries.html?tripKey=" + data.trips[i].key;
        var src = "/getTripImage?tripKey=" + data.trips[i].key;
        console.log("trip json title"+data.trips[i].title);
        console.log("trip json key"+data.trips[i].key);

        //TODO - get from json or location
       //var location = getLatandLngFromInput(data.trips[i].location); //this is wrong
       // tripTitles.push(data.trips[i].title);
       // tripPlaces.push(data.trips[i].location);
       var trip_obj = {
          title: data.trips[i].title,
          description: data.trips[i].description,
          location: data.trips[i].location,
          tripkey: data.trips[i].key,
          userkey: data.trips[i].userKey,
          depDate: data.trips[i].departDate,
          retDate: data.trips[i].returnDate,
          tags: data.trips[i].tags,
          index: i,
          link: link,
          img: src,
          lat: parseFloat(data.trips[i].latitude),
          lon: parseFloat(data.trips[i].longitude),
        };

        trips.push(trip_obj);
      }
      setMarkers(map, trips, "trips");
      createSearchBar();
      addTripSearchResults(trips);
    });

  var interval = setInterval(function(){
   if (trips != undefined){
     console.log("not undefined yay");
     clearInterval(interval);
   }
    }, 200);

    function createSearchBar(){
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
        'color':"white",
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
        var results = searchTrips(trips, searchString);
        addTripSearchResults(results);
        console.log("added search results");
        setMarkers(map, results, "trips");
        
      });
      searchInput.keyup(function () {
        //hide all div and show the right ones
        var searchString = searchInput.val();
        var results = searchTrips(trips, searchString);
        addTripSearchResults(results);
        //setMarkers(map, results, "trips");
      });
      searchInput.change(function () {
        var searchString = searchInput.val();
        var results = searchTrips(trips, searchString);
        addTripSearchResults(results);
        //setMarkers(map, results, "trips");
      });
    }
   //loadTripsEx(map);
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

//TODO: fix css of this?? It looks like bootstrap isn't really working hmm
function addTripSearchResults(tripResults) {
  console.log("trip results size is" + tripResults.length);
  //clear previous results div
  $("#results").remove();
  var resultsDiv = $(document.createElement("div"));
  resultsDiv.attr("id", "results");
  resultsDiv.attr("class", "list-group");
  for(var i = 0; i < tripResults.length; i++) {
    var link = $(document.createElement("a"));
    link.attr("href", tripResults[i].link);
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
    heading.text(tripResults[i].title);
    var text= $(document.createElement("p"));
    text.attr("class", "list-group-item-text");
    text.css({
      "padding" : "10px",
      "color" : Util.mid_blue,
    });

    //Why are none of these working???:
    text.text(tripResults[i].location);
    //TODO: image thumbnail and view button
    var thumb = $(document.createElement("img"));
    thumb.attr("src", tripResults[i].img);
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

  function searchTrips(trips,searchString) {
    var results = []; 
    // Deletes all markers in the array by removing references to them.
    deleteMarkers();

    for(var i = 0; i < trips.length; i++) {
      //make uppercase (case insensitive)
      var curtrip =trips[i];
      var place = curtrip.location.toUpperCase();
      var title = curtrip.title.toUpperCase();
      //check for a match
      if(place.indexOf(searchString.toUpperCase()) > -1) {
       results.push(curtrip);
     }
     else if(title.indexOf(searchString.toUpperCase()) > -1) {
      results.push(curtrip);
     }
    }

    return results
  }


  google.maps.event.addDomListener(window, "load", initialize);

  
// //makes one trip at 0, 0
// function loadTripsEx(map) {
// 	var trips = [];
// 	var trip_obj = {
// 		title: "trip title",
// 		description: "description bla bla bla bla asdfkjasdfkjhasdf",
// 		location: "new york",
// 		tripkey: "na",
// 		userkey: "na",
// 		depDate: "now",
// 		retDate: "later",
// 		tags: "great",
// 		index: 0,
// 		link: "google.com",
// 		img: "images/4.jpg",
// 		lat: 0,
// 		lon: 0
// 	};

// 	trips.push(trip_obj);
// 	setMarkers(map, trips, "trips");

// // }

// function loadTrips(map, trips) {

//    console.log("get trips about to set markers");
//    setMarkers(map, trips, "trips");
//    console.log("set markers");
// }

function getLatandLngFromInput(input){
	var autocomplete = new google.maps.places.Autocomplete(input);
	var place = autocomplete.getPlace();
	var latitude = place.geometry.location.lat();
	var longitude = place.geometry.location.lng();
	var location = [latitude, longitude];
	return location;
}

function loadEntries(tripkey) {
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
      	tripkey: data.entries[i].tripKey,
          entrykey: data.entries[i].key, //???this is coming as undefined...
          index: i,
          photos: photos,
          lat: 0,
          lon: 0,
      };
      console.log("entryobj json title"+entry_obj.title);
      console.log("entryobj json key"+entry_obj.entryKey);
      console.log("size of photos in this entry is"+entry_obj.photos.length);
      entries.push(entry_obj);
  }
  setMarkers(map, entries, "entry");
});
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

function setNewTripInfoWindow(map, marker, spec) {
var thumbW,thumbH;
var d = new Date();
var id = d.getTime() + spec.index;
var buttonid = "addTrip" + id;

  var modal = Util.editBtn("NewTrip", spec); //modal id = spec.title+spec.location
    function openModal(){
      console.log("open modal");
      modal.modal({show:true});
    }
    

  var contentString = '<div id="infoWindowDiv>'
  +'<link rel="stylesheet" type="text/css" href="./3DHoverEffects/css/style1.css" />'+
  ' <script src="../js/util/jquery-1.10.2.min.js"></script>'+
          '<script type="text/javascript" src="./3DHoverEffects/js/modernizr.custom.69142.js"></script>' +
  ' <script type="text/javascript" src="js/util/bootstrap/js/bootstrap.min.js"></script>'+
  ' <script type="text/javascript" src="js/util/datepicker/js/bootstrap-datetimepicker.min.js"></script>'+
  '<link rel="stylesheet" href="js/util/datepicker/css/bootstrap-datetimepicker.min.css" />'+
  '<script type="text/javascript" src="js/util/node_modules/moment/moment.js"></script>'+
  '<script src="js/jquery.validate.js"></script>'+
  '<h1 id="firstHeading" class="firstHeading" style="font:1em">'+spec.title+'</h1>'+
  '<p><button class="btn btn-primary" id="'+ buttonid + '"'+
  '>Add Trip</button></p>'+ 
  '<style>.btn-primary, .btn-primary:hover, .btn-primary:active { background-color: #3E546A; border: #3E546A; }</style>'+
  '</div>';

      var infowindow = new google.maps.InfoWindow({
       content: contentString
     });


google.maps.event.addListener(infowindow,'domready',function(){
  console.log("domready yay");
     $('#'+buttonid).click(function() {
        console.log("clicked add trip!");
        openModal();
     });
  });

  google.maps.event.addListener(marker, 'click', function() {
  infowindow.open(map,marker);
  });
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
	function openModal(){
		modal.modal({show:true});
	}
  var id = spec.title+spec.location;
  var editid= "editbtn"+id,
      deleteformid = "deleteform"+id,
      deleteid = "deletebtn"+id;
  var contentString = '<link rel="stylesheet" type="text/css" href="./3DHoverEffects/css/style1.css" />'+
          '<script type="text/javascript" src="./3DHoverEffects/js/modernizr.custom.69142.js"></script>' +
  ' <script src="js/util/bootstrap/js/bootstrap.min.js"></script>'+
  '<h1 id="firstHeading" class="firstHeading" style="font:1em">'+spec.title+'</h1>'+
  '<div id="grid" class="main">'+
      '<div class="view">'+
          '<div class="view-back">'+
             '<a href='+spec.link+'>'+
     			'<button class="btn btn-primary" style="margin-top: 20px">View</button>'+
     			'</a>' +
             '<a><button class="btn btn-primary" id="'+ editid + '" style=" margin-top: 30px">Edit</button></a>'+
             '<form id="'+ deleteformid +' " method="post" action="/deleteTrip?frommap=True&tripKey='+spec.tripkey+'&userKey='+spec.userkey+'"><a><button class="btn btn-primary" id="'+ deleteid +' " margin-top: 40px">Delete</button></a></form>'+
          '</div>'+
   	 '<img src='+ spec.img + ' style="width: 338"/>'+
      '</div>'+
      '</div>'+ //end of grid
                 '<p>' + spec.description + '</p>'+
                 '<style>.btn-primary, .btn-primary:hover, .btn-primary:active { background-color: rgb(110, 130,170); border: rgb(110, 130,170); margin-left:95px}</style>';

console.log("set content string");
      var infowindow = new google.maps.InfoWindow({
     	 content: contentString
     });
console.log("listeners");
  google.maps.event.addListener(infowindow,'domready',function(){
    console.log("dom is ready");
    $(document.getElementById(editid)).click(function(){
      openModal(id);
    });
  });

  google.maps.event.addListener(marker, 'click', function() {
  	// function openModal(id){
  	// 	$(document.getElementById(id)).modal({show:true});
  	// }

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
