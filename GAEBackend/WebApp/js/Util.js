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
            'id':'modalBody',
        })
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
        savebtn.text("Add");
        savebtn.css({
            'background-color':'#504552'
        });
        footer.append(closebtn);
        footer.append(savebtn);
        return modal;
    }

    /**
    Customized function for input group
    including a divwrapper, title and input box..
    @param: the tilte
    **/

    function inputGroup(name, placeholder){
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
        titleInput.addClass('form-control');
        titleInput.attr('placeholder',placeholder);
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
})();