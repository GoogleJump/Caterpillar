Util = (function(){
	"use strict";
	return {
        makeNavButton: makeNavButton,
    };
	/**
     * Used by web app code to slide in pages given their html files
     * @param path     the path to the html file within the html directory
     */
    function makeNavButton(title) {
        var button = $(document.createElement('div'));
        

        // var ret;
        // $.ajax({
        //     async: false,
        //     cache: false,
        //     url: "html/"+path,
        //     success: function (data) {
        //         ret = $(data);
        //     },
        //     error: function (err) {
        //         console.log("url = " + path);
        //         console.log("error: "+err.statusText);
        //         ret = null;
        //     },
        //     dataType: 'html'
        // });
        return button;
    }

})();