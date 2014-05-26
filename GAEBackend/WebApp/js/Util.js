Util = (function(){
	"use strict";

    var yellow = '#FFFCD4',
        dark_purple='#504552',
        aqua= '#B1F2EF',
        teal='#5A9491',
        lgter_aqua='#cff8f6';
    // this.yellow = yellow;
	return {
        yellow: yellow,
        aqua: aqua,
        teal: teal,
        lgter_aqua:lgter_aqua,
        dark_purple: dark_purple,
        makeModal: makeModal,
        inputGroup : inputGroup,
        makePost: makePost,
        photoPreview: photoPreview,
        uploadPhotos:uploadPhotos,
    };
	/**
     * make a Modal when a button is clicked. 
     * @param modalId: assign a unique id to the modal so that the btn knows which to trigger
     @param title: the title of the modal.
     @param: islarge: if true, the modal is the large one, else, regular size
     */

    function makeModal(modalId,title, islarge) {
        var modal = $(document.createElement('div'));
        modal.addClass('modal fade');
        modal.attr('id',modalId);
        modal.attr('tabindex','-1');
        modal.attr('role','dialog');
        modal.attr('aria-labelledby',"myModalLabel");
        modal.attr('aria-hidden','true');
        modal.css({
            'height':'100%',
            'width':'100%'
        });

        var dialogmodal = $(document.createElement('div'));
        dialogmodal.addClass('modal-dialog');
        if(islarge){
            dialogmodal.addClass('modal-lg');
        }
        modal.append(dialogmodal);
        var content=$(document.createElement('div'));
        content.addClass('modal-content');
        dialogmodal.append(content);
        content.css({
            'border':'10px black solid',
            'top':'80px',
            'min-height':'300px',
            'min-width':'250px',
            'border-radius':'15px',
            'background-color':'#B1F2EF'
        });
        var headingDiv=$(document.createElement('div'));
        headingDiv.addClass('modal-header');
        headingDiv.css({
            'background-color':'#2F4F4F',
            'font-family':'Serif',
            'color':'#FFFCD4',
            'height':'60px',
        });
        content.append(headingDiv);
        var heading = $(document.createElement('h3'));
        // heading.addClass('panel-title');
        heading.addClass('modal-title');
        heading.text(title);
        headingDiv.append(heading);
        
        var modalBody= $(document.createElement('div'));
        modalBody.attr({
            'id':modalId+'modalBody',
        });
        content.append(modalBody);
        modalBody.addClass("modal-body");

        var footer=$(document.createElement('div'));
        content.append(footer);
        footer.addClass('modal-footer');
        var closebtn = $(document.createElement('btn'));
        closebtn.attr('type','button');
        closebtn.addClass('btn btn-default');
        closebtn.attr('data-dismiss','modal');
        closebtn.text("Close");
        var savebtn = $(document.createElement('btn'));
        savebtn.attr('type','button');
        savebtn.addClass('btn btn-primary');
        // savebtn.attr('data-dismiss','modal');
        savebtn.text("Submit");
        savebtn.css({
            'background-color':'#504552'
        });
        footer.append(closebtn);
        footer.append(savebtn);
        return modal;
    }
    this.makeModal = makeModal;
    /**
    Customized function for input group
    including a divwrapper, title and input box..
    @param: the tilte
    **/

    function inputGroup(name, placeholder,value,type){
        var wrapper = $(document.createElement('div'));
        wrapper.addClass('input-group');
        wrapper.css({
            'padding-bottom':'5px'
        })
        var title = $(document.createElement('span'));
        title.addClass('input-group-addon');
        title.text(name);
        title.css({
            'height':'15px',
            'background-color':'#504552',
            'color':'white',
            'font-weight':'bold'
        });
        wrapper.append(title);
        var titleInput = $(document.createElement('input'));
        titleInput.attr('type','text');
        if(type===1){
            titleInput.datepicker();
        }
        titleInput.addClass('form-control');
        titleInput.attr('placeholder',placeholder);
        if(value){
            titleInput.attr('value',value);
        }
        wrapper.append(titleInput);

        return wrapper;
    }
    
    /**
    function to make a post in trip view, it can be with img only, or text only, or both.
    probably, later add videos too. toEntry is the link to the post's main page.
    */
    function makePost(hasText, hasImg, toEntry, content){
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
        title.text("This is a Post");
        title.css({
            'font-family':'Arial Black", Gadget, sans-serif',
        });

        var date = $(document.createElement('p'));
        date.addClass('blog-post-meta');
        date.text("1min before the end of the world");
        date.css({
            'font-family':'"Comic Sans MS", cursive, sans-serif'
        });
        
        postDiv.append(title);
        postDiv.append(date);
        var content = $(document.createElement('div'));
        content.addClass("row");
        content.css({
            'height':'auto',
            'width':'100%%'
        });        
        if(hasImg===true){
            var carouselDiv = $(document.createElement('div'));
            carouselDiv.addClass("col-xs-6 col-md-4");
            content.append(carouselDiv);
            carouselDiv.css({
                'padding-bottom':'5px',
            })
            var carousel = makeCarousel("myCarousel",3);
            carouselDiv.append(carousel);
        }
        if(hasText===true){
            var textDiv = $(document.createElement("div"));
            content.append(textDiv);
            textDiv.addClass("col-xs-12 col-sm-6 col-md-8");
            var block = $(document.createElement("blockquote"));
            block.css({
                'color':'black',
            });
            textDiv.append(block);
            textDiv.css({
                'height':'auto',
                'padding-bottom':'5px',
                'text-overflow':'ellipsis',
                'overflow':'hidden',
            });
            var text=$(document.createElement('p'));
            text.text("Scientists have for the first time ever solved a 150-year-old evolutionary mystery - the iconic kiwi actually once flew. ")
            block.append(text);            
        }
        postDiv.append(content);
        
        return postDiv;
    }

    /**
    function to create carousel, given a unique id for it, and number of pictures. 
    and data(including info for pics.)
    */
    function makeCarousel(id, picNum, data){        
        var carousel = $(document.createElement('div'));
        // carouselDiv.
        carousel.attr({
            'id':id,
            'data-ride':'carousel',
        });
        carousel.css({
            'height':'100%',
            'width':'100%',
        });
        carousel.addClass('carousel slide');
        //carousel indicators:
        var indicators = $(document.createElement('ol'));
        indicators.addClass('carousel-indicators');
        carousel.append(indicators);
        for(var i=0; i<picNum;i++){
            var li = $(document.createElement('li'));
            li.attr({
                'data-target':"#"+id,
                'data-slide-to':i.toString(),
            });
            if(i==0){
                li.addClass('active');
            };
            indicators.append(li);
        }
        var inner = $(document.createElement('div'));
        inner.addClass('carousel-inner');
        carousel.append(inner);
        for(var j=0; j<picNum; j++){
            if(j===0){
                var item = carouselItem(true, "Slide "+j.toString(),"descript the city you live in");
            }
            else{
                var item = carouselItem(false, "Slide "+j.toString(),"descript the city you live in");
            }
            inner.append(item);            
        }

        var left = $(document.createElement('a'));
        left.addClass('carousel-control left');
        left.attr({
            'href':"#"+id,
            'data-slide':'prev',
        })
        var lefticon = $(document.createElement('span'));
        lefticon.addClass('glyphicon glyphicon-chevron-left')
        left.append(lefticon)
        carousel.append(left);

        var right = $(document.createElement('a'));
        right.addClass('carousel-control right');
        right.attr({
            'href':"#"+id,
            'data-slide':'next',
        });
        var rightIcon = $(document.createElement('span'));
        rightIcon.addClass('glyphicon glyphicon-chevron-right')
        right.append(rightIcon)
        carousel.append(left);
        carousel.append(right);
        return carousel;
    }
    /**function to create a single slide in carousel., given thumbnail, 
    boolean for checking active(first slide)
    picture's caption and description
    */
    function carouselItem(isActive,picCaption,description){
        var item = $(document.createElement('div'));
        item.addClass('item');
        
        var thumb = $(document.createElement('img'));
        thumb.css({
            'width':'100%',
            'height':'auto',
            // 'position':'absolute',
            'display':'block',
            // 'margin':'auto'
        })
        thumb.attr('src','../images/3.jpg');
        item.append(thumb);
        var container = $(document.createElement('div'));
        container.addClass('container');
        item.append(container);
        if(isActive===true){
            item.addClass('active');
        }
        var captionDiv = $(document.createElement('div'));
        captionDiv.addClass('carousel-caption');
        captionDiv.css({
            'z-index':10,
        });
        container.append(captionDiv);
        var caption = $(document.createElement('h1'));
        caption.text(picCaption);
        captionDiv.append(caption);
        var descri= $(document.createElement('p'));
        descri.text(description);
        captionDiv.append(description);

        return item;
    }
    this.carouselItem = carouselItem;

    /**
    function to create a thumbnail div with caption and description
    isTrip: boolean to decide what buttons to put.
    path: path to img src
    file: file to img.(for file picker preview)
    */
    function photoPreview(isTrip, path, file, cap,desc){
        var colDiv = $(document.createElement('div'));
        var descript=desc;
        // var path;
        var spec={
                imgsrc: path,
                title: cap,
                description: desc,
        };
        var modal = editPhoto(spec);
        colDiv.addClass('col-sm-6 col-md-4');
        var thumbDiv = $(document.createElement('div'));
        thumbDiv.addClass('thumbnail');
        colDiv.append(thumbDiv);
        var thumbnail=$(document.createElement('img'));
        if(path){
            thumbnail.attr("src","../images/1.jpg");            
        }
        if(file){
            //probably need to crop them/resize them later if the photos are not in standard size
            var reader = new FileReader();
            reader.onload = function (e) {
                var p = e.target.result;
                $(document.getElementById(cap+"modal")).attr('src',p);
                thumbnail.attr('src', p);
            }
            
            reader.readAsDataURL(file);
        }
        thumbnail.attr("alt","No image for the trip available")
        thumbDiv.append(thumbnail);
        var captionDiv = $(document.createElement('div'));
        captionDiv.addClass("caption");
        thumbDiv.append(captionDiv);
        var caption = $(document.createElement('label'));
        captionDiv.append(caption);
        captionDiv.css('width','100%');
        caption.css({
            'width':'100%',
            'overflow':'hidden',
            'white-space': 'nowrap',
            'text-overflow': 'ellipsis',
        });
        caption.text(cap);
        var descDiv = $(document.createElement('div'));
        descDiv.css({
            'height':'60px',
            'overflow':'hidden',
            // 'white-space': 'nowrap',
            'text-overflow': 'ellipsis',
        });
        // description.text("This is a long and meaningless sdaf sd faskdfgwe;gjegkjew fjsdakljf;description for the thumbnail");
        captionDiv.append(descDiv);
        var btngroup = $(document.createElement('div'));
        btngroup.addClass('row');
        captionDiv.append(btngroup);
        
        var editbtn = $(document.createElement('button'));
        if(isTrip){
            editbtn.text("View");
        }else{
            descDiv.text(descript);
            editbtn.text("Edit");
        }
        editbtn.addClass("btn btn-default col-sm-offset-1");
        // editbtn.attr({
        //     'href':"#",//would be the link to open the modal for editing the photo
        //     // 'role':'button',

        // });
        editbtn.click(function(){
            modal.modal({show:true});
            //open a modal to edit info about the photo

        });
        btngroup.append(editbtn);
        var deletebtn = $(document.createElement('button'));
        deletebtn.text("Delete");
        deletebtn.addClass("btn btn-default delete col-sm-offset-4");
        // deletebtn.attr({
            
        // });
        deletebtn.click(function(){
            colDiv.remove(); 
            modal.remove();   
        });
        btngroup.append(deletebtn);
        return colDiv;
    }
    this.photoPreview=photoPreview;

    function editPhoto(spec){
        var title = spec.title,
            thumb = spec.img,
            description = spec.description;
        var modal = makeModal(title, "Edit Photo", false);
        var body = $(document.getElementById("body"));
        body.append(modal); 
        var modalBody = $(document.getElementById(title+"modalBody"));
        var contentRow = $(document.createElement('div'));
        contentRow.addClass('row');
        modalBody.append(contentRow);
        var thumbRow = $(document.createElement('div'));
        thumbRow.addClass('row');
        contentRow.append(thumbRow);
        var thumbDiv = $(document.createElement('div'));
        thumbDiv.addClass('thumbnail col-md-6 col-sm-offset-3');
        thumbRow.append(thumbDiv);
        var thumbnail=$(document.createElement('img'));
        thumbnail.attr('id',title+"modal");
        thumbDiv.append(thumbnail);
        thumbnail.attr('src',thumb);
        var titleRow =$(document.createElement('div'));
        titleRow.addClass("row col-md-10 col-sm-offset-1");
        var titleInput = inputGroup("Title: ", null, title);
        titleRow.append(titleInput);    
        contentRow.append(titleRow);
        var descriRow = $(document.createElement('div'));
        descriRow.addClass('row col-md-10 col-sm-offset-1');
        contentRow.append(descriRow);
        descriRow.css('padding-top','5px');
        descriRow.css('padding-bottom','10px');
        var desLabel = $(document.createElement('span'));
        desLabel.addClass('label label-default');
        desLabel.text("Description");
        desLabel.css({
            'zoom':'1.5',
            "background-color":Util.dark_purple,
        });
        descriRow.append(desLabel);

        var textWrapper= $(document.createElement('div'));
        textWrapper.addClass('row col-md-10 col-sm-offset-1');
        textWrapper.css('padding-bottom','5px');
        contentRow.append(textWrapper);
        // var textDiv = $(document.createElement('div'));
        // textWrapper.append(textDiv);

        var text = $(document.createElement('textarea'));
        text.css({
            'height':'60px',
            'overflow-y':'auto',
            'width':'100%',
            'resize':'none',
        });
        text.val(description);
        textWrapper.append(text);
        return modal;
    }

    function uploadPhotos(selector, toDiv){
        var files = selector.files;
        var num= files.length;
        for (var i=0;i<num;i++){
            var file = files[i];
            var filename = file.name;
            toDiv.append(photoPreview(false,null,file,filename,"Edit to add Description"));
        }
    }

})();