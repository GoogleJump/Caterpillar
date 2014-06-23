signinPage=(function(){
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
		// 'width': '20%',
		'left':'960px',
		'position':'absolute'
	});	
	var logo = $(document.createElement('img'));
	logo.attr('src','../images/stamp.png');
	// logo.addClass('img-responsive');
	logoDiv.append(logo);
	logo.css({
		'display':'block',
		// 'margin':'auto',
		// 'right':'220px',
		'top':'0px',
		'z-index':'1000000',
		'position':'relative',
		// 'width':'100%',
		'height':'200%',
	});
	var whiteDiv = $(document.createElement("div"));
	$navbar.append(whiteDiv);
	whiteDiv.css({
		'height':'100%',
		// 'width': '20%',
		'left':'560px',
		'position':'absolute'
	});	
	var whitelogo = $(document.createElement('img'));
	whitelogo.attr('src','../images/whitelogo.png');
	whiteDiv.append(whitelogo);
	whitelogo.css({
		'top':'0px',
		// 'left':'100px',
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
	var username = $(document.createElement('input'));
	username.attr('name', 'email'); //name
	username.attr('type','email');
	username.attr('placeholder','Email address');
	username.attr('required');
	username.attr('autofocus');
	signinDiv.append(username);
	username.css({
		'bottom':'25px',
		'background-color':'#FFFCD4',
		'text-color':'black',
		'position':'absolute'
	});
	var password = $(document.createElement('input'));
	password.attr('name', 'password'); //name
	password.attr('placeholder','Password');
	password.attr('required');
	password.attr('type','password');
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
	var signupModal = Util.userSignup();
	signupbtn.click(function(){
		// console.log(signupModal);
		signupModal.modal({show:true});
	});

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
		'background-image':'url(../images/android.png)'
	});
	overlay.append(androidDiv);
})();

