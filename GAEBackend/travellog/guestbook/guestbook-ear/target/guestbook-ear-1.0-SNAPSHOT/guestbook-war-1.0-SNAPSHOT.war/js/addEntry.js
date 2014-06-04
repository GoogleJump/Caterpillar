addEntry = (function(){
	"use strict";
	var body = $(document.getElementById("body"));
	var main = $(document.getElementById("main"));

	var contentDiv = $(document.createElement('div'));
	main.append(contentDiv);
	contentDiv.addClass("row");
	main.css('padding-bottom','60px');
	var pageheader = $(document.createElement('div'));
	pageheader.addClass('page-header col-md-12');
	contentDiv.append(pageheader);
	var header = $(document.createElement('h1'));
	header.text("Add a Post");
	pageheader.append(header);

	var title = Util.inputGroup('Title: ', "Untitled");
	title.addClass('col-md-6 col-sm-offset-1');
	contentDiv.append(title);

	//TODO: get the current date and set datepicker
	var when = Util.inputGroup('When: ',"Today",null,1);
	when.addClass('col-md-6 col-sm-offset-1');
	contentDiv.append(when);
	var where = Util.inputGroup('Where: ',"Current Location");
	contentDiv.append(where);
	where.addClass('col-md-6 col-sm-offset-1');

	var btnDiv = $(document.createElement('div'));
	contentDiv.append(btnDiv);
	btnDiv.addClass('col-sm-offset-1');
	var uploadbtn = $(document.createElement('button'));
	uploadbtn.addClass("btn btn-primary");
	btnDiv.append(uploadbtn);
	var fileElem=(document.getElementById("fileElem"));
	uploadbtn.click(function(){//"click", function (e) {
	  if (fileElem) {
	    fileElem.click();
	  }
	  //e.preventDefault(); // prevent navigation to "#"
	});

	uploadbtn.text("Upload Photos");
	uploadbtn.css({
		'margin-top':'10px',
		'background-color':Util.dark_purple,
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
		'background-color':'#f5f5f5',//Util.yellow,
		'border':'gray 3px solid',
		'border-radius':'3px',
		// 'padding-top':'20px',
		// 'padding-bottom':'20px',
		'overflow-y':'auto',
		'height':'450px',
		// 'margin-bottom':'20px'
		// 'left':''
	});

	$(fileElem).change(function(){
		Util.uploadPhotos(fileElem, photoDiv);
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
	var addTags = $(document.createElement('input'));
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
	});

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
	    // 'background-color':'#504552',
	    'right':'16%',
	    'position':'absolute',
	});
	var savebtn = $(document.createElement('btn'));
	savebtn.attr('type','button');
	savebtn.addClass('btn btn-default');
	// savebtn.attr('data-dismiss','modal');
	savebtn.text("Submit");
	savebtn.css({
	    // 'background-color':'#504552',
	    'right':'9%',
	    'position':'absolute',
	});
	btnsDiv.append(cancelbtn);
	btnsDiv.append(savebtn);

})();