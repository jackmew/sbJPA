$(document).ready(function(){

	$('input[type=file]').change(function () {
		 console.dir(this.files[0]);
		 $('input[name="name"]').val(this.files[0].name);
		 $('input[name="contentType"]').val(this.files[0].type);
	})
});