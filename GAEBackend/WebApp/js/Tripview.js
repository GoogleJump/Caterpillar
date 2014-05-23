body = $(document.getElementById("body"));

contentDiv = $(document.createElement('div'));
body.append(contentDiv);
contentDiv.addClass("row");
contentDiv.css({
	'top':'150px',
	'left':'5%',
	'position':'absolute',
	'height':'auto',
	'width':'100%%'
});
titleDiv = $(document.createElement('div'));
	
titleDiv.addClass('span5 offset2');
contentDiv.append(titleDiv);
// contentDiv.append(titleDiv);
title = $(document.createElement('h1'));
title.text('Trip to Wonderland');
title.addClass('font-effect-fragile');
title.css({
	'font-family':"Rancho', serif",
	'font-size':'100px'
})
titleDiv.append(title);
date=$(document.createElement('small'));
date.text(" created on May 33th");
title.append(date);
titleDiv.css({
	'padding-top':'10px'
});
// <button class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">Large modal</button>

// <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
//   <div class="modal-dialog modal-lg">
//     <div class="modal-content">
//       ...
//     </div>
//   </div>
// </div>/
addbtn = $(document.createElement('button'));
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

contentDiv.append(addbtn);

allEntries = $(document.createElement('div'));
allEntries.addClass('col-md-8 blog-main');
contentDiv.append(allEntries);
allEntries.css({
	'padding-top':'5px',
	'height':'auto',
	'width':'80%',
	// 'background-color':'background-color'
});

allEntries.append(Util.makePost());

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