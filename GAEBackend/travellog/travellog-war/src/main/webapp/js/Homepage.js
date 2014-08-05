Homepage = (function(){

  //don't submit on enter:
     $(window).keydown(function(event){
    if( (event.keyCode == 13) ) {
      event.preventDefault();
      return false;
    }
  });
     
    //if there is a user key stored, get it and set as parameter for link
    // console.log("about to get user key");
    "use strict";
    var userKey = Util.getFromLocalStorage("userKey");
    if(userKey != null) {
        // console.log("user key was not null, setting param")
        var tripbutton = $(document.getElementById("trips_button"));
        tripbutton.attr("href", "/homepage.jsp?userKey=" + userKey);
        var mapsbutton = $(document.getElementById("maps_button"));
        mapsbutton.attr("href","/MapHome.html?userKey="+userKey);
    }
    var body = $(document.getElementById("body"));
    var main = $(document.getElementById("main"));
    var contentDiv = $(document.createElement('div'));
    main.append(contentDiv);
    contentDiv.addClass("row");
    contentDiv.css('padding-top','50px');

    //button for adding a new trip
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
        'background-color':Util.blue,
    });
    Util.addNewTrip(body); //initial add new trip modal

    //grid for all trips
    var tripGrid = $(document.createElement('div'));
    contentDiv.append(tripGrid);
    tripGrid.addClass("row col-md-offset-1");
    
    //get all trips request
    $.getJSON('getTrips?userKey='+userKey, function(data) {
           var trips = [];
           var i = 0;
         for (var i =0; i < data.trips.length; i++) {
            var link = "/tripview.jsp?tripKey=" + data.trips[i].key;
            var src = "/getTripImage?tripKey=" + data.trips[i].key;
            console.log("trip json title"+data.trips[i].title);
            console.log("trip json key"+data.trips[i].key);

            var trip_obj = {
                title: data.trips[i].title,
                description: data.trips[i].description,
                location: data.trips[i].location,
                longitude: data.trips[i].longitude,
                latitude: data.trips[i].latitude,
                tripkey: data.trips[i].key,
                userkey: data.trips[i].userKey,
                depDate: data.trips[i].departDate,
                retDate: data.trips[i].returnDate,
                tags: data.trips[i].tags,
                index: i,
                link: link,
                img:src
            };

           trips.push(trip_obj);
           tripGrid.append(tripPreview(trip_obj));
       }
    });

    /**
    generate trip preview for single trip. Takes in spec of the trip
    spec includes trip title, description, location, tripKey, userKey, depDate, retDate, tags, index link and img
    returns the outtest div
    */
    function tripPreview(spec){
        var colDiv = $(document.createElement('div'));
        colDiv.addClass("col-md-5");
        colDiv.css({
            'background-color':Util.light_blue,
            'height':'400px',
            'margin-right':'40px',
            'margin-bottom':'40px',
            'border-radius':'5px'
        });
        var thumbDiv = $(document.createElement('div'));
        thumbDiv.css({
            'top':'3%',
            'left':'10%',
            'height':'60%',
            'width':'80%',
            'position':'absolute',
            'background':'url(' + spec.img + ')',
            'background-size':'contain',
            'background-repeat': 'no-repeat',
            'background-position':'center',
        });
        colDiv.append(thumbDiv);

        var captionDiv = $(document.createElement('div'));
        captionDiv.addClass("caption");
        captionDiv.css({
            'top':'64%',
            'position':'absolute',
            'width':'90%',
        });
        colDiv.append(captionDiv);
        var caption = $(document.createElement('label'));
        captionDiv.append(caption);
        caption.css({
            'width':'100%',
            'overflow':'hidden',
            'white-space': 'nowrap',
            'text-overflow': 'ellipsis',
        });
        if(spec.title===""){
            caption.text("Untitled");
        }else{
            caption.text(spec.title);
        }
        var descDiv = $(document.createElement('div'));
        descDiv.css({
            'height':'45px',
            'overflow':'hidden',
            'word-wrap': 'break-word',
            'text-overflow': 'ellipsis',
        });

        if(spec.description===""){
            descDiv.text("Click Edit to add description");
        }else{
            descDiv.text(spec.description);
        }
        captionDiv.append(descDiv);
        var btngroup = $(document.createElement('div'));
        btngroup.addClass('row');
        captionDiv.append(btngroup);
        
        // var modal = Util.editBtn("Trip",spec);
        // var editbtn = $(document.createElement('button'));
        // editbtn.text("Edit");
        // editbtn.addClass("btn btn-default col-sm-offset-2");
        // editbtn.click(function(){
        //     //open a modal to edit info about the photo
        //     modal.modal({show:true});
        // });
        // btngroup.append(editbtn);

        var viewbtna = $(document.createElement('a'));
        var viewbtn = $(document.createElement('button'));
        viewbtn.text("View");
        viewbtn.addClass('btn btn-default col-sm-offset-5');
        //lead to the trip's page when clicked
        viewbtna.attr("href",spec.link);
        viewbtn.click(function(){});
        viewbtna.append(viewbtn);
        var deleteDiv=$(document.createElement("div"));
        deleteDiv.addClass("col-sm-2 col-sm-offset-1");
        var deleteForm = $(document.createElement('form'));
        deleteDiv.append(deleteForm);
        deleteForm.attr('method', 'post');
        deleteForm.attr('action', '/deleteTrip?tripKey=' + spec.tripkey + "&userKey=" + spec.userkey);
        var deletebtn = $(document.createElement('button'));
        deletebtn.text("Delete");
        deletebtn.addClass("btn btn-default delete");
        deletebtn.click(function(){
            colDiv.remove(); 
            modal.remove();

        });
        btngroup.append(viewbtna);
        deleteForm.append(deletebtn);
        btngroup.append(deleteDiv);

        return colDiv;
    }
    /***
    add a trip modal. 
    **/
    function addNewTrip(){
        var modal = Util.makeModal('addTrip', "Add Trip",false);
        body.append(modal);
        var rowwrapper = $(document.createElement('div'));
        //for submit purpose
        addTripform = $(document.createElement('form'));
        addTripform.attr('method','post');
        addTripform.attr('action', '/insertTrip');
        addTripform.attr('id','addTripform');
        var submit_input = $(document.createElement('input')); //actually calls servlet, but invisible
        submit_input.attr('type', 'submit');
        submit_input.attr('name', 'submitbtn');
        submit_input.attr('id', 'addTripInput');
        submit_input.css({
            'display' : 'none'
        });
        addTripform.append(submit_input);
        rowwrapper.append(addTripform);
        $("#addTripmodalBody").append(rowwrapper);
        rowwrapper.addClass('row');
        rowwrapper.css({
            'padding-top':'9px',
            'top':'5%',
            'left':'10%',
            'position':'relative',
            'width':'80%'
        });
        var titleWrapper = Util.inputGroup('Title: ', 'title', 'Untitled',null,null,true);
        addTripform.append(titleWrapper);

        var start = Util.inputGroup('Start: ', 'departDate', "Choose a start date",null,1,false);
        start.addClass('col-md-12');
        var end = Util.inputGroup('End: ', 'retDate', "Choose an end date",null,1,false);
        end.addClass('col-md-12');
        addTripform.append(start); 
        addTripform.append(end);
        //make sure the start date is always in front of the end date

        start.on("dp.change",function (e) {
            end.data("DateTimePicker").setMinDate(e.date);
        });
        end.on("dp.change",function (e) {
            start.data("DateTimePicker").setMaxDate(e.date);
        });
        var locationWrapper = Util.inputGroup('Where: ', 'location', 'Enter a location',null,4,false);
        addTripform.append(locationWrapper);
        locationWrapper.addClass('col-md-12');
        
        var description = $(document.createElement('textarea'));
        description.attr('placeholder','Description');
        description.css({
            'height':'200px',
            'width':'100%',
            'resize':'none',
            'padding-bottom':'5px',

        });
        description.attr('name', 'description');
        addTripform.append(description);

        //invisible input that gives the userKey as a parameter:
        //(kind of hacky, but we'll see if it works... and it does! yay)
        var keyUser = $(document.createElement('input'));
        keyUser.attr('type, text');
        keyUser.attr('name', 'userKey');
        keyUser.css("display", "none");
        setKey();
        addTripform.append(keyUser);

        var addTags = $(document.createElement('input'));
        addTags.attr('type','text');
        addTags.addClass('tags');
        addTags.css({
            'padding-bottom':'5px',

        });

        addTripform.append(addTags);
        addTags.tagsInput({
            'width': 'auto',
            'height':'5px',
            'padding-bottom':'5px',
            //autocomplete_url:'test/fake_plaintext_endpoint.html' //jquery.autocomplete (not jquery ui)
            // autocomplete_url:'test/fake_json_endpoint.html' // jquery ui autocomplete requires a json endpoint
        });



        function setKey() {
        //store user key:
            if(typeof(Storage) !== "undefined") {
                console.log("there is session storage");
            } else {
                console.log("browser does not support storage");
            }

            var key = getQueryVariable("userKey");
            if(key != null) {
                keyUser.attr('value', key);
                putToLocalStorage("userKey", key);
                console.log("set key user attribute as:" + key);
            }
        }


        //insert url parameter
        function insertParam(key, value) {
            key = escape(key); value = escape(value);

            var kvp = document.location.search.substr(1).split('&');
            if (kvp == '') {
                document.location.search = '?' + key + '=' + value;
            }
            else {

                var i = kvp.length; var x; while (i--) {
                    x = kvp[i].split('=');

                    if (x[0] == key) {
                        x[1] = value;
                        kvp[i] = x.join('=');
                        break;
                    }
                }

                if (i < 0) { kvp[kvp.length] = [key, value].join('='); }

                //this will reload the page, it's likely better to store this until finished
                document.location.search = kvp.join('&');
            }
        }

        //get value from local storage
        function getFromLocalStorage(key) {
            var item = localStorage.getItem(key);
            if(item) { 
                return item;
            }
            else {
                // console.log("could not store " + key);
                return null;
            }
        }

        //put key value pair into local storage
        function putToLocalStorage(key, value) {
            localStorage.setItem(key, value);
        }

        //gets parameter either from url or local storage
        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            } 
            //if it's not a parameter, check if it's in local storage
            return getFromLocalStorage(variable);
            //alert('Query Variable ' + variable + ' not found');
        }

        var submitbtn = $(document.getElementById('addTripsavebtn'));
        body.on('click', '#addTripsavebtn', function () {

            submit_input.submit();
            submit_input.click();
        });
    }
})();