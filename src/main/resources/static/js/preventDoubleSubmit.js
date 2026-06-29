document.querySelector("form").onSubmit = function(){
    this.querySelector("button[type=submit]").disabled = true;
}