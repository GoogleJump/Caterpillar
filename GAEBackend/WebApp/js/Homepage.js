var root;

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
// addTrip.attr('href','#myModal');
addTrip.click(function(evt){
	addNewTrip();

});


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

**/
function addNewTrip(){
    console.log("fsadfsadfsag");
    modal = $(document.createElement('div'));
    modal.addClass('modal fade');
    modal.attr('id','myModal');
    modal.attr('tabindex','-1');
    modal.attr('role','dialog');
    modal.attr('aria-labelledby',"myModalLabel");
    modal.attr('aria-hidden','true');
    modal.css({
        'height':'100%',
        'width':'100%'
    });

    body.append(modal);
    dialogmodal = $(document.createElement('div'));
    dialogmodal.addClass('modal-dialog');
    modal.append(dialogmodal);
    tripContainer=$(document.createElement('div'));
    tripContainer.addClass('modal-content');
    // tripContainer.addClass("panel panel-default");
    dialogmodal.append(tripContainer);
    tripContainer.css({
		'border':'10px black solid',
		// 'height':'auto',
		// 'width':'80%',
		'top':'80px',
		// 'left':'25%',
		// 'z-index':'100000001',
		// 'position':'absolute',
		'min-height':'300px',
		'min-width':'250px',
		'border-radius':'15px',
		'background-color':'#B1F2EF'
    });
    headingDiv=$(document.createElement('div'));
    // headingDiv.addClass('panel-heading text-center');
    headingDiv.addClass('modal-header');
    headingDiv.css({
    	'background-color':'#2F4F4F',
    	'font-family':'Serif',
		'color':'#FFFCD4',
		'height':'60px',
    });
    tripContainer.append(headingDiv);
    heading = $(document.createElement('h3'));
    // heading.addClass('panel-title');
    heading.addClass('modal-title');
    heading.text("Add Trip");
    headingDiv.append(heading);
    heading.css({
    	// 'font-size':'40px'
    });
    panelBody= $(document.createElement('div'));
    tripContainer.append(panelBody);
    // panelBody.addClass("panel-body");
    panelBody.addClass("modal-body");
    // panelBody.css({
    //     // 'padding-top':'9px',
    //     // 'top':'5%',
    //     // 'left':'0%',
    //     // 'position':'absolute',
    //     // 'width':'100%'
    // });

	rowwrapper = $(document.createElement('div'));
	rowwrapper.addClass('row');
	rowwrapper.css({
		'padding-top':'9px',
		'top':'5%',
    	'left':'10%',
    	'position':'relative',
    	'width':'80%'
    });
    panelBody.append(rowwrapper);
    titleWrapper = inputGroup('Title: ', 'Untitled');
    rowwrapper.append(titleWrapper);
    
    locationWrapper = inputGroup('Where: ','Location');
    rowwrapper.append(locationWrapper);

    timeWrapper = inputGroup('When: ','Today');
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
    //TODO: tags and submit and cancel button
    addTags = $(document.createElement('input'));
    addTags.attr('type','text');
    addTags.addClass('tags');
    // addTags.attr('placeholder','Add Tags');
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

    footer=$(document.createElement('div'));
    tripContainer.append(footer);
    footer.addClass('modal-footer');
    closebtn = $(document.createElement('btn'));
    closebtn.attr('type','button');
    closebtn.addClass('btn btn-default');
    closebtn.attr('data-dismiss','modal');
    closebtn.text("Close");
    savebtn = $(document.createElement('btn'));
    savebtn.attr('type','button');
    savebtn.addClass('btn btn-primary');
    // savebtn.attr('data-dismiss','modal');
    savebtn.text("Add");
    savebtn.css({
        'background-color':'#504552'
    });
    footer.append(closebtn);
    footer.append(savebtn);
    modal.modal({show:true});
}
this.addNewTrip= addNewTrip;

/**
Customized function for input group
including a divwrapper, title and input box..
@param: the tilte
**/
function inputGroup(name, placeholder){
	wrapper = $(document.createElement('div'));
    wrapper.addClass('input-group');
    wrapper.css({
    	// 'top':'5%',
    	// 'left':'10%',
    	// 'position':'relative',
    	// 'width':'80%'
    	'padding-bottom':'5px'
    })
    title = $(document.createElement('span'));
    title.addClass('input-group-addon');
    title.text(name);
    title.css({
    	'height':'15px',
    	'background-color':'#504552',
    	'color':'white',
    	'font-weight':'bold'
    });
    wrapper.append(title);
    titleInput = $(document.createElement('input'));
    titleInput.attr('type','text');
    titleInput.addClass('form-control');
    titleInput.attr('placeholder',placeholder);
    wrapper.append(titleInput);

    return wrapper;
}
this.inputGroup=inputGroup;

