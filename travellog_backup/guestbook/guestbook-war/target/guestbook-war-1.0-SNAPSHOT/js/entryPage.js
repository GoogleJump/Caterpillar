entryPage= (function(){
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

 	var contentDiv = $(document.getElementById('contentDiv'));
 	
 	var photoDiv = $(document.getElementById("photoDiv"));
 	photoDiv.isotope({
 		itemSelector:'.photos',
 	});
 	var photos = $(document.getElementsByClassName("entryPhoto"));
 	var photoTitles =[];
 	for(var i = 0; i < photos.length; ++i) {
 		var photo = $(photos[i]);
 		var photopath = photo.attr("src");
 		var photoTitle = photo.attr("title");
 		var photoDescription=photo.attr("description");
 		photoTitles.push(photoTitle);
 		if(i == 0) photoDiv.append(createThumbnail(photopath, photoTitle, 0));
 		else photoDiv.append(createThumbnail(photopath, photoTitle));
 	}

 	//modal for nest a carousel
 	var modal = Util.makeModal("diplayPhotos",photoTitles[0],true,true);
 	main.append(modal);
 	var modalBody = modal.find('.modal-body');
 	var carousel = Util.makeCarousel("AllPhotos",photos,true);
 	modalBody.append(carousel);


	/* activate the carousel */
	carousel.attr({'data-interval':false});

	/* change modal title when slide changes */
	carousel.on('slid.bs.carousel', function () {
	  $('.modal-title').html($(this).find('.active').attr("title"));
	})

	//remove the carousel indicators, aks white dots
	$('.carousel-indicators').remove();

	/* when clicking a thumbnail */
	$('.row .thumbnail').click(function(){
	    var idx = $(this).parents('div').index();
	  	var id = parseInt(idx);
	  	modal.modal('show');
	  	console.log("Current Id "+id);
	  	carousel.carousel(id-1);
	});

 	/**
    Function for create a div for a trip, 
    @param: path: the path to the thumbnail image.
    caption: the title for the photo
    i: index of the photo
    @return: return the outtermost div
    **/
    function createThumbnail(path, caption,i){
    	var colDiv = $(document.createElement('div'));
       	colDiv.addClass('col-lg-3 col-sm-4 col-xs-6');
    	var thumba=$(document.createElement('a'));
    	thumba.attr("href","#");//TODO: href would be the trip's url later
    	colDiv.addClass("photos");

    	thumba.attr('title',caption);
    	colDiv.append(thumba);
    	var thumbnail=$(document.createElement('img'));
    	thumbnail.addClass("thumbnail img-responsive");
    	thumbnail.attr("src",path);    	
    	thumbnail.attr("alt","No image for the trip available")
    	thumba.append(thumbnail);

    	return colDiv;
    }
    // this.createThumbnail=createThumbnail;

})();