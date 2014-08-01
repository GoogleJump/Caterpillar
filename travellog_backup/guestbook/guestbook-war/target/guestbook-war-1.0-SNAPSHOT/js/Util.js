Util = (function(){
    "use strict";

    var yellow = '#FFFCD4',
    dark_purple='#504552',
    aqua= '#B1F2EF',
    teal='#5A9491',
    lgter_aqua='#cff8f6';

    return {
        yellow: yellow,
        aqua: aqua,
        teal: teal,
        lgter_aqua:lgter_aqua,
        dark_purple: dark_purple,
        addNewTrip:addNewTrip,
        makeModal: makeModal,
        inputGroup : inputGroup,
        makeCarousel:makeCarousel,
        editBtn:editBtn,
        photoPreview: photoPreview,
        uploadPhotos:uploadPhotos,
        getQueryVariable: getQueryVariable,
        getFromLocalStorage : getFromLocalStorage,
        getTrip : getTrip,
        getEntry : getEntry,
    };

    /***
    add a trip modal. 
    @param: the container that you are going to append the modal to
    **/
    function addNewTrip(body){
        var modal = Util.makeModal('addTrip', "Add Trip",false);
        body.append(modal);

        var rowwrapper = $(document.createElement('div'));
        //for submit purpose
        var addTripform = $(document.createElement('form'));
        addTripform.attr('method','post');
        addTripform.attr('action', '/insertTrip');
        addTripform.attr('id','addTripform');
        var submit_input = $(document.createElement('input')); //actually calls servlet, but invisible
        submit_input.attr('type', 'submit');
        submit_input.attr('name', 'submitbtn');
        submit_input.attr('id', 'addTripInput');
        submit_input.css({
            'display' : 'none'
        });
        addTripform.append(submit_input);
        rowwrapper.append(addTripform);
        $("#addTripmodalBody").append(rowwrapper);
        rowwrapper.addClass('row');
        rowwrapper.css({
            'padding-top':'9px',
            'top':'5%',
            'left':'10%',
            'position':'relative',
            'width':'80%'
        });
        var titleWrapper = Util.inputGroup('Title: ', 'title', 'Untitled',null,null,true);
        addTripform.append(titleWrapper);

        var start = Util.inputGroup('Start: ', 'departDate', "Choose a start date",null,1,false);
        start.addClass('col-md-12');
        var end = Util.inputGroup('End: ', 'retDate', "Choose an end date",null,1,false);
        end.addClass('col-md-12');
        addTripform.append(start); 
        addTripform.append(end);
        //make sure the start date is always in front of the end date

        start.on("dp.change",function (e) {
            end.data("DateTimePicker").setMinDate(e.date);
        });
        end.on("dp.change",function (e) {
            start.data("DateTimePicker").setMaxDate(e.date);
        });
        var locationWrapper = Util.inputGroup('Where: ', 'location', 'Enter a location',null,4,false);
        addTripform.append(locationWrapper);
        locationWrapper.addClass('col-md-12');
        
        var description = $(document.createElement('textarea'));
        description.attr('placeholder','Description');
        description.css({
            'height':'200px',
            'width':'100%',
            'resize':'none',
            'padding-bottom':'5px',

        });
        description.attr('name', 'description');
        addTripform.append(description);

        //invisible input that gives the userKey as a parameter:
        //(kind of hacky, but we'll see if it works... and it does! yay)
        var keyUser = $(document.createElement('input'));
        keyUser.attr('type, text');
        keyUser.attr('name', 'userKey');
        keyUser.css("display", "none");
        setKey();
        addTripform.append(keyUser);

        var addTags = $(document.createElement('input'));
        addTags.attr('type','text');
        addTags.addClass('tags');
        addTags.css({
            'padding-bottom':'5px',

        });

        addTripform.append(addTags);
        addTags.tagsInput({
            'width': 'auto',
            'height':'5px',
            'padding-bottom':'5px',
            //autocomplete_url:'test/fake_plaintext_endpoint.html' //jquery.autocomplete (not jquery ui)
            // autocomplete_url:'test/fake_json_endpoint.html' // jquery ui autocomplete requires a json endpoint
        });



        function setKey() {
        //store user key:
            if(typeof(Storage) !== "undefined") {
                console.log("there is session storage");
            } else {
                console.log("browser does not support storage");
            }

            var key = getQueryVariable("userKey");
            if(key != null) {
                keyUser.attr('value', key);
                putToLocalStorage("userKey", key);
                console.log("set key user attribute as:" + key);
            }
        }


        //insert url parameter
        function insertParam(key, value) {
            key = escape(key); value = escape(value);

            var kvp = document.location.search.substr(1).split('&');
            if (kvp == '') {
                document.location.search = '?' + key + '=' + value;
            }
            else {

                var i = kvp.length; var x; while (i--) {
                    x = kvp[i].split('=');

                    if (x[0] == key) {
                        x[1] = value;
                        kvp[i] = x.join('=');
                        break;
                    }
                }

                if (i < 0) { kvp[kvp.length] = [key, value].join('='); }

                //this will reload the page, it's likely better to store this until finished
                document.location.search = kvp.join('&');
            }
        }

        //put key value pair into local storage
        function putToLocalStorage(key, value) {
            localStorage.setItem(key, value);
        }

        var submitbtn = $(document.getElementById('addTripsavebtn'));
        body.on('click', '#addTripsavebtn', function () {

            submit_input.submit();
            submit_input.click();
        });
        return modal;
    }

    //get value from local storage
    function getFromLocalStorage(key) {
        var item = localStorage.getItem(key);
        if(item) { 
            return item;
        }
        else {
            console.log("could not store " + key);
            return null;
        }
    }

    //gets parameter either from url if available or local storage if not
    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        } 
        //if it's not a parameter, check if it's in local storage
        return getFromLocalStorage(variable);
    }

    /**
     * make a Modal when a button is clicked. 
     * @param modalId: assign a unique id to the modal so that the btn knows which to trigger
     @param title: the title of the modal.
     @param: islarge: if true, the modal is the large one, else, regular size
     @param: isForEntry: if the modal is for entry's photos, we don't need the save button
     @return: the modal
     */

     function makeModal(modalId,title, islarge, isForEntry) {
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
        footer.append(closebtn);

        if(isForEntry!==true){
            var savebtn = $(document.createElement('btn'));
            savebtn.attr('type','button submit');
            savebtn.addClass('btn btn-primary');
            // savebtn.attr('data-dismiss','modal');
            savebtn.text("Submit");
            savebtn.attr('id',modalId+'savebtn');
            savebtn.css({
                'background-color':'#504552'
            });
            footer.append(savebtn);
        }
        return modal;
    }
    this.makeModal = makeModal;
    
    /**
    Customized function for input group
    including a divwrapper, title and input box..
    @param: nametitle: title for this input
    @param: inputname: name attribute for the inputfield.
    @param: placeholder: placeholder for the input
    @param: value: value for the input
    @param: type
    type1: date
    type2: email
    type3: password
    type4: location
    @param: isrequired: if the input is required. 
    return: returns the wrapper div that contents everything
    **/

    function inputGroup(nametitle, inputname, placeholder,value,type, isrequired){
        var wrapper = $(document.createElement('div'));
        wrapper.addClass('input-group');
        wrapper.css({
            'padding-bottom':'5px'
        })
        var title = $(document.createElement('span'));
        title.addClass('input-group-addon');
        title.text(nametitle);
        title.css({
            'height':'15px',
            'background-color':'#504552',
            'color':'white',
            'font-weight':'bold'
        });
        wrapper.append(title);
        var titleinput=document.createElement('input');
        var titleInput = $(titleinput);
        titleInput.attr('type','text');
        titleInput.attr('name', inputname);
        wrapper.append(titleInput);
        if(isrequired===true){
            titleInput.attr('required',true);
        }
        switch(type){
            case 1:
                wrapper.addClass('date');
                var btn = $(document.createElement('span'));
                btn.addClass('input-group-addon');
                var icon = $(document.createElement('span'));
                icon.addClass('glyphicon glyphicon-calendar');
                btn.append(icon);
                wrapper.append(btn);
                wrapper.datetimepicker({ //SA: temporarily commented out b/c was getting error ****
                    // pickTime: false,
                });
                break;
            case 2:
                titleInput.attr({
                    'type':'email',
                });
                break;
            case 3:
                titleInput.attr('type','password');
                break;
            case 4:
                var autocomplete = new google.maps.places.Autocomplete(
                      titleinput,
                      { types: ['geocode'] });
                var latitude = $(document.createElement('input'));
                var longitude = $(document.createElement('input'));
                longitude.css("display", "none");
                longitude.attr("name", "longitude");
                latitude.css("display", "none");
                latitude.attr("name", "latitude");
                latitude.attr("value", "");//initialize lat/long as empty:
                longitude.attr("value", "");
                longitude.innerHTML = "";
                wrapper.append(latitude);
                wrapper.append(longitude);
                  google.maps.event.addListener(autocomplete, 'place_changed', function() {
                    var place = autocomplete.getPlace();
                    latitude.attr("value", place.geometry.location.lat());
                    longitude.attr("value", place.geometry.location.lng());
                  });

        }
        titleInput.addClass('form-control');
        titleInput.attr('placeholder',placeholder);
        if(value){
            titleInput.attr('value',value);
        }
        return wrapper;
    }

    function getTrip(tripKey, useKey) {
        var trip;
         $.getJSON('getTrip?tripKey='+tripKey, function(data) {
            var src = "/getTripImage?tripKey=" + tripKey
            console.log("trip json title"+data.trip.title);
            console.log("trip json key"+data.trip.key);

            var trip_obj = {
                title: data.trip.title,
                description: data.trip.description,
                location: data.trip.location,
                tripkey: data.trip.key,
                userkey: data.trip.userKey,
                depDate: data.trip.departDate,
                retDate: data.trip.returnDate,
                tags: data.trip.tags,
                img:src,
                lat: parseFloat(data.trip.latitude),  //TODO??
                lon: parseFloat(data.trip.longitude),
            };
            trip = trip_obj;
            console.log("trip in getTrip function is "+trip_obj.title );
        //}
        });
        var interval = setInterval(function(){
       if (trip != undefined){
        console.log("trip in getTrip function after json interval is " + trip.title);
         clearInterval(interval);
         return trip;
       }
    }, 200);
    }

    function getEntry(entryKey, tripKey) {
        var entry = $.getJSON('getEntry?entryKey='+entryKey, function(data) {

        var entry_obj = {
            title: data.entry.title,
            description: data.entry.description,
            location: data.entry.location,
            tripkey: data.entry.tripKey,
            entrykey: data.entry.key,
            tags: data.entry.tags,
            lat: parseFloat(data.entry.latitude),  //TODO!!??
            lon: parseFloat(data.entry.longitude),
         };
         return entry_obj;

     //}
    });
        return entry;
    }

    /**
    function to create carousel, given a unique id for it, and number of pictures. 
    and data(including info for pics.)
    if it is for entry, we don't want items to have captions and descriptions for now in the carousel
    make sure the imgs passed in are <img> and all have attribute title and description
    */

    function makeCarousel(id, imgs,isForEntry){        
        var carousel = $(document.createElement('div'));
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

        var picNum = imgs.length;
        var inner = $(document.createElement('div'));
        inner.addClass('carousel-inner');
        carousel.append(inner);
        for(var i=0; i<picNum;i++){
            var li = $(document.createElement('li'));
            var item;
            li.attr({
                'data-target':"#"+id,
                'data-slide-to':i.toString(),
            });
            if(i==0){
                li.addClass('active');
                item = carouselItem(true, imgs[i],isForEntry);
            }else{
                item = carouselItem(false,imgs[i],isForEntry);
            }
            indicators.append(li);
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
    @param: isActive: boolean for checking active(first slide)
            img: a <img> element. all the data about the image, including title and description, are store as attr
            isEntryPage:if the carouselItem is not for Entrypage, we can don't need description
    @return: the item div
    */
    function carouselItem(isActive,img, isEntryPage){
        var item = $(document.createElement('div'));
        item.addClass('item');
      var thumb;
      if(isEntryPage) {
        thumb = $(img);
        item.attr('title',thumb.attr('title'));
    }
     else {   
        thumb = $(document.createElement("img"));
            thumb.attr("src", img.link);
            thumb.attr("title", img.title);
        }

        thumb.css({
            'width':'100%',
            'height':'auto',
            'display':'block',
        })

        item.append(thumb);
        var container = $(document.createElement('div'));
        container.addClass('container');
        item.append(container);
        if(isActive===true){
            item.addClass('active');
        }
        
        container.append(captionDiv);
        if(isEntryPage===true){
            var captionDiv = $(document.createElement('div'));
            captionDiv.addClass('carousel-caption');
            captionDiv.css({
                'z-index':10,
            });
            container.append(captionDiv);
            //we don't need caption for any carousel we are using so far
            // var caption = $(document.createElement('h1'));
            // caption.text(picCaption);
            // captionDiv.append(caption);
            var descri= $(document.createElement('p'));
            descri.text(thumb.attr("description"));
            captionDiv.append(descri);    
        }
        
        return item;
    }
    this.carouselItem = carouselItem;


    /*function for uploading photo button, called in addEntry and editEntry.
    @params: selector: file selector for this photo
            toDiv: the div we are going to append photo preview to
            ind: the index for this selector in all selectors
            title: title for this photo
            description: description for this photo
    @return filenames
    */
    function uploadPhotos(selector, toDiv, ind, title, description){
        //since we are not using multiple file uploader for now. the files.length will always be 1
        var files = selector.files;
        var filenames = [];
        var fileurl = [];
        var num= files.length;
        for (var i=0;i<num;i++){
            var file = files[i];
            var filename = file.name;
            filenames.push(filename);
            fileurl.push(URL.createObjectURL(file.slice()));
            toDiv.append(photoPreview(file, title, description, i+ind));
        }
        return filenames;
    }

    /**
    function to create a thumbnail div with caption and description
    @param: file: file to img.(for file picker preview)
            cap: the title for the photo
            desc: description for the photo
            i: = index of current selector and index of current photo in current selector. is used to remove photo
            (since we are using single file picker, it should just be the index of current selector for now)
            filetype: for editEntry, it would be an url. Used as a flag to make editBtn
    */
    function photoPreview(file,cap,desc,i,filetype){
        var colDiv = $(document.createElement('div'));
        var descript=desc;
        var spec={
            picfile: file,
            title: cap,
            description: desc,
            index: i,
            photoKey: file.photoKey, //will be null for addEntry
            entryKey: file.entryKey, //will be null for addEntry
        };

        
        colDiv.addClass('col-sm-6 col-md-4');
        var thumbDiv = $(document.createElement('div'));
        thumbDiv.addClass('thumbnail');
        thumbDiv.attr('id', 'thumbnail'+ spec.picfile.name); //give thumbnail a unique id based on index of file

        colDiv.append(thumbDiv);
        var imgDiv = $(document.createElement('div'));
        imgDiv.css({
            'width':'100%',
            'height':'166px',
            'position':"relative",
            'background-size':'contain',
            'background-repeat': 'no-repeat',
            'background-position':'center',
        })
        thumbDiv.append(imgDiv);
        var modal;
        // if(filetype !== "url") {
        //    modal = editBtn("addEntry",spec);
        // }
        // else {
        //     modal = editBtn("editEntry",spec);
        // }
        //get the thumbnail from the upload file. Sync the thumbnail in corresponding modal as well
        if(file && (filetype !=="url")){
            // console.log("filetype is: "+filetype);
            modal = editBtn("addEntry",spec);
            var reader = new FileReader();
            reader.onload = function (e) {
                var p = e.target.result; 
                $(document.getElementById(file.name+"modalthumb")).attr('src',p);
                imgDiv.css('background-image','url(' + p + ')');
            }
            reader.readAsDataURL(file);
        }
        if(filetype ==="url") {
            modal = editBtn("editEntry",spec);
            var p = file.url;
            $(document.getElementById(file.name+"modalthumb")).attr('src',p);
            imgDiv.css('background-image','url(' + p + ')');
            thumbDiv.addClass("uploaded");
        }

        var captionDiv = $(document.createElement('div'));
        captionDiv.addClass("caption");
        thumbDiv.append(captionDiv);
        var caption = $(document.createElement('label')); //title label w/class photoTitle 
        captionDiv.append(caption);
        captionDiv.css('width','100%');
        caption.attr('id', 'photoTitle' + file.name);
        caption.css({
            'width':'100%',
            'overflow':'hidden',
            'white-space': 'nowrap',
            'text-overflow': 'ellipsis',
        });
        caption.text(cap);
        var descDiv = $(document.createElement('div')); //description div w/id photoDescription holds description of photo
        descDiv.css({
            'height':'60px',
            'overflow':'hidden',
            // 'white-space': 'nowrap',
            'text-overflow': 'ellipsis',
        });
        captionDiv.append(descDiv);
        var btngroup = $(document.createElement('div'));
        btngroup.addClass('row');
        captionDiv.append(btngroup);
        

        var editbtn = $(document.createElement('button'));
        descDiv.text(descript);
        descDiv.attr('id', 'photoDescription'+file.name); 
        editbtn.text("Edit");
        editbtn.addClass("btn btn-default col-sm-offset-1");
        editbtn.click(function(e){
            //remove default behavior (submitting form):
            e.preventDefault();
            modal.modal({show:true});
            //open a modal to edit info about the photo

        });
        btngroup.append(editbtn);
        var deletebtn = $(document.createElement('button'));
        deletebtn.text("Delete");
        deletebtn.addClass("btn btn-default delete col-sm-offset-4");

        deletebtn.click(function(e) {
            e.preventDefault();
            colDiv.remove(); 
            modal.remove();   
            //click the x, index is i
            var removelist = $(document.getElementsByClassName("MultiFile-remove"));
            var remove = $(removelist[i]);
            remove.click();
            if(filetype ==="url") {
                // console.log("edit photo click");
                 $.post('deletePhoto?photoKey='+spec.photoKey+"&entryKey="+spec.entryKey, function() {
                 });
            }
            
        });
        btngroup.append(deletebtn);
        return colDiv;
    }
    /**
    this is the function for editBtn for both thumbnail div for tripview and addEntry.
    @param: type: indicates which kind of edit button this is for
    @param: spec: includes title, description,date,loc and image. 
    */
    function editBtn(type, spec) {
        // console.log("edit button spec is" + spec + "desc is " + spec.description + "title is" + spec.title);
        var title = spec.title,
        thumb = spec.img,
        description = spec.description,
        index = spec.index,
        loc = spec.location;
         // console.log("edit btn index is: " + index + " or maybe " + spec.index);

        var modal,modalId;
        var ctform=document.createElement('form');
        var contentForm = $(ctform);
        if(type==="Trip"){
            modalId = spec.title+spec.location;
            modal=makeModal(modalId, "Edit Trip", false);  
             //submission functionality:
             $(document.getElementById(modalId + "savebtn")).click(function(){
                contentForm.submit();
            });
         } 
         else if (type === "NewTrip"){
            modalId = spec.location + spec.index;
            modal=makeModal(modalId, "Add a Trip", false);  
             //submission functionality:
             $(document.getElementById(modalId + "savebtn")).click(function(){
                contentForm.submit();
            });
         }
         else {
            modalId="image"+spec.picfile.name;
            modal=makeModal(modalId, "Edit Photo", false);
        }

        var body = $(document.getElementById("body"));
        body.append(modal); 
        var modalBody = $(document.getElementById(modalId+"modalBody"));
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
        thumbnail.addClass("photoThumbnails");
        if(type=="editEntry") thumbnail.addClass("uploaded");
        if(type==="Trip"){
            thumbnail.attr('id',title+"modal");
        }
        else if(type!=="Trip"){
            if(type!=="NewTrip") {
            thumbnail.attr('id',spec.picfile.name+"modalthumb");   
            } 
        }
        thumbDiv.append(thumbnail);
        thumbnail.attr('src',thumb);
        var titleRow =$(document.createElement('div'));
        titleRow.addClass("row col-md-10 col-sm-offset-1");
        var titleInput = inputGroup("Title: ", 'title', null, title, null,true);
        titleRow.append(titleInput);    
        contentRow.append(titleRow);
        
        //Make sure call submit_input.click() when click submit 
        var submit_input = $(document.createElement('input')); //actually calls servlet, but invisible
        submit_input.attr('type', 'submit');
        submit_input.css({
                'display' : 'none'
        });



        contentForm.append(submit_input);
        
        if(type==="Trip" || type == "NewTrip"){
            contentForm.attr('action', '/editTrip?tripKey='+spec.tripkey);
            if(type === "NewTrip")  contentForm.attr('action', '/addTrip?userKey='+spec.userKey);
            contentForm.attr('method', 'post')
            var startDiv = $(document.createElement('div'));
            startDiv.addClass("row col-md-10 col-sm-offset-1");
            var start = Util.inputGroup('Start: ', 'departDate', "Choose a start date",null,1,true);
            startDiv.append(start);
            var endDiv = $(document.createElement('div'));
            endDiv.addClass("row col-md-10 col-sm-offset-1");
            var end = Util.inputGroup('End: ', 'retDate',"Choose an end date",null,1,true);
            endDiv.append(end);

            contentRow.append(startDiv);
            contentRow.append(endDiv);
            if (spec.depDate)
                start.data("DateTimePicker").setDate(spec.depDate);
            if(spec.retDate)
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
            var locInput = inputGroup("Location: ", 'location', null, loc,4,false);
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

        var text = $(document.createElement('textarea'));
        text.css({
            'height':'60px',
            'overflow-y':'auto',
            'width':'100%',
            'resize':'none',
        });

        if(description=="" && type!=="NewTrip"){
            text.val("Click Edit to add description for the photo")
        }else
            text.val(description);

        text.attr("name", "description");
        textWrapper.append(text);
        var titleInputField =   titleInput.children('input').eq(0);
        if(type ==="addEntry") {
            var submitEditPhoto = $(document.getElementById(modalId + "savebtn"));
            var closeEditPhoto = $(document.getElementById(modalId + "closebtn"));

            submitEditPhoto.click(function(e) {
                //e.preventDefault(); //no submission
                // console.log("clicked. index is: " + index + " or maybe " + spec.index);
                // console.log("title is:" + titleInputField.val() + "desc is " + text.val());
                spec.title=titleInputField.val();
                spec.description=text.val();
                $(document.getElementById("photoTitle"+spec.picfile.name)).text(spec.title);
                $(document.getElementById("photoDescription"+spec.picfile.name)).text(spec.description);
                closeEditPhoto.click();
            });
        }
        else{
            var submit = $(document.getElementById(modalId + "savebtn"));
            submit.click(function(e){
                submit_input.click();
                contentForm.submit();
            });
        }

        if (type === "editEntry") {
            contentForm.attr('action', '/editPhoto?photoKey='+spec.photoKey+"&entryKey="+spec.entryKey);
            contentForm.attr('method', 'post')

        }
        var closebtn = $(document.getElementById(modalId+'closebtn'));
        closebtn.click(function(){//make sure the input get reset when close
            if(type==="Trip" || type==="NewTrip"){
                $(ctform.querySelectorAll('[name=title]')).val(spec.title);
                $(ctform.querySelectorAll('[name=depDate]')).val(spec.depDate);
                $(ctform.querySelectorAll('[name=retDate]')).val(spec.retDate);
                $(ctform.querySelectorAll('[name=location]')).val(spec.location);
                $(ctform.querySelectorAll('[name=description]')).val(spec.description);
            }
            else{
                $(ctform.querySelectorAll('[name=title]')).val(spec.title);
                $(ctform.querySelectorAll('[name=description]')).val(spec.description);
            }

        });
        return modal;
    }

})(); /****ASDLKFJASDLKFJASDLKFJASDLKFJASDLKFJASDLKF***/