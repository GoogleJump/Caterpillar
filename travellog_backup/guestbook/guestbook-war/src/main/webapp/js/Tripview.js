Tripview = (function(){

  //don't submit on enter:
     $(window).keydown(function(event){
    if( (event.keyCode == 13) ) {
      event.preventDefault();
      return false;
    }
  });

	//if there is a user key stored, get it and set as parameter
	// console.log("about to get user key");
    var userKey = Util.getQueryVariable("userKey");
    if(userKey != null) {
        console.log("user key was not null, setting param")
    }
    var tripKey = Util.getQueryVariable("tripKey");
	var title = $(document.getElementById('tripTitle'));
	var date=$(document.getElementById('tripDate'));
	var location = $(document.getElementById('tripLocation'));
	var description = $(document.getElementById('tripDescription'));

    var tripspec={
		title: title.text(),
        description: description.text(),
        location: location.text(),
        //longitude: data.trips[i].longitude,
        //latitude: data.trips[i].latitude,
        tripkey: tripKey,
        userkey: userKey,
        depDate: date.attr("start"),
        retDate: date.attr("end"),
        //tags: data.trips[i].tags,
        //link: link,
        //img://src
    };
    var tripbutton = $(document.getElementById("trips_button"));
    tripbutton.attr("href", "/homepage.jsp?userKey=" + userKey);
    var entriesmap =$(document.getElementById("entries_map_button"));
    entriesmap.attr("href", "/MapEntries.html?tripKey=" + tripKey);

	var body = $(document.getElementById("body"));
	var main = $(document.createElement("div"));
	main.addClass("container-fluid");
	body.append(main);
	main.css({
		'position':'absolute',
		'top':'100px',
		'paddingLeft':'35px'
	});
	

	//instead, make it so it gets the classes of the dynamically created entries and formats them
	var contentDiv = $(document.createElement('div'));
	main.append(contentDiv);
	contentDiv.addClass("row");

	var titleDiv = $(document.createElement('div'));
	titleDiv.addClass('page-header col-md-12');
	contentDiv.append(titleDiv);
	//var title = $(document.createElement('h1'));
	title.addClass('font-effect-fragile');
	title.css({
		'padding-top':'5px',
		'font-family':"Rancho', serif",
		'font-size':'100px',
		"display" : "block"
	})

	titleDiv.append(title);
	title.append(date);

	location.css({
		"font-family": "serif",
		"font-size":"14",
		"display" : "block"
	});
	titleDiv.append(location);
		location.css({
		"font-family": "serif",
		"font-size":"12",
		"display" : "block"
	});
	titleDiv.append(description);
	titleDiv.css({
		'padding-top':'10px'
	});

	// var buttondiv = $(document.createElement("div"));
	// buttondiv.addClass("row");
	// buttondiv.css({
	// 	'padding-bottom':'5px'
	// });
    var modal = Util.editBtn("Trip",tripspec);
    var editbtn = $(document.createElement('button'));
    editbtn.text("Edit Trip");
    editbtn.css({
    	'background-color':'#00868B',
		'margin-bottom':'10px'
    });
    editbtn.addClass("btn btn-primary");
    editbtn.click(function(){
        //open a modal to edit info about the photo
        modal.modal({show:true});
    });
    contentDiv.append(editbtn);

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


	//get the form that this button needs to attach to
	var addEntryForm = $(document.getElementById("addEntryForm"));
	addEntryForm.append(addbtn);
	// console.log("action of form is: " + addEntryForm.attr("action"));
	contentDiv.append(addEntryForm);
	// contentDiv.append(buttondiv);
	//Here goes all the posts
	var postsWrapper = $(document.createElement('div'));
	postsWrapper.addClass('row');
	contentDiv.append(postsWrapper);
	var allEntries = $(document.createElement('div'));
	allEntries.addClass('col-md-12 blog-main');
	postsWrapper.append(allEntries);
	allEntries.css({
		'padding-top':'5px',
		'height':'auto',
	});

/*var entries = $(document.getElementsByClassName("entry"));
	if(entries.length!==0){
		for(var i=0;i<entries.length;i++) {
			var entry = $(entries[i]);
			var entrytitle = entry.children(".Entrytitle").val();
			var entryDescripion = entry.children(".EntryDescription").val();
			var imgs = entry.children(".Entryimages");
			var entryKey = entry.children(".EntryKey").val();
			console.log("entry key is:" + entryKey);
			allEntries.append(makePost(entrytitle,entryDescripion,imgs, entryKey)); //TODO null null??
		}
	}*/


	 $.getJSON('getEntries?tripKey='+tripKey, function(data) {
       var entries = [];
       var i = 0;
     for (var i =0; i < data.entries.length; i++) {
       // var link = "/tripview.jsp?tripKey=" + data.trips[i].key;
       // var src = "/getTripImage?tripKey=" + data.trips[i].key;
        console.log("entry json title"+data.entries[i].title);
        console.log("entry json key"+data.entries[i].key);

        var photos = [];
        for(var j = 0; j < data.entries[i].photos.length; j++) {
        	var photo = {
        		title: data.entries[i].photos[j].title,
        		description: data.entries[i].photos[j].description,
        		photoKey: data.entries[i].photos[j].key,
        		blobKey: data.entries[i].photos[j].blobKey,
        		index: j,
        		link: "/getImageFromBlobKey?blobKey="+data.entries[i].photos[j].blobKey,
        	};
        	photos.push(photo);
        }

        var entry_obj = {
            title: data.entries[i].title,
            description: data.entries[i].description,
            location: data.entries[i].location,
            tripkey: data.entries[i].tripKey,
            entrykey: data.entries[i].key, //???this is coming as undefined...
            index: i,
            photos: photos,
            location: data.entries[i].location
        };
       console.log("entryobj json title"+entry_obj.title);
       console.log("entryobj json key"+entry_obj.entryKey);
       console.log("size of photos in this entry is"+entry_obj.photos.length);
       entries.push(entry_obj);
       allEntries.append(makePost(entry_obj.title, entry_obj.description, entry_obj.photos, data.entries[i].key));
   }
   console.log("size of entries is:" + entries.length);

  /* for (var i = 0; i < entries.length; i++) {
   		var entry = $(entries[i]);
   	 allEntries.append(makePost(entry.title, entry.description, entry.photos, entry.entryKey));
   }*/
  });
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

	/**
    function to make a post in trip view, it can be with img only, or text only, or both.
    probably, later add videos too. toEntry is the link to the post's main page.
	@params: entrytitle: title of this entry
			entrydesp: blog for this entry
			imgs: imgs in the entry
			entryKey:key to this entry's page
	@return: the post div
    */
    function makePost(entrytitle, entrydesp, imgs, entryKey){
        // console.log("entry key is:" + entryKey);
        var postDiv = $(document.createElement('div'));
        postDiv.addClass('blog-post');
        var title = $(document.createElement('h2'));
        
        title.hover(function() {
            $(this).css({
                'cursor':'pointer',
                'text-decoration':'underline',                
            });
        }, function() {
            $(this).css({
                'cursor':'auto',
                'text-decoration':'none',
            });
        });
        title.text(entrytitle);
        title.css({
            'font-family':'Arial Black", Gadget, sans-serif',
        });

        var entryPageLink = $(document.createElement('a'));
        entryPageLink.attr('href', '/entryPage.jsp?entryKey='+entryKey);
        entryPageLink.append(title);
        var date = $(document.createElement('p'));
        date.addClass('blog-post-meta');
        date.css({
            'font-family':'"Comic Sans MS", cursive, sans-serif'
        });
        
        postDiv.append(entryPageLink);
        postDiv.append(date);
        var content = $(document.createElement('div'));
        content.addClass("row");
        content.css({
            'height':'auto',
            'width':'100%%'
        });        
        if(imgs.length!==0){
            var carouselDiv = $(document.createElement('div'));
            carouselDiv.addClass("col-xs-6 col-md-4");
            content.append(carouselDiv);
            carouselDiv.css({
                'padding-bottom':'5px',
            })
            var carousel = Util.makeCarousel("myCarousel", imgs);
            carouselDiv.append(carousel);
        }
        var textDiv = $(document.createElement("div"));
        content.append(textDiv);
        textDiv.addClass("col-xs-12 col-sm-6 col-md-8");
        var block = $(document.createElement("blockquote"));
        block.css({
            'color':'black',
        });
        textDiv.append(block);

        textDiv.css({
            'max-height':'300px',
            'padding-bottom':'5px',
            'text-overflow':'ellipsis',
            'overflow':'hidden',
            'word-wrap': 'break-word'
        });
        var text=$(document.createElement('p'));
        if(entrydesp!==""){
	        text.text(entrydesp)
        }else{
          	text.text("No blog available");
        }
        block.append(text);            
        postDiv.append(content);
        
        return postDiv;
    }
    this.makePost = makePost;
})();
