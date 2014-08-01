signinPage=(function(){
	    //don't submit on enter:
     $(window).keydown(function(event){
    if( (event.keyCode == 13) && (validationFunction() == false) ) {
      event.preventDefault();
      return false;
    }
  });
	//var root;
	var overlay = $(document.getElementById("body"));//root.find("div");
	/*-----initial the navigation bar---------*/
	var navbar = document.createElement("div");
	navbar.setAttribute("role","navigation");
	var $navbar= $(navbar);
	$navbar.addClass("navbar navbar-default navbar-fixed-top");
	overlay.append(navbar);
	$(document.getElementsByClassName('navbar-default')).css({
		'background-color':'#5A9491'
	});
	$(document.getElementsByClassName('navbar-fixed-top')).css({
		'height':'100px'
	});

	var strip = $(document.createElement('div'));
	strip.css({
		'background-color':'#B1F2EF',
		'top':'100px',
		'position':'absolute',
		'width':'100%',
		'height':'15px',
		'left':'0px'

	});
	overlay.append(strip);

	//logo on the right
	var logoDiv = $(document.createElement("div"));
	$navbar.append(logoDiv);
	logoDiv.css({
		'height':'100%',
		'left':'960px',
		'position':'absolute'
	});	
	var logo = $(document.createElement('img'));
	logo.attr('src','../images/stamp.png');
	logoDiv.append(logo);
	logo.css({
		'display':'block',
		'top':'0px',
		'z-index':'1000000',
		'position':'relative',
		'height':'200%',
	});
	var whiteDiv = $(document.createElement("div"));
	$navbar.append(whiteDiv);
	whiteDiv.css({
		'height':'100%',
		'left':'560px',
		'position':'absolute'
	});	
	var whitelogo = $(document.createElement('img'));
	whitelogo.attr('src','../images/whitelogo.png');
	whiteDiv.append(whitelogo);
	whitelogo.css({
		'top':'0px',
		'position':'relative',
		'height':'100%'
	});

	// signin option:
	var signinDiv = $(document.createElement('form')); //changed from div to form
	$navbar.append(signinDiv);
	signinDiv.attr('method', 'get'); //added this for backend
	signinDiv.attr('action', '/signIn'); //add this for backend
	signinDiv.css({
		'height':'100%',
		'max-width':'600px',
		'left':'0%',
		'top':'0%',
		'position':'absolute',
		'padding-left':'5px',
	});
	var username=$(document.createElement('input'));
	// var $username = $(username);
	username.attr('name', 'email'); //name
	username.attr('type','email');
	username.attr('placeholder','Email address');
	username.attr('required',true);
	username.attr('autofocus');
	signinDiv.append(username);
	username.css({
		'bottom':'25px',
		'background-color':'#FFFCD4',
		'text-color':'black',
		'position':'absolute'
	});
	var password = $(document.createElement('input'));
	password.attr({
		'name': 'password',
		'placeholder':'Password',
		'required':true,
		'type':'password',
	}); 
	signinDiv.append(password);
	password.css({
		'bottom':'25px',
		'background-color':'#FFFCD4',
		'position':'absolute',
		'left':'180px'
	});
	var signinbtn = $(document.createElement('button'));
	signinbtn.addClass('btn btn-default btn-primary btn-block');
	signinbtn.attr('type','submit');
	signinbtn.text('Sign In');
	signinbtn.css({
		'left':'350px',
		'bottom':'20px',
		'background-color':'#B1F2EF',
		'position':'absolute',
		'width':'65px',
	});
	
	// signup option:
	var signupDiv = $(document.createElement('div'));
	$navbar.append(signupDiv);
	signupDiv.css({
		'height':'100%',
		'max-width':'600px',
		'left':'0%',
		'top':'0%',
		'position':'absolute',
		'padding-left':'5px',
	});

	var signupbtn = $(document.createElement('button'));
	signupbtn.addClass('btn btn-default btn-primary btn-block');
	signupbtn.text('Sign Up');
	signupbtn.css({
		'left':'430px',
		'background-color':'#B1F2EF',
		'bottom':'20px',
		'position':'absolute',
		'width':'65px',
	});
	
	signinDiv.append(signinbtn);
	signupDiv.append(signupbtn); 
	var signupModal = userSignup();
	signupbtn.click(function(){
		signupModal.modal({show:true});
	});
	/*
	function that makes the sign up modal.
	returns the modal div
	*/
	function userSignup(){
        var id = "signup";
        var modal = Util.makeModal(id,'Sign Up',false);
        overlay.append(modal);
        var modalBody = $(document.getElementById(id+"modalBody"));
        var contentRow = $(document.createElement('div'));
        contentRow.addClass('row');
        contentRow.css({
            'width':'80%',
            'left':'10%',
            'position':'relative'
        });
        modalBody.append(contentRow);
        var registration = $(document.createElement('form'));
        registration.addClass('form-horizontal');
        contentRow.append(registration);
        registration.attr('method','post');
        registration.attr('action', '/insertUser');
        registration.attr('id','registration');
        var username = Util.inputGroup('Username','username', 'Pick a Username',null,null,true);
        registration.append(username);

        var pwd = Util.inputGroup('Password','password', "Please enter your password", null, 3,false);
        registration.append(pwd);

        var emailinput = Util.inputGroup('Email', 'email', 'Please enter your email address', null, 2,false);
        registration.append(emailinput);

        var firstname = Util.inputGroup('First Name', 'firstname', null, null, null,true);
        registration.append(firstname);

        var lastname = Util.inputGroup('Last Name', 'lastname', null, null, null,true);
        registration.append(lastname);

        var submit_input = $(document.createElement('input')); //actually calls servlet, but invisible
        submit_input.attr('type', 'submit');
        submit_input.css({
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

        var closebtn = $(document.getElementById(id+'closebtn'));
        closebtn.click(function(){//clean up the modal inputs when closed
        	registration.find('input:text, input:password, input:file, select, textarea').val('');
        });
    	return modal;
    }
    // this.userSignup=userSignup;

	var description= $(document.createElement('div'));
	description.addClass('container');
	description.css({
		'position':'absolute',
		'top':'150px',
		'left':'10%',
		'height':'60%',
		'width':'30%',
		'background-color':'#FFFCD4',
		'border':'solid 2px gray',
		'border-radius':'5px'

	});
	overlay.append(description);

	var androidDiv = $(document.createElement('div'));
	androidDiv.addClass('container');
	androidDiv.css({
		'position':'absolute',
		'top':'150px',
		'left':'50%',
		'height':'60%',
		'width':'40%',
		'background-image':'url(../images/travellogimage.jpg)',
		'background-size': 'cover'
	});
	overlay.append(androidDiv);

	var signin = Util.getQueryVariable("signin"); //will be false if unsuccessful
	var username = Util.getQueryVariable("username"); //will be taken if unsuccessful
	var email = Util.getQueryVariable("email"); //will be taken if unsuccessful

	if(signin != null) {
		alert("email or password incorrect");
	}
	if(username != null) {
		alert("Sorry, that username is taken");
	}
	if(email != null) {
		alert("Someone has already registered with that email.");
	}


})();

