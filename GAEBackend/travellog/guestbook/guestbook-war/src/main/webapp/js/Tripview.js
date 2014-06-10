Tripview = (function(){
	var body = $(document.getElementById("body"));
	var main = $(document.createElement("div"));
	main.addClass("container-fluid");
	body.append(main);
	main.css({
		'position':'absolute',
		'top':'100px',
		'paddingLeft':'35px'
	});
	

	//TODO: get rid of this sample stuff
	//instead, make it so it gets the classes of the dynamically created entries and formats them
	var contentDiv = $(document.createElement('div'));
	main.append(contentDiv);
	contentDiv.addClass("row");

	var titleDiv = $(document.createElement('div'));
		
	titleDiv.addClass('page-header col-md-12');
	contentDiv.append(titleDiv);
	var title = $(document.createElement('h1'));
	title.text('Trip to Wonderland');
	title.addClass('font-effect-fragile');
	title.css({
		'padding-top':'5px',
		'font-family':"Rancho', serif",
		'font-size':'100px'
	})
	titleDiv.append(title);
	var date=$(document.createElement('small'));
	date.text(" created on May 33th");
	title.append(date);
	titleDiv.css({
		'padding-top':'10px'
	});

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

	//is this necessary?? because it really shouldn't be....
	/*addbtn.click(function(){
		addbtn.submit();
	});*/

	//get the form that this button needs to attach to
	var addEntryForm = $(document.getElementById("addEntryForm"));
	addEntryForm.append(addbtn);
	console.log("action of form is: " + addEntryForm.attr("action"));

	//contentDiv.append(addbtn);
	contentDiv.append(addEntryForm);
	var postsWrapper = $(document.createElement('div'));
	postsWrapper.addClass('row');
	contentDiv.append(postsWrapper);
	var allEntries = $(document.createElement('div'));
	allEntries.addClass('col-md-12 blog-main');
	postsWrapper.append(allEntries);
	allEntries.css({
		'padding-top':'5px',
		'height':'auto',
		// 'width':'80%',
		// 'background-color':'background-color'
	});
	var entries = $(document.getElementsByClassName("entry"));
	if(entries.length!==0){
		for(var i=0;i<entries.length;i++){
			var entry = $(entries[i]);
			var entrytitle = entry.children(".Entrytitle").val();
			var entryDescripion = entry.children(".EntryDescripion").val();
			var imgs = entry.children(".Entryimages");
			var hasImage=true,
				hasText=true;
			if(entryDescripion===""){
				hasText=false;
			}
			if(imgs.length===0){
				hasImage=false;
			}
			allEntries.append(Util.makePost(hasText, hasImage,entrytitle,entryDescripion,imgs));
		}
	}

	// allEntries.append(Util.makePost(true, false));
	// allEntries.append(Util.makePost(true, true));
	// allEntries.append(Util.makePost(false, true));


	var pager = $(document.createElement('ul'));
	pager.addClass('pager');
	pager.css({
		'margin-bottom': '60px',
	 	'text-align': 'left',
	 	'width':'100%',
	});
	main.append(pager);
	var prev = $(document.createElement('li'));
	pager.append(prev);
	prev.addClass('previous');
	var preva = $(document.createElement('a'));
	preva.attr('href','#');
	prev.append(preva)
	preva.text("Previous");
	preva.css({
		'color':Util.teal,
	});
	var next = $(document.createElement('li'));
	pager.append(next);
	next.addClass('next');
	var nexta = $(document.createElement('a'));
	next.append(nexta);
	nexta.attr('href','#');
	nexta.text("Next");
	nexta.css({
		'color':Util.teal,
	});
})();
// addEntry();
/*****create the modal for adding entry*****/
// function addEntry(){
//     modal = Util.makeModal('addEntryDiv', "Add Entry",true);
//     body.append(modal);
// 	rowwrapper = $(document.createElement('div'));
//     $("#modalBody").append(rowwrapper);
// 	rowwrapper.addClass('row');
// 	rowwrapper.css({
// 		'padding-top':'9px',
// 		'top':'5%',
//     	'left':'10%',
//     	'position':'relative',
//     	'width':'80%'
//     });

//     titleWrapper = Util.inputGroup('Title: ', 'Untitled');
//     rowwrapper.append(titleWrapper);
    
//     locationWrapper = Util.inputGroup('Where: ','Current Location');
//     rowwrapper.append(locationWrapper);

//     timeWrapper = Util.inputGroup('When: ','Today');
//     rowwrapper.append(timeWrapper);
//     header = $(document.createElement('h3'));
//     desLabel = $(document.createElement('span'));
//     desLabel.addClass('label label-default');
//     desLabel.text("Diary");
//     desLabel.css({
//     	"background-color":Util.dark_purple,
//     });
//     header.append(desLabel);
//     rowwrapper.append(header);

//     diary = $(document.createElement('textarea'));
//     diary.attr('id','editor');
//     diary.css({
//     	'height':'auto',
//     	'position':'absolute',
//     	'width':'100%',	
//  //    	'max-height': '250px'
// 	// height: 250px;
// 	// background-color: white;
// 	// border-collapse: separate; 
// 	// border: 1px solid rgb(204, 204, 204); 
// 	// padding: 4px; 
// 	// box-sizing: content-box; 
// 	// -webkit-box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px 0px inset; 
// 	// box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px 0px inset;
// 	// border-top-right-radius: 3px; border-bottom-right-radius: 3px;
// 	// border-bottom-left-radius: 3px; border-top-left-radius: 3px;
// 	// overflow: scroll;
// 	// outline: none;'
//     })
// 	// diary.wysiwyg();
// 	// $('#editor')
//     rowwrapper.append(diary);
//     addTags = $(document.createElement('input'));
//     addTags.attr('type','text');
//     addTags.addClass('tags');
//     addTags.css({
//         'padding-bottom':'5px',

//     });
//     rowwrapper.append(addTags);
//     addTags.tagsInput({
//         'width': 'auto',
//         'height':'5px',
//         // 'padding-bottom':'5px',
//         //autocomplete_url:'test/fake_plaintext_endpoint.html' //jquery.autocomplete (not jquery ui)
//         // autocomplete_url:'test/fake_json_endpoint.html' // jquery ui autocomplete requires a json endpoint
//     });
// }
// this.addEntry= addEntry;