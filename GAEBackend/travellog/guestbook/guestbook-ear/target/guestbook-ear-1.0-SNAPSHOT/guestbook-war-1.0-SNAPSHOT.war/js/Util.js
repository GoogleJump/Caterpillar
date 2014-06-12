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
        tripPreview:tripPreview,
        photoPreview: photoPreview,
        uploadPhotos:uploadPhotos,
        userSignup: userSignup,
    };
	/**
     * make a Modal when a button is clicked. 
     * @param modalId: assign a unique id to the modal so that the btn knows which to trigger
     @param title: the title of the modal.
     @param: islarge: if true, the modal is the large one, else, regular size
     TODO: submit function for the button?
     */

    function makeModal(modalId,title, islarge) {
        var modal = $(document.createElement('div'));
        modal.addClass('modal fade');
        modal.attr('data-backdrop','false');
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
        closebtn.attr('id',modalId+'closebtn');
        closebtn.text("Close");
        // console.log(modalId+'closebtn');
        var savebtn = $(document.createElement('btn'));
        savebtn.attr('type','button submit');
        savebtn.addClass('btn btn-primary');
        // savebtn.attr('data-dismiss','modal');
        savebtn.text("Submit");
        savebtn.attr('id',modalId+'savebtn');
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
    type1: date
    type2: email
    type3: password
    type4: location
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
        wrapper.append(titleInput);
        if(type===1){
            //add the button to show the calendar
            wrapper.addClass('date');
            var btn = $(document.createElement('span'));
            btn.addClass('input-group-addon');
            var icon = $(document.createElement('span'));
            icon.addClass('glyphicon glyphicon-calendar');
            btn.append(icon);
            wrapper.append(btn);
            titleInput.addClass('form-control');
            wrapper.datetimepicker({ //SA: commented out b/c was getting error ****
                // pickTime: false,
            });
            titleInput.attr({
                'type':'datetime',
                'required':true,
                'disabled':true,
            });
        }
        if(type===2){
            titleInput.attr({
                'type':'email',
                'required':true,
            });
        }
        if(type===3){
            titleInput.attr('name','password');
            titleInput.attr('type','password');
        }
        titleInput.addClass('form-control');
        titleInput.attr('placeholder',placeholder);
        if(value){
            titleInput.attr('value',value);
        }

        return wrapper;
    }
    /*

    */
    function userSignup(){
        var id = "signup";
        var modal = makeModal(id,'Sign Up',false);
        var body = $(document.getElementById("body"));
        body.append(modal);
        // var contentDiv= $(document.createElement('div'));
        // console.log(id+"modalBody");
        var modalBody = $(document.getElementById(id+"modalBody"));
        var contentRow = $(document.createElement('div'));
        contentRow.addClass('row');
        contentRow.css('width','80%');
        modalBody.append(contentRow);
        var registration = $(document.createElement('form'));
        registration.addClass('form-horizontal');
        contentRow.append(registration);
        registration.attr('method','post');
        registration.attr('action', '/insertUser');
        registration.attr('id','registration');
        // var username = $(document)
        var username = inputGroup('Username','Pick a Username',null,null);
        username.children('input').eq(0).attr({
            'name':'username',
            'required':'true',
        });
        registration.append(username);
         
       
        var pwd = inputGroup('Password',"Please enter your password", null, 3);
        pwd.children('input').eq(0).attr('name', 'password');
        registration.append(pwd);
       
        var emailinput = inputGroup('Email', 'Please enter your email address', null, 2);
        emailinput.children('input').eq(0).attr({
            'name': 'email',
            // 'type':'email',
            // 'required':true,
        });
        registration.append(emailinput);
        // emailinput.attr('required');
        // password.attr('type','password');

        var firstname = inputGroup('First Name', null, null, null);
        firstname.children('input').eq(0).attr({
            'name':'firstname',
            'required':'true',
        });
        registration.append(firstname);

        var lastname = inputGroup('Last Name', null, null, null);
        lastname.children('input').eq(0).attr({
            'name':'lastname',
            'required':'true',
        });
        registration.append(lastname);

        var submit_input = $(document.createElement('input')); //actually calls servlet, but invisible
        submit_input.attr('type', 'submit');
        submit_input.css({
            /*'height':'100px',
            'width': '100px',
            'position':'absolute',
            'z-index': '1000'*/
            'display' : 'none'
        });

        registration.append(submit_input);



        var submitbtn = $(document.getElementById(id+'savebtn')); //triggers the button that will actually call servlet
        submitbtn.click(function() {
            //which one of these will work??
            submit_input.submit();
            submit_input.click();
            //submit_input.toggle();
        });

        // $("#registration").validate({
        //     rules:{
        // // $.extend(settings, {
        // //     rules: {
        //         email: {required:true,email: true},
        //         pwd:{required:true},
        //         firstname:{required:true},
        //         lastname:{required:true},
        //         username:{required:true},
        //     },
        //     messages: {
        //         "email": "<li>Please enter a valid Email address.</li>",
        //         pwd: "password required",
        //         firstname:"Please enter your first name",
        //         lastname:"Please enter your last name",
        //         username:"Please enter your username",
        //     }
        // });

        var closebtn = $(document.getElementById(id+'closebtn'));
        console.log(id+'closebtn');
        closebtn.click(function(){
            console.log("clicked");
            registration.find('input:text, input:password, input:file, select, textarea').val('');
            // registration.validate().resetForm();
            // $(this).closest('form').find("input[type=text], textarea").val("");
        });
        return modal;
    }
    /**
    function to make a post in trip view, it can be with img only, or text only, or both.
    probably, later add videos too. toEntry is the link to the post's main page.
    */
    function makePost(hasText, hasImg, entrytitle, entrydesp, imgs,toEntry, content){
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
            var carousel = makeCarousel("myCarousel",imgs.length, imgs);
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
            text.text(entrydesp)
            block.append(text);            
        }
        postDiv.append(content);
        
        return postDiv;
    }

    /**
    function to create carousel, given a unique id for it, and number of pictures. 
    and data(including info for pics.)
    */
    function makeCarousel(id, picNum,imgs){        
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
                var item = carouselItem(true, imgs[j],"Slide "+j.toString(),"descript the city you live in");
            }
            else{
                var item = carouselItem(false,imgs[j], "Slide "+j.toString(),"descript the city you live in");
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
    function carouselItem(isActive,img,picCaption,description){
        var item = $(document.createElement('div'));
        item.addClass('item');
        
        var thumb = $(img);
        thumb.css({
            'width':'100%',
            'height':'auto',
            // 'position':'absolute',
            'display':'block',
            // 'margin':'auto'
        })
        // thumb.attr('src','../images/3.jpg');
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

    function tripPreview(src,spec,cap,desc,link){
        var colDiv = $(document.createElement('div'));
        // var thumba=$(document.createElement('a'));
        // thumba.attr('href',link);
        // colDiv.append(thumba);        
        // var descript=desc;
        // var spec={
        //         img: src,
        //         title: cap,
        //         description: desc,
        // };
        var modal = editBtn("Trip",spec);
        colDiv.addClass('col-sm-6 col-md-4');
        var thumbDiv = $(document.createElement('div'));
        thumbDiv.addClass('thumbnail');
        colDiv.append(thumbDiv);
        var thumbnail=$(document.createElement('img'));
        thumbnail.attr("src",src);

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
        caption.text(spec.title);
        var descDiv = $(document.createElement('div'));
        descDiv.css({
            'height':'60px',
            'overflow':'hidden',
            // 'white-space': 'nowrap',
            'text-overflow': 'ellipsis',
        });
        // var descript=$(document.createElement('p'))
        if(spec.desc===null){
            descDiv.text("Click Edit to add description");
        }else{
            descDiv.text(spec.desc);
        }
        captionDiv.append(descDiv);
        var btngroup = $(document.createElement('div'));
        btngroup.addClass('row');
        captionDiv.append(btngroup);

        var editbtn = $(document.createElement('button'));
        // descDiv.text(desc);
        editbtn.text("Edit");
        editbtn.addClass("btn btn-default col-sm-offset-1");
        editbtn.click(function(){
            modal.modal({show:true});
            //open a modal to edit info about the photo

        });

        var viewbtna = $(document.createElement('a'));
        var viewbtn = $(document.createElement('button'));
        viewbtn.text("View");
        viewbtn.addClass('btn btn-default col-sm-offset-1');
        //TODO: lead to the trip's page when clicked
        viewbtna.attr("href",spec.link);
        viewbtn.click(function(){});
        viewbtna.append(viewbtn);
        var deletebtn = $(document.createElement('button'));
        deletebtn.text("Delete");
        deletebtn.addClass("btn btn-default delete col-sm-offset-1");
        deletebtn.click(function(){
            colDiv.remove(); 
            modal.remove();   
        });
        btngroup.append(editbtn);
        btngroup.append(viewbtna);
        btngroup.append(deletebtn);
        return colDiv;
    }
    /**
    function to create a thumbnail div with caption and description
    isTrip: boolean to decide what buttons to put.
    path: path to img src
    file: file to img.(for file picker preview)
    */
    function photoPreview(file,cap,desc){
        var colDiv = $(document.createElement('div'));
        var descript=desc;
        // var path;
        var spec={
                // imgsrc: path,
                title: cap,
                description: desc,
        };
        var modal = editBtn(null,spec);
        colDiv.addClass('col-sm-6 col-md-4');
        var thumbDiv = $(document.createElement('div'));
        thumbDiv.addClass('thumbnail');

        colDiv.append(thumbDiv);
        var imgDiv = $(document.createElement('div'));
        imgDiv.css({
            'width':'251px',
            'height':'166px',
            'background-size':'contain',
            'background-repeat': 'no-repeat',
            'background-position':'center',
        })
        thumbDiv.append(imgDiv);
        // var thumbnail=$(document.createElement('img'));
        // thumbnail.css({
        //     'height':'180px',
        //     'width':'260px',
        // })
        //get the thumbnail from the upload file. Sync the thumbnail in corresponding modal as well
        if(file){
            //probably need to crop them/resize them later if the photos are not in standard size
            var reader = new FileReader();
            reader.onload = function (e) {
                var p = e.target.result; 
                $(document.getElementById(cap+"modal")).attr('src',p);
                imgDiv.css('background-image','url(' + p + ')');
            }
            
            reader.readAsDataURL(file);
        }
        // thumbnail.attr("alt","Oops, there is an error with the image")
        // imgDiv.append(thumbnail);
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
        // if(isTrip){
        //     var viewbtn = $(document.createElement('button'));
        //     viewbtn.text("View");
        //     viewbtn.addClass('btn btn-default col-sm-offset-1');
        //     //TODO: lead to the trip's page when clicked
        //     viewbtn.click(function(){});
        //     btngroup.append(viewbtn);
        // }
        descDiv.text(descript);
        editbtn.text("Edit");
        // }
        editbtn.addClass("btn btn-default col-sm-offset-1");
        editbtn.click(function(){
            modal.modal({show:true});
            //open a modal to edit info about the photo

        });
        btngroup.append(editbtn);
        var deletebtn = $(document.createElement('button'));
        deletebtn.text("Delete");
        deletebtn.addClass("btn btn-default delete col-sm-offset-4");

        deletebtn.click(function(){
            colDiv.remove(); 
            modal.remove();   
        });
        btngroup.append(deletebtn);
        return colDiv;
    }
    this.photoPreview=photoPreview;
    /**
    this is the function for editBtn for both thumbnail div for tripview and addEntry.
    @param: type: indicates which kind of edit button this is for
    @param: spec: includes title, description,date,loc and image. 
    */
    function editBtn(type, spec){
        console.log(spec);
        var title = spec.title,
            thumb = spec.img,
            description = spec.description,
            // date=spec.date,
            loc = spec.location;

        var modal;
        if(type==="Trip"){
            modal=makeModal(spec.link, "Edit Photo", false);  
        } else{
            modal=makeModal(spec.link, "Edit Trip", false);
        }
        var body = $(document.getElementById("body"));
        body.append(modal); 
        var modalBody = $(document.getElementById(spec.link+"modalBody"));
        var contentForm = $(document.createElement('form'));
        modalBody.append(contentForm);
        var contentRow = $(document.createElement('div'));
        contentRow.addClass('row');
        contentForm.append(contentRow);
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

        
        if(type==="Trip"){
            var startDiv = $(document.createElement('div'));
            startDiv.addClass("row col-md-10 col-sm-offset-1");
            var start = Util.inputGroup('Start: ',"Choose a start date",null,1);
            startDiv.append(start);
            // start.addClass('row col-md-10 col-sm-offset-1');
            start.children('input').eq(0).attr('name', 'departDate');
            var endDiv = $(document.createElement('div'));
            endDiv.addClass("row col-md-10 col-sm-offset-1");
            var end = Util.inputGroup('End: ',"Choose an end date",null,1);
            endDiv.append(end);
            end.children('input').eq(0).attr('name', 'retDate');
            // end.addClass('row col-md-10 col-sm-offset-1');
           
            contentRow.append(startDiv); 
            contentRow.append(endDiv);
            start.data("DateTimePicker").setDate(spec.depDate);
            end.data("DateTimePicker").setDate(spec.retDate);
            //make sure the start date is always in front of the end date
            start.on("dp.change",function (e) {
                end.data("DateTimePicker").setMinDate(e.date);
            });
            end.on("dp.change",function (e) {
                start.data("DateTimePicker").setMaxDate(e.date);
            });
            var locRow =$(document.createElement('div'));
            locRow.addClass("row col-md-10 col-sm-offset-1");
            var locInput = inputGroup("Location: ", null, loc);
            locRow.append(locInput);    
            contentRow.append(locRow);
        }
        
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
    this.editbtn = editbtn;

    /*function for uploading photo button, called in addEntry.**/
    //returns the file names (todo: or maybe should return files themselves??)
    //trying to return fileurl instead
    function uploadPhotos(selector, toDiv){
        var files = selector.files;
        var filenames = new Array(files.length);
        var fileurl = new Array(files.length);
        var num= files.length;
        for (var i=0;i<num;i++){
            var file = files[i];
            var filename = file.name;
            filenames[i] = filename;
            fileurl[i] = URL.createObjectURL(file.slice());
            toDiv.append(photoPreview(file,filename,"Edit to add Description"));
        }
        return fileurl;
    }

})();