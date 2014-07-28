MapHome = (function(){
	"use strict";
	var body = $(document.getElementById("body"));
	var main = $(document.getElementById("main"));
	main.css({
		'height':'100%',
		'width':'100%',
	})
	var contentDiv = $(document.getElementById("contentDiv"));
	contentDiv.css('padding-top','50px');

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
	addbtn.css({
		'background-color':'#00868B',
	});
   Util.addNewTrip(body);//initial add new trip modal

   //here goes the map outtermost div
   var mapCanvas = document.createElement('div');
   var $mapCanvas = $(mapCanvas);
   $mapCanvas.addClass("col-md-10 col-md-offset-1");
   $mapCanvas.css({
   	'height':'500px',
   })
   contentDiv.append($mapCanvas);
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
		//loadTripsEx(map); //loads one trip at 0, 0
		var userKey = Util.getQueryVariable("userKey");
		if(userKey != null) {
        // console.log("user key was not null, setting param")
        var tripbutton = $(document.getElementById("trips_button"));
        tripbutton.attr("href", "/homepage.jsp?userKey=" + userKey);
        var mapbutton = $(document.getElementById("maps_button"));
        mapbutton.attr("href", "/MapHome.html?userKey=" + userKey);
    }
    loadTrips(map, userKey);
}


google.maps.event.addDomListener(window, "load", initialize);

//makes one trip at 0, 0
function loadTripsEx(map) {
	var trips = [];
	var trip_obj = {
		title: "trip title",
		description: "description",
		location: "new york",
		tripkey: "na",
		userkey: "na",
		depDate: "now",
		retDate: "later",
		tags: "great",
		index: 0,
		link: "google.com",
		img: "images/2.jpg",
		lat: 0,
		lon: 0
	};

	trips.push(trip_obj);
	setMarkers(map, trips, "trips");

}

function loadTrips(map, userKey) {
	$.getJSON('getTrips?userKey='+userKey, function(data) {
		var trips = [];
		var i = 0;
		for (var i =0; i < data.trips.length; i++) {
			var link = "/tripview.jsp?tripKey=" + data.trips[i].key;
			var src = "/getTripImage?tripKey=" + data.trips[i].key;
			console.log("trip json title"+data.trips[i].title);
			console.log("trip json key"+data.trips[i].key);

      //TODO - get from json or location
     //var location = getLatandLngFromInput(data.trips[i].location); //this is wrong

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
     	img:src,
     	lat: parseFloat(data.trips[i].latitude),
     	lon: parseFloat(data.trips[i].longitude),
     };

     trips.push(trip_obj);
 }
 setMarkers(map, trips, "trips");
});
}

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
var bounds = new google.maps.LatLngBounds();
for (var i = 0; i < locations.length; i++) {
if(type == "entry") { /* anything special for entries? */ }
if(type == "trip") { /* anything special for entries? */ }
	var myLatLng = new google.maps.LatLng(locations[i].lat, locations[i].lon);
var marker = new google.maps.Marker({
	position: myLatLng,
	map: map,
	title: locations[i].title,
	zIndex: locations[i].index,
});
bounds.extend(myLatLng);
setInfoWindow(map, marker, locations[i], "google.com");
}
map.fitBounds(bounds);
}

//TODO: link or button to trip or entry, make title, img, and description as a spec so we
//don't need to pass a long list of params 
//TODO: maybe add photo previews
/*
the info window for each pin.
*/
function setInfoWindow(map, marker, spec, link) {
// var img = new Image();
// img.src=spec.img;
// var imgH=img.height;
// var imgW=img.width;
var thumbW,thumbH;
// if(imgW/imgH>1){
// thumbW=300;
// thumbH = thumbW*imgH/imgW;
// }else{
// thumbH=255;
// thumbW = thumbH*imgW/imgH;
// }
// function getWidthAndHeight() {
//     return ;
// }

var modal = Util.editBtn("Trip",spec);//modal id = spec.title+spec.location
	function openModal(id){
		console.log("open modal");
		$(document.getElementById(id)).modal({show:true});
	}
var id = spec.title+spec.location;
var contentString = '<link rel="stylesheet" type="text/css" href="../3DHoverEffects/css/demo.css" />' +
        '<link rel="stylesheet" type="text/css" href="../3DHoverEffects/css/style_common.css" />'+
        '<link rel="stylesheet" type="text/css" href="../3DHoverEffects/css/style1.css" />'+
        '<script type="text/javascript" src="../3DHoverEffects/js/modernizr.custom.69142.js"></script>' +
        /*'<link href="js/util/bootstrap/css/bootstrap.min.css" rel="stylesheet">'+
' <script src="js/util/bootstrap/js/bootstrap.min.js"></script> <div id="content">'+*/
'<h1 id="firstHeading" class="firstHeading" style="font:1em">'+spec.title+'</h1>'+
'<div id="grid" class="main">'+
    '<div class="view">'+
        '<div class="view-back">'+
            '<span data-icon="A">566</span>'+
            '<span data-icon="B">124</span>'+
           '<a href="http://www.flickr.com/photos/ag2r/5439506585/in/photostream">→</a>'+
        '</div>'+
 	 '<img src="'+ spec.img + '/>'+
    '</div>'+
    '/div'+ //end of grid

    '<script> Modernizr.load({'+
				'test: Modernizr.csstransforms3d && Modernizr.csstransitions,'+
				'yep : ["http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js","../3DHoverEffects/js/jquery.hoverfold.js"],'+
				'nope: "css/fallback.css",'+
				'callback : function( url, result, key ) {'+
						
					'if( url === "../3DHoverEffects/js/jquery.hoverfold.js" ) {'+
				'$( "#grid" ).hoverfold();'+
					'}'+
				'}'+
			'}); </script>';

    var infowindow = new google.maps.InfoWindow({
   	// content: contentString
   });
// function openModal(id){
// $(document.getElementById(id)).modal({show:true});
// }

google.maps.event.addListener(marker, 'click', function() {
	function openModal(id){
		$(document.getElementById(id)).modal({show:true});
	}
	infowindow.setContent(contentString);
  // $(document.getElementById("editBtn"+spec.title+spec.location)).click(function(){
     //       //open a modal to edit info about the photo
     //       modal.modal({show:true});
     //   });

//uncomment this for thumbnail stuff:
/*var img = $(document.getElementById(spec.img));
img.onload;
var imgH=img.height();
var imgW=img.width();
console.log("img height is"+imgH);
var thumbW,thumbH;
if(imgW/imgH>1){
	thumbW=300;
	thumbH = thumbW*imgH/imgW;
}else{
	thumbH=255;
	thumbW = thumbH*imgW/imgH;
}
img.css({
	'height':thumbW+'px',
	'width':thumbH+'px',
});*/
infowindow.open(map,marker);

});

}

})();
