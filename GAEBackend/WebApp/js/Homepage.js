body = $(document.getElementById("body"));//root.find("div");

main = $(document.getElementById("main"));
contentDiv = $(document.createElement('div'));
main.append(contentDiv);
contentDiv.addClass("row");
contentDiv.css('padding-top','50px');
addbtnDiv = $(document.createElement('div'));
// addbtnDiv.addClass("col-md-offset5");
addbtnDiv.css({
    'padding-left':'15px',
    'padding-bottom':'8px'
});
contentDiv.append(addbtnDiv);
addbtn = $(document.createElement('button'));
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

tripGrid = $(document.createElement('div'));
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

trip1 = Util.photoPreview(true);//createThumbnail("../images/1.JPG");
tripGrid.append(trip1);
trip2 = Util.photoPreview(true);//createThumbnail("../images/2.jpg");
tripGrid.append(trip2);
trip3 = Util.photoPreview(true);//createThumbnail("../images/3.jpg");
tripGrid.append(trip3);
trip4 = Util.photoPreview(true);//createThumbnail("../images/4.jpg");
tripGrid.append(trip4);
trip5 = Util.photoPreview(true);//createThumbnail("../images/5.JPG");
tripGrid.append(trip5);
trip6 = Util.photoPreview(true);//createThumbnail("../images/6.JPG");
tripGrid.append(trip6);


/**
Function for create a div for a trip, 
TODO: later to consider if there is no thumbnail image, add a picture stands for text. 
Since bootstraps cannot have a fixed height for all thumbnails, we probably want to crop
the thumbnails and the picture stands for text should be the same ratio.

@param: path: the path to the thumbnail image.
**/
function createThumbnail(path){
//     <div class="row">
//   <div class="col-sm-6 col-md-4">
//     <div class="thumbnail">
//       <img data-src="holder.js/300x200" alt="...">
//       <div class="caption">
//         <h3>Thumbnail label</h3>
//         <p>...</p>
//         <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
//       </div>
//     </div>
//   </div>
// </div>
	colDiv = $(document.createElement('div'));
	
	colDiv.addClass('col-sm-6 col-md-4');
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
