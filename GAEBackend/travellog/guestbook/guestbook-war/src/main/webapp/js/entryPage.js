entryPage= (function(){
	"use strict";

	var body = $(document.getElementById("body"));
	var main = $(document.getElementById("main"));
	/*var backDiv = $(document.getElementById("backDiv"));
	backDiv.addClass("col-md-12");
	backDiv.css("padding-bottom","10px");
	var back2Trip = $(document.createElement('button'));
	back2Trip.addClass('btn btn-primary');*/
	//main.addClass("container-fluid");
	// body.append(main);
	/*var contentDiv = $(document.createElement('div'));
    main.append(contentDiv);
    contentDiv.addClass("row");
    contentDiv.css('padding-top','50px');

	var backDiv = $(document.createElement('div'));
	backDiv.addClass("col-md-12");
	backDiv.css("padding-bottom","10px");
	var back2Trip = $(document.createElement('button'));
	back2Trip.addClass('btn btn-primary');*/
/*	back2Trip.attr({
		//'data-target':"#",
		'left':'0px',
		'position':'relative'
	});
	back2Trip.text("Back to Trip");
	back2Trip.css({
		'background-color':'#00868B',
	});

	//contentDiv.append(backDiv);
 	backDiv.append(back2Trip);*/


 	/**For title and created day**/
 	/*var blogTitleDiv = $(document.createElement('div'));
 	blogTitleDiv.addClass('row col-md-10 col-md-offset-1');
 	contentDiv.append(blogTitleDiv);
 	var blogTitle = $(document.createElement('h1'));
 	blogTitle.text("We Put Blog Title here");
 	blogTitleDiv.append(blogTitle);

 	/** for photos. */
 	/*var photoDiv = $(document.createElement('div'));
 	photoDiv.addClass('row col-md-10 col-md-offset-1');
 	contentDiv.append(photoDiv);*/

 	var photoDiv = $(document.getElementById("photoDiv"));
 	var photos = $(document.getElementsByClassName("entryPhoto"));
 	for(var i = 0; i < photos.length; ++i) {
 		var photo = $(photos[i]);
 		if(i == 0) photoDiv.append(createThumbnail(photo.attr("id"), photo.attr("value"), 0));
 		else photoDiv.append(createThumbnail(photo.attr("id"), photo.attr("value")));
 	}

 	/*photoDiv.append(createThumbnail("../images/1.JPG", "Image1",0));
 	photoDiv.append(createThumbnail("../images/1.JPG", "Image2"));
 	photoDiv.append(createThumbnail("../images/1.JPG", "Image3"));
 	photoDiv.append(createThumbnail("../images/1.JPG", "Image4"));
 	photoDiv.append(createThumbnail("../images/1.JPG", "Image5"));
 	photoDiv.append(createThumbnail("../images/1.JPG", "Image6"));
 	photoDiv.append(createThumbnail("../images/1.JPG", "Image7"));
 	photoDiv.append(createThumbnail("../images/1.JPG", "Image8"));*/


 	/* copy loaded thumbnails into carousel */
 	$('.row .thumbnail').on('load', function() {
	}).each(function(i) {
	  if(this.complete) {
	  	var item = $('<div class="item"></div>');
	    var itemDiv = $(this).parents('div');
	    var title = $(this).parent('a').attr("title");
	    item.attr("title",title);
	    console.log("image: "+item+"  title"+title);
	  	$(itemDiv.html()).appendTo(item);
	  	item.appendTo('.carousel-inner'); 
	    if (i==0){ // set first item active
	     item.addClass('active');
	    }
	  }
	});

	/* activate the carousel */
	$('#modalCarousel').carousel({interval:false});

	/* change modal title when slide changes */
	$('#modalCarousel').on('slid.bs.carousel', function () {
	  $('.modal-title').html($(this).find('.active').attr("title"));
	})

	/* when clicking a thumbnail */
	$('.row .thumbnail').click(function(){
	    var idx = $(this).parents('div').index();
	  	var id = parseInt(idx);
	  	$('#myModal').modal('show'); // show the modal
	    $('#modalCarousel').carousel(id); // slide carousel to selected
	  	
	});


	/**Now the main content for blog**/
	/*&var blogDiv = $(document.createElement('div'));
	blogDiv.addClass('row col-md-10 col-md-offset-1');
	contentDiv.append(blogDiv);
	var blogText = $(document.createElement('p'));
	blogDiv.append(blogText);
	blogText.text("sajfkjasdl;gjsdkb fsda fkjewg;sdkfjasdkjsadklbjvaskjewijgsdkljga;dfsadf\njlkfasjdkfjsdakfjsd;akfjdslkfjdslkjf;sdakjkbmxcnvjglfkjsadf");
	blogText.css({
 	    'font-family': '"Times New Roman", Times, serif',
 	    'font-size':'25px',
 	    'padding-bottom':'20px',
	});*/

 	/**
    Function for create a div for a trip, 
    TODO: later to consider if there is no thumbnail image, add a picture stands for text. 
    Since bootstraps cannot have a fixed height for all thumbnails, we probably want to crop
    the thumbnails and the picture stands for text should be the same ratio.

    @param: path: the path to the thumbnail image.
    **/
    function createThumbnail(path, caption,i){
    	var colDiv = $(document.createElement('div'));
    	
    	colDiv.addClass('col-lg-3 col-sm-4 col-xs-6');
    	var thumba=$(document.createElement('a'));
    	thumba.attr("href","#");//TODO: href would be the trip's url later
    	// thumba.addClass("thumbnail img-responsive");
    	thumba.attr('title',caption);
    	colDiv.append(thumba);
    	var thumbnail=$(document.createElement('img'));
    	thumbnail.addClass("thumbnail img-responsive");
    	thumbnail.attr("src",path);
    	// thumbnail.attr("src","//placehold.it/600*350");
    	thumbnail.attr("alt","No image for the trip available")
    	thumba.append(thumbnail);

    	var item = $('<div class="item"></div>');
	    var itemDiv = thumbnail.parents('div');
	    var title = thumbnail.parent('a').attr("title");
	    item.attr("title",title);
	    console.log("image: "+item+"  title"+title);
	  	$(itemDiv.html()).appendTo(item);
	  	item.appendTo('.carousel-inner'); 
	    if (i==0){ // set first item active
	     item.addClass('active');
		}
    	return colDiv;
    }
    // this.createThumbnail=createThumbnail;

})();