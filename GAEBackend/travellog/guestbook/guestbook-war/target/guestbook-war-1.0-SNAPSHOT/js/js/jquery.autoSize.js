/*
*   Plug-in that resizes the height of a textarea 
*   Author: David Correa (dcorrea)
*   Brown University '15
*/

jQuery.fn.autoSize = function () {
    return this.each(function () {
        //Variables
        var defaultScrollHeight = this.scrollHeight;

        //Functions
        var increase = function () {
            increaseByRef(this);
        }

        var increaseByRef = function (objct) {
            var actualScrollHeight = objct.scrollHeight;

            if (actualScrollHeight !== defaultScrollHeight) {
                objct.style.height = actualScrollHeight + "px";
                defaultScrollHeight = actualScrollHeight;
            }
        }

        //Manipulations to the DOM element
        this.style.overflow = 'hidden';
        this.onkeydown = increase;
        this.onfocus = increase;
        this.onblur = increase;
        this.onchange = increase;
        
    });
};