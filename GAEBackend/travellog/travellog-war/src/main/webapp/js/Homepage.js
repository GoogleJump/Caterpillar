Homepage = (function(){

    

    //if there is a user key stored, get it and set as parameter for link
    console.log("about to get user key");
  //  var userKey = Util.getQueryVariable("userKey");
    var userKey = Util.getFromLocalStorage("userKey");
    //if(userKey == null) userKey = Util.getQueryVariable("userKey");
    if(userKey != null) {
        console.log("user key was not null, setting param")
        var tripbutton = $(document.getElementById("trips_button"));
        tripbutton.attr("href", "/homepage.jsp?userKey=" + userKey);
    }

    var body = $(document.getElementById("body"));//root.find("div");

    var main = $(document.getElementById("main"));
    var contentDiv = $(document.createElement('div'));
    main.append(contentDiv);
    contentDiv.addClass("row");
    contentDiv.css('padding-top','50px');
    var addbtnDiv = $(document.createElement('div'));
    addbtnDiv.addClass("col-md-12");
    addbtnDiv.css({
        //'padding-left':'15px',
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
        // 'left':'10px',
        // 'position':'absolute'
    });
    addbtn.css({
        'background-color':'#00868B',
    });

    var tripGrid = $(document.createElement('div'));
    contentDiv.append(tripGrid);
    tripGrid.addClass("row col-md-offset-1");
    // tripGrid.css({
    // 	'top':'100px',
    // 	'left':'10%',
    // 	'position':'absolute',
    // 	'height':'auto',
    // 	'width':'80%'
    // });
    // addTrip=createThumbnail("../images/plus.jpeg");
    // tripGrid.append(addTrip);
    // addTrip.attr('data-toggle','modal');
    // addTrip.attr('data-target','#addTrip');
    addNewTrip();

   /* var trips = $(".trip");
    for(var i = 0; i < trips.length; ++i) {
        var tripinfo = trips.eq(i);
        var src = tripinfo.attr("id");
        var link = "/tripview.jsp?tripKey=" + tripinfo.attr("name");
        // var trip = createThumbnail(src, link);
        // var triptitle;
        var spec ={
            title: tripinfo.children(".title").val(),
            description:tripinfo.children(".description").val(),
            depDate: tripinfo.children(".depdate").val(),
            retDate: tripinfo.children(".retdate").val(),
            location: tripinfo.children(".location").val(),
            link: link,
            img:src,
            tripkey: tripinfo.attr("name"),
            userkey: userKey,
            index: i,
        };
        var trip = tripPreview(spec);
        //TODO: onclick sets parameter as this trip and leads to tripview **Test
        // tripGrid.append(oneTrip);
        tripGrid.append(trip);
    }*/

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
    /*  for(var i = 0; i < trips.length; ++i) {
        var trip = tripPreview(trips[i]);
        tripGrid.append(trip);
    }*/
});



    /**
    Function for create a div for a trip, 
    TODO: later to consider if there is no thumbnail image, add a picture stands for text. 
    Since bootstraps cannot have a fixed height for all thumbnails, we probably want to crop
    the thumbnails and the picture stands for text should be the same ratio.

    @param: path: the path to the thumbnail image.
    **/
    function createThumbnail(path, link){
    	var colDiv = $(document.createElement('div'));
    	
    	colDiv.addClass('col-sm-6 col-md-4');
    	var thumba=$(document.createElement('a'));
    	thumba.attr("href",link);
    	thumba.addClass("thumbnail");
    	colDiv.append(thumba);
    	var thumbnail=$(document.createElement('img'));
    	thumbnail.attr("src",path);
    	thumbnail.attr("alt","No image for the trip available")
    	thumba.append(thumbnail);
    	return colDiv
    }
    this.createThumbnail=createThumbnail;

    function tripPreview(spec){
        var colDiv = $(document.createElement('div'));
        colDiv.addClass("col-md-5");
        colDiv.css({
            'background-color':Util.yellow,
            'height':'400px',
            // 'padding-left':'20px',
            'margin-right':'40px',
            'margin-bottom':'40px',

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
        // captionDiv.css('width','100%');
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
        

        var modal = Util.editBtn("Trip",spec);
        var editbtn = $(document.createElement('button'));
        // descDiv.text(desc);
        editbtn.text("Edit");
        editbtn.addClass("btn btn-default col-sm-offset-2");
        editbtn.click(function(){
            modal.modal({show:true});
            //open a modal to edit info about the photo
        });

        var viewbtna = $(document.createElement('a'));
        var viewbtn = $(document.createElement('button'));
        viewbtn.text("View");
        viewbtn.addClass('btn btn-default col-sm-offset-2');
        //TODO: lead to the trip's page when clicked
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
        deleteForm.append(deletebtn);
        btngroup.append(editbtn);
        btngroup.append(deleteDiv);

        btngroup.append(viewbtna);
        return colDiv;
    }
    this.tripPreview=tripPreview;
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
        // var bodyid = 
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

        // var timeWrapper = $(document.createElement('div'));
        // timeWrapper.addClass()
        // rowwrapper.append(timeWrapper);
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
        console.log("could not store " + key);
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

$("#addTripform").validate({
    rules:{
        titleWrapper: {required:true},
        locationWrapper:{required:true},
        start:{required:true},
        end:{required:true},
                    // description:{required:true},
                }
            });
var submitbtn = $(document.getElementById('addTripsavebtn'));
$('body').on('click', '#addTripsavebtn', function () {

    submit_input.submit();
    submit_input.click();
    console.log("clicked");

        //insert key
      //  insertParam("userKey", key);
       // console.log("set param");




                //send request from local storage
            //var key = localStorage.getItem("userKey");
            // insertParam("userKey", key);
           // console.log("inserted param");

                //For the format of date, check http://momentjs.com/
                console.log(start.data("DateTimePicker").getDate().format());        
            });
        // submitbtn.click(function(){
        //         //which one of these will work??
        //     submit_input.submit();
        //     console.log("clicked");

        //         //send request from local storage
        //     var key = localStorage.getItem("userKey");
        //     // insertParam("userKey", key);
        //     console.log("inserted param");

        //         //For the format of date, check http://momentjs.com/
        //     console.log(start.data("DateTimePicker").getDate().format());
        // });
}
this.addNewTrip= addNewTrip;
})();