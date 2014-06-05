Homepage = (function(){
    var body = $(document.getElementById("body"));//root.find("div");

    var main = $(document.getElementById("main"));
    var contentDiv = $(document.createElement('div'));
    main.append(contentDiv);
    contentDiv.addClass("row");
    contentDiv.css('padding-top','50px');
    var addbtnDiv = $(document.createElement('div'));
    // addbtnDiv.addClass("col-md-offset5");
    addbtnDiv.css({
        'padding-left':'15px',
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
    tripGrid.addClass("row col-md-offset3 col-md-10");
    tripGrid.css({
    	// 'top':'100px',
    	// 'left':'10%',
    	// 'posit ion':'absolute',
    	// 'height':'auto',
    	// 'width':'80%'
    });
    // addTrip=createThumbnail("../images/plus.jpeg");
    // tripGrid.append(addTrip);
    // addTrip.attr('data-toggle','modal');
    // addTrip.attr('data-target','#addTrip');
    addNewTrip();

    //TODO: Should get thumbnails of each trip later from backend

    var trip1 = Util.photoPreview(true);//createThumbnail("../images/1.JPG");
    tripGrid.append(trip1);
    var trip2 = Util.photoPreview(true);//createThumbnail("../images/2.jpg");
    tripGrid.append(trip2);
    var trip3 = Util.photoPreview(true);//createThumbnail("../images/3.jpg");
    tripGrid.append(trip3);
    var trip4 = Util.photoPreview(true);//createThumbnail("../images/4.jpg");
    tripGrid.append(trip4);
    var trip5 = Util.photoPreview(true);//createThumbnail("../images/5.JPG");
    tripGrid.append(trip5);
    var trip6 = Util.photoPreview(true);//createThumbnail("../images/6.JPG");
    tripGrid.append(trip6);


    /**
    Function for create a div for a trip, 
    TODO: later to consider if there is no thumbnail image, add a picture stands for text. 
    Since bootstraps cannot have a fixed height for all thumbnails, we probably want to crop
    the thumbnails and the picture stands for text should be the same ratio.

    @param: path: the path to the thumbnail image.
    **/
    function createThumbnail(path){
    	var colDiv = $(document.createElement('div'));
    	
    	colDiv.addClass('col-sm-6 col-md-4');
    	var thumba=$(document.createElement('a'));
    	thumba.attr("href","#");//TODO: href would be the trip's url later
    	thumba.addClass("thumbnail");
    	colDiv.append(thumba);
    	var thumbnail=$(document.createElement('img'));
    	thumbnail.attr("src",path);
    	thumbnail.attr("alt","No image for the trip available")
    	thumba.append(thumbnail);
    	return colDiv
    }
    this.createThumbnail=createThumbnail;


    /***
    add a trip modal. 
    **/
    function addNewTrip(){
        var modal = Util.makeModal('addTrip', "Add Trip");
        body.append(modal);
    	var rowwrapper = $(document.createElement('div'));
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
        var titleWrapper = Util.inputGroup('Title: ', 'Untitled',null);
        rowwrapper.append(titleWrapper);
        
        var locationWrapper = Util.inputGroup('Where: ','Location',null,4);
        rowwrapper.append(locationWrapper);

        // var timeWrapper = $(document.createElement('div'));
        // timeWrapper.addClass()
        // rowwrapper.append(timeWrapper);
        var start = Util.inputGroup('Start: ',"Choose a start date",null,1);
        start.addClass('col-md-12');
        rowwrapper.append(start);
        var end = Util.inputGroup('End: ',"Choose an end date",null,1);
        end.addClass('col-md-12');
        rowwrapper.append(end); 
        //make sure the start date is always in front of the end date
        start.on("dp.change",function (e) {
            end.data("DateTimePicker").setMinDate(e.date);
        });
        end.on("dp.change",function (e) {
            start.data("DateTimePicker").setMaxDate(e.date);
        });
        var description = $(document.createElement('textarea'));
        description.attr('placeholder','Description');
        description.css({
        	'height':'200px',
        	'width':'100%',
            'resize':'none',
            'padding-bottom':'5px',

        })
        rowwrapper.append(description);
        var addTags = $(document.createElement('input'));
        addTags.attr('type','text');
        addTags.addClass('tags');
        addTags.css({
            'padding-bottom':'5px',

        });
        rowwrapper.append(addTags);
        addTags.tagsInput({
            'width': 'auto',
            'height':'5px',
            'padding-bottom':'5px',
            //autocomplete_url:'test/fake_plaintext_endpoint.html' //jquery.autocomplete (not jquery ui)
            // autocomplete_url:'test/fake_json_endpoint.html' // jquery ui autocomplete requires a json endpoint
        });
    }
    this.addNewTrip= addNewTrip;
})();