MapHome = (function(){
  "use strict";
  var body = $(document.getElementById("body"));
    var main = $(document.getElementById("main"));
    main.css({
      'height':'100%',
      'width':'100%',
    })
    var contentDiv = $(document.getElementById("contentDiv"));
    contentDiv.css('padding-bottom','100px');
    var titleDiv = $(document.createElement('div'));
    
    titleDiv.addClass('page-header col-md-12');
    contentDiv.append(titleDiv);

    var title = $(document.createElement('h1'));
    title.text("Temp trip title")
    //TODO: uncomment this when we can get the title
    // var title = $(document.getElementById('tripTitle'));
    title.addClass('font-effect-fragile');
    title.css({
      'padding-top':'5px',
      'font-family':"Rancho', serif",
      'font-size':'100px',
      "display" : "block"
    })

    titleDiv.append(title);
    //TODO: uncomment this when we can get the date
    // var date=$(document.getElementById('tripDate'));
    var date=$(document.createElement('h2'));
    date.text("this is testing");
    title.append(date);

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
    location.css({
      "font-family": "serif",
      "font-size":"12",
      "display" : "block"
    });
    titleDiv.append(description);
    titleDiv.css({
      'padding-top':'10px'
    });


    var buttonDiv = $(document.createElement('div'));
    buttonDiv.addClass('col-md-4');
    buttonDiv.css({'padding-bottom':'8px'});
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
      'background-color':'#00868B',
    });


    //TODO: Uncomment below and remove buttonDiv when addEntryForm is ready
    // var addEntryForm = $(document.getElementById("addEntryForm"));
    // addEntryForm.append(addbtn);
    var buttonDiv = $(document.createElement('div'));
    buttonDiv.addClass('col-md-4 col-md-offset-2');
    buttonDiv.append(addbtn)
    contentDiv.append(buttonDiv);
    // console.log("action of form is: " + addEntryForm.attr("action"));
    // contentDiv.append(addEntryForm);

    //here goes the map outtermost div
    var mapCanvas = document.createElement('div');
    var $mapCanvas = $(mapCanvas);
    $mapCanvas.addClass("col-md-10 col-md-offset-1");
    $mapCanvas.css({
      'height':'500px',
      // 'width':'600px',
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
    loadTripsEx(map); //loads one trip at 0, 0
  }


  google.maps.event.addDomListener(window, "load", initialize);

  //makes one trip at 0, 0
  function loadEntry(map) {
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
             img: "images/stamp.png",
             lat: 0,
             lon: 0
         };

        trips.push(trip_obj);
    setMarkers(map, trips, "trips");

  }

  // function loadTrips(map, userkey) {
  //   $.getJSON('getTrips?userKey='+userKey, function(data) {
  //       var trips = [];
  //       var i = 0;
  //     for (var i =0; i < data.trips.length; i++) {
  //        var link = "/tripview.jsp?tripKey=" + data.trips[i].key;
  //        var src = "/getTripImage?tripKey=" + data.trips[i].key;
  //        console.log("trip json title"+data.trips[i].title);
  //        console.log("trip json key"+data.trips[i].key);

  //        //TODO - get from json or location
  //       //var location = getLatandLngFromInput(data.trips[i].location); //this is wrong

  //        var trip_obj = {
  //            title: data.trips[i].title,
  //            description: data.trips[i].description,
  //            location: data.trips[i].location,
  //            tripkey: data.trips[i].key,
  //            userkey: data.trips[i].userKey,
  //            depDate: data.trips[i].departDate,
  //            retDate: data.trips[i].returnDate,
  //            tags: data.trips[i].tags,
  //            index: i,
  //            link: link,
  //            img:src,
  //            lat: 0,
  //            lon: 0,
  //        };

  //       trips.push(trip_obj);
  //   }
  //   setMarkers(map, trips, "trips");
  // });
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
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < locations.length; i++) {
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
    var contentString = '<div id="content">'+
        '<div class="siteNotice>'+
        '</div>'+
        '<h1 id="firstHeading" class="firstHeading">'+spec.title+'</h1>'+
        '<div class="bodyContent">'+
        '<img class="infothumb thumbnail" src='+spec.src+'><img>'+ //Uncomment this line when ready
        // '<img class="thumbnail" src="images/2.jpg"><img>'+ 
        '<p>'+spec.description+'</p>'+
        '</div>'+
        '</div>';

    var infowindow = new google.maps.InfoWindow({
        content: contentString
    });
      google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map,marker);
    });
  }

})();