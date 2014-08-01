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

	contentDiv.append(addbtn);
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

	allEntries.append(Util.makePost(true, false));
	allEntries.append(Util.makePost(true, true));
	allEntries.append(Util.makePost(false, true));


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
