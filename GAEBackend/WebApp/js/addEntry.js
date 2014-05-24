body = $(document.getElementById("body"));
main = $(document.getElementById("main"));
// main.addClass("container-fluid");
// body.append(main);
// main.css({
// 	'position':'absolute',
// 	'top':'100px',
// 	// 'paddingLeft':'35px'
// });
contentDiv = $(document.createElement('div'));
main.append(contentDiv);
contentDiv.addClass("row");
main.css('padding-bottom','60px');
pageheader = $(document.createElement('div'));
pageheader.addClass('page-header col-md-12');
contentDiv.append(pageheader);
header = $(document.createElement('h1'));
header.text("Add a Post");
pageheader.append(header);

//title:
// titleDiv = $(document.createElement('div'));
// titleDiv.addClass('row');
// contentDiv.append(titleDiv);
title = Util.inputGroup('Title: ', "Untitled");
title.addClass('col-md-6 col-sm-offset-1');
contentDiv.append(title);

// metaDiv =$(document.createElement('div'));
// metaDiv.addClass('row');
// metaDiv.addClass('col-md-8 col-sm-offset-1');
// contentDiv.append(metaDiv);
when = Util.inputGroup('When: ',"Today");
when.addClass('col-md-6 col-sm-offset-1');
contentDiv.append(when);
where = Util.inputGroup('Where: ',"Current Location");
contentDiv.append(where);
where.addClass('col-md-6 col-sm-offset-1');

btnDiv = $(document.createElement('div'));
contentDiv.append(btnDiv);
btnDiv.addClass('col-sm-offset-1');
uploadbtn = $(document.createElement('button'));
uploadbtn.addClass("btn btn-primary");
btnDiv.append(uploadbtn);
uploadbtn.text("Upload Photos");
uploadbtn.css({
	'margin-top':'10px',
	'background-color':Util.dark_purple,
});

photoDiv = $(document.createElement('div'));
photoDiv.addClass("row col-sm-offset-1 col-md-10");
contentDiv.append(photoDiv);
photoDiv.css({
	'margin-top':'10px',
	'background-color':'#f5f5f5',//Util.yellow,
	'border':'gray 3px solid',
	'border-radius':'3px',
	// 'padding-top':'20px',
	// 'padding-bottom':'20px',
	'overflow-y':'auto',
	'height':'450px',
	'margin-bottom':'20px'
	// 'left':''
});
photoDiv.append(Util.photoPreview());
photoDiv.append(Util.photoPreview());
photoDiv.append(Util.photoPreview());
photoDiv.append(Util.photoPreview());

labelDiv = $(document.createElement('div'));
labelDiv.addClass('row col-sm-offset-1');
labelDiv.css('padding-bottom','10px');
contentDiv.append(labelDiv);
notebook = $(document.createElement('h3'));
desLabel = $(document.createElement('span'));
desLabel.addClass('label label-default');
desLabel.text("Write down your thoughts");
desLabel.css({
	"background-color":Util.dark_purple,
	// 'margin-top':'10px'
});
notebook.append(desLabel);
labelDiv.append(notebook);

//TODO: rich text editor goes here. 
textWrapper= $(document.createElement('div'));
textWrapper.addClass('row');
textWrapper.css('padding-bottom','15px');
contentDiv.append(textWrapper);
textDiv = $(document.createElement('div'));
textDiv.addClass('col-md-10 col-sm-offset-1');
textWrapper.append(textDiv);

text = $(document.createElement('textarea'));
text.css({
	'height':'300px',
	'overflow-y':'auto',
	'width':'100%'
});
textDiv.append(text);

tagsWrapper = $(document.createElement('div'));
tagsWrapper.addClass('row');
// tagsWrapper.css('margin-top','15px');
contentDiv.append(tagsWrapper);
tagsDiv= $(document.createElement('div'));
tagsDiv.addClass('col-md-10 col-sm-offset-1');
tagsWrapper.append(tagsDiv);
addTags = $(document.createElement('input'));
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

btnsDiv = $(document.createElement('div'));
btnsDiv.addClass('row col-sm-offset-1');
btnsDiv.css('margin-top','10px');
// btnsDiv.css('margin-bottom','10px');
contentDiv.append(btnsDiv);
btns = $(document.createElement('div'));
btns.addClass('col-md-12 col-sm-offset-1');
btnsDiv.append(btns);

cancelbtn = $(document.createElement('btn'));
cancelbtn.attr('type','button');
cancelbtn.addClass('btn btn-default pull-left');
// cancelbtn.attr('data-dismiss','modal');
cancelbtn.text("Cancel");
cancelbtn.css({
    // 'background-color':'#504552',
    'right':'16%',
    'position':'absolute',
});
savebtn = $(document.createElement('btn'));
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