body = $(document.getElementById("body"));//root.find("div");

tripGrid = $(document.createElement('div'));
body.append(tripGrid);
tripGrid.addClass("row");
tripGrid.css({
	'top':'200px',
	'left':'10%',
	'position':'absolute',
	'height':'auto',
	'width':'80%'
});
addTrip=createThumbnail("../images/plus.jpeg");
tripGrid.append(addTrip);
addTrip.attr('data-toggle','modal');
addTrip.attr('data-target','#addTrip');
addNewTrip();

//TODO: Should get thumbnails of each trip later from backend

trip1 = createThumbnail("../images/1.JPG");
tripGrid.append(trip1);
trip2 = createThumbnail("../images/2.jpg");
tripGrid.append(trip2);
trip3 = createThumbnail("../images/3.jpg");
tripGrid.append(trip3);
trip4 = createThumbnail("../images/4.jpg");
tripGrid.append(trip4);
trip5 = createThumbnail("../images/5.JPG");
tripGrid.append(trip5);
trip6 = createThumbnail("../images/6.JPG");
tripGrid.append(trip6);


/**
Function for create a div for a trip, 
TODO: later to consider if there is no thumbnail image, add a picture stands for text. 
Since bootstraps cannot have a fixed height for all thumbnails, we probably want to crop
the thumbnails and the picture stands for text should be the same ratio.

@param: path: the path to the thumbnail image.
**/
function createThumbnail(path){
	colDiv = $(document.createElement('div'));
	
	colDiv.addClass('col-xs-6 col-md-3');
	// grid.append(colDiv);
	thumba=$(document.createElement('a'));
	thumba.attr("href","#");//TODO: href would be the trip's url later
	thumba.addClass("thumbnail");
	colDiv.append(thumba);
	thumbnail=$(document.createElement('img'));
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
    modal = Util.makeModal('addTrip', "Add Trip");
    body.append(modal);
	rowwrapper = $(document.createElement('div'));
    $("#modalBody").append(rowwrapper);
	rowwrapper.addClass('row');
	rowwrapper.css({
		'padding-top':'9px',
		'top':'5%',
    	'left':'10%',
    	'position':'relative',
    	'width':'80%'
    });
    titleWrapper = Util.inputGroup('Title: ', 'Untitled');
    rowwrapper.append(titleWrapper);
    
    locationWrapper = Util.inputGroup('Where: ','Location');
    rowwrapper.append(locationWrapper);

    timeWrapper = Util.inputGroup('When: ','Today');
    rowwrapper.append(timeWrapper);
    description = $(document.createElement('textarea'));
    description.attr('placeholder','Description');
    description.css({
    	'height':'200px',
    	'width':'100%',
        'resize':'none',
        'padding-bottom':'5px',

    })
    rowwrapper.append(description);
    addTags = $(document.createElement('input'));
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
