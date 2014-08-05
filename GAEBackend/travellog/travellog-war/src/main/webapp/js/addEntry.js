addEntry = (function(){

  //don't submit on enter:
     $(window).keydown(function(event){
    if( (event.keyCode == 13) ) {
      event.preventDefault();
      return false;
    }
  });

	"use strict";
	var body = $(document.getElementById("body"));
	var main = $(document.getElementById("main"));

	var contentForm = $(document.getElementById('addEntry')); //changed from making new one because needed to include some java stuff for blobs in .jsp
	var contentDiv = $(document.createElement('div'));
	contentForm.append(contentDiv);
	contentDiv.addClass("row");
	var pageheader = $(document.createElement('div'));
	pageheader.addClass('page-header col-md-12');
	contentDiv.append(pageheader); 
	var header = $(document.createElement('h1'));
	header.text("Add a Post");
	pageheader.append(header);
	var title = Util.inputGroup('Title: ', "title","Untitled",null,null,true);
	title.addClass('col-md-6 col-sm-offset-1');
	title.children('input').eq(0).attr({
		"id":"entryTitle",
	});
	contentDiv.append(title);

	//if there is a user key stored, get it and set as parameter for link
    var userKey = Util.getQueryVariable("userKey");
    if(userKey != null) {
        console.log("user key was not null, setting param")
    }


	//invisible user key input - get from local storage and set
	var userKeyInput = $(document.createElement('input'));
	userKeyInput.css("display", "none");
	var userKey = getFromLocalStorage("userKey");
	console.log("user key is:" + userKey);
	userKeyInput.attr("value", userKey);
	userKeyInput.attr("name","userKey")
	contentDiv.append(userKeyInput);

	//invisible trip key input - get from url param and set
	var tripKeyInput = $(document.createElement('input'));
	tripKeyInput.css("display", "none");
	var tripKey = getQueryVariable("tripKey");
	console.log("trip key is:" + tripKey);
	tripKeyInput.attr("value", tripKey);
	tripKeyInput.attr("name","tripKey")
	contentDiv.append(tripKeyInput);

	//link buttons
	var tripbutton = $(document.getElementById("trips_button"));
    tripbutton.attr("href", "/homepage.jsp?userKey=" + userKey);
    var entriesbutton = $(document.getElementById("entriesmap_button"));
    entriesbutton.attr("href", "/MapHome.html?tripKey=" + tripKey);

	//location input
    var where = Util.inputGroup('Where: ',"location","Enter a location",null,4,false);
	contentDiv.append(where);
	where.addClass('col-md-6 col-sm-offset-1');

	var btnDiv = $(document.createElement('div'));
	contentDiv.append(btnDiv);
	btnDiv.addClass('col-sm-offset-1');
	var uploadbtn = $(document.createElement('div'));
	//add this b/c idk if there is way to uplaod files without it coming straight from file form
	uploadbtn.addClass("btn btn-primary fileUpload");
	var upload = $(document.createElement('span'));
	upload.text('Upload Photos');
	uploadbtn.append(upload);
	btnDiv.append(uploadbtn);

	var fileElem = (document.getElementsByClassName('multi'));

	for(var i = 0; i < fileElem.length; i++) {
		uploadbtn.append(fileElem[i]);
		uploadbtn.css({
			'position': 'relative',
			'overflow': 'hidden',
			// 'margin': '10px',
			'background-color':Util.dark_purple,

		});
		$(fileElem[i]).css({
			'position': 'absolute',
			'top': '0',
			'right': '0',
			'margin': '0',
			'padding': '0',
			'font-size': '20px',
			'cursor': 'pointer',
			'opacity': '0',
			'filter': 'alpha(opacity=0)',
		});
		
	} //end of fileElem loop
	var filenames;

	/*removes all photos and adds the updated ones back on the front end*/
	function addPhotoPreview() {
		console.log("update");
		$(".MultiFile-list").hide(); //hide the list that shows the files
		var titles = new Array(fileElem.length);
		var descriptions = new Array(fileElem.length);
		//save titles and descriptions before removing from photoDiv
		for(var i = 0; i < fileElem.length; i++) {
			titles[i]=[];
			descriptions[i]=[];
			for(var k=0;k<fileElem[i].files.length;k++){
				var curfilename = fileElem[i].files[k].name;
				titles[i].push($(document.getElementById("photoTitle"+curfilename)).text());
	        	descriptions[i].push($(document.getElementById("photoDescription"+curfilename)).text());
			}
			
		}
		
			$(photoDiv).empty(); //empty all photo thumbnails
			$(".modal").remove(); //empty all modals
			var num_photos = photoDiv.children().length; //current amount - index of new one will be this length
			fileElem = (document.getElementsByClassName('multi')); //redefine fileElem (more added)
			console.log("fileElem length multifile is:" +fileElem.length);
			console.log("file Elem is:"+ fileElem);
			console.log("fileElem[0] is"+ fileElem);
			for(var j = 0; j < fileElem.length - 1; j++ ) {
					filenames = Util.uploadPhotos(fileElem[j], photoDiv, j, titles[j], descriptions[j]);
			}
		}

		var first = 0;
		$(uploadbtn).bind('DOMNodeInserted DOMNodeRemoved', function(event) {
			console.log("upload bind insert/remove");

		
	    if (event.type == 'DOMNodeInserted') {
	        //alert('Content added! Current content:' + '\n\n' + this.innerHTML);
	        if(first != 0) addPhotoPreview(); //don't do this the first time
			first = 1;
	    } else {
	       // alert('Content removed! Current content:' + '\n\n' + this.innerHTML);
	       console.log("content removed");
	    }
	});





	var photoRow = $(document.createElement('div'));
	contentDiv.append(photoRow);
	photoRow.addClass('row');
	photoRow.css({
		'margin-left':'0px',
		'margin-right':'0px',
	});
	var photoDiv = $(document.createElement('div'));
	photoDiv.addClass("col-sm-offset-1 col-md-10");
	photoRow.append(photoDiv);
	photoDiv.css({
		'margin-top':'10px',
		'background-color':'white',//Util.yellow,
		'border':'gray 3px solid',
		'border-radius':'3px',
		'overflow-y':'auto',
		'height':'450px',
	});

	

	var labelDiv = $(document.createElement('div'));
	labelDiv.addClass('row col-sm-offset-1');
	labelDiv.css('padding-bottom','10px');
	contentDiv.append(labelDiv);
	var notebook = $(document.createElement('h3'));
	var desLabel = $(document.createElement('span'));
	desLabel.addClass('label label-default');
	desLabel.text("Blog");
	desLabel.css({
		"background-color":Util.dark_purple,
	});
	notebook.append(desLabel);
	labelDiv.append(notebook);

	var textWrapper= $(document.createElement('div'));
	textWrapper.addClass('row');
	textWrapper.css('padding-bottom','15px');
	contentDiv.append(textWrapper);
	var textDiv = $(document.createElement('div'));
	textDiv.addClass('col-md-10 col-sm-offset-1');
	textWrapper.append(textDiv);

	var text = $(document.createElement('textarea'));
	text.attr("name", "description");
	text.css({
		'height':'300px',
		'overflow-y':'auto',
		'width':'100%',
        'resize':'none',

	});
	textDiv.append(text);

	var tagsWrapper = $(document.createElement('div'));
	tagsWrapper.addClass('row');
	// tagsWrapper.css('margin-top','15px');
	contentDiv.append(tagsWrapper);
	var tagsDiv= $(document.createElement('div'));
	tagsDiv.addClass('col-md-10 col-sm-offset-1');
	tagsWrapper.append(tagsDiv);
	//not working, commented out for now:
	/*var addTags = $(document.createElement('input'));
	addTags.attr("name", "tags");
	addTags.attr('type','text');
	addTags.addClass('tags');
	addTags.css({
	    'padding-bottom':'10px',
	});
	tagsDiv.append(addTags); 
	addTags.tagsInput({
	    'width': 'auto',
	    'height':'5px',
	        // 'padding-bottom':'5px',
	        //autocomplete_url:'test/fake_plaintext_endpoint.html' //jquery.autocomplete (not jquery ui)
	        // autocomplete_url:'test/fake_json_endpoint.html' // jquery ui autocomplete requires a json endpoint
	});*/

	function getQueryVariable(variable) {
	  var query = window.location.search.substring(1);
	  var vars = query.split("&");
	  for (var i=0;i<vars.length;i++) {
	    var pair = vars[i].split("=");
	    if (pair[0] == variable) {
	      return pair[1];
	  }
	} 
	//alert('Query Variable ' + variable + ' not found');
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



	var submit_input = $(document.createElement('input')); //actually calls servlet, but invisible
    submit_input.attr('type', 'submit');
    submit_input.css({
            'display' : 'none'
    });

    contentForm.append(submit_input);

	var btnsDiv = $(document.createElement('div'));
	btnsDiv.addClass('row col-sm-offset-1');
	btnsDiv.css('margin-top','10px');
	// btnsDiv.css('margin-bottom','10px');
	contentDiv.append(btnsDiv);
	var btns = $(document.createElement('div'));
	btns.addClass('col-md-12 col-sm-offset-1');
	btnsDiv.append(btns);

	var cancelbtn = $(document.createElement('btn'));
	cancelbtn.attr('type','button');
	cancelbtn.addClass('btn btn-default pull-left');
	// cancelbtn.attr('data-dismiss','modal');
	cancelbtn.text("Cancel");
	cancelbtn.css({
	    'background-color':Util.blue,
	    'right':'16%',
	    'color':"white",
	    'position':'absolute',
	});
	var savebtn = $(document.createElement('btn'));
	savebtn.attr('type','button');
	savebtn.addClass('btn btn-default');
	// savebtn.attr('data-dismiss','modal');
	savebtn.text("Submit");
	savebtn.css({
	    'background-color':Util.blue,
	    'color':"white",
	    'right':'9%',
	    'position':'absolute',
	});

	//make sure this button submits the form (because there are two buttons it might not work):
	savebtn.click(function(){

		//create inputs for each of the photo previews
		var thumbs = $(document.getElementsByClassName("photoThumbnails"));
		console.log("thumbnail amount is:" + thumbs.length);
		for(var i = 0; i < thumbs.length; ++i) {
			var curid = $(thumbs[i]).attr("id");
			var curtitle= curid.slice(0,curid.length-10);//the id is filedefaultname+modalthumb. we only need filedefaultname
			var titleInput = $(document.createElement('input'));
			titleInput.attr('name', 'photoTitle');
			titleInput.attr('value', $(document.getElementById("photoTitle"+curtitle)).text());
			titleInput.css("display", "none");
			contentForm.append(titleInput);

			var descInput = $(document.createElement('input'));
			descInput.attr('name', 'photoDescription');
			descInput.attr('value', $(document.getElementById("photoDescription"+curtitle)).text());
			descInput.css("display", "none");
			contentForm.append(descInput);

			var fileInput = $(document.createElement('input'));
			fileInput.attr('name', 'photoFile');
			fileInput.attr('value', filenames[i]);
			fileInput.css('display', 'none');
			contentForm.append(fileInput);

		}

		savebtn.submit();
		submit_input.click(); //why should we even need this?
	});
	btnsDiv.append(cancelbtn);
	btnsDiv.append(savebtn);

})();