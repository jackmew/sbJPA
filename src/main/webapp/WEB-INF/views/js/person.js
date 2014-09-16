$(document).ready(function(){
	
	var file ;
	var fileName ;
	var type ;
	var size ;	
	$('input[type=file]').change(function () {
	     console.dir(this.files[0]);     
	     file = this.files[0];
	     name = this.files[0].name;
	     type = this.files[0].type;
	     size = this.files[0].size;
	});
	
	$('#btnSubmit').click(function() {
		var firstName = $("input[name='firstName']").val();
		var lastName = $("input[name='lastName']").val();
		
		if(file === null || file === undefined){
			alert("null");
			return false;
		}
		var formData = new FormData();
		formData.append("file",file);
		formData.append("firstName",firstName);
		formData.append("lastName",lastName);
		submitClick(formData);
	});
	
	$("#btnFindAll").on("click",findAllClick);
});

function submitClick(formData){
	$.ajax({
        url: '/person/personUpload',  //Server script to process data
        type: 'POST',
        success: function(result){
        	alert(result);
        },
        error: function(result,jqXHR){
        	alert("e");
        },
        // Form data
        data: formData,
        //Options to tell jQuery not to process data or worry about content-type.
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'text'
    });
}
function findAllClick(){
	
	$("div[name='showPersonDiv']").remove();
	
	$.ajax({
		url: '/person/findAllPerson',
		type: "GET",
		success: function(data){
			$.each(data , function(index,value){
				var personWithImage = value ;
				//console.log(personWithImage);
				showPerson(personWithImage);
			});
		},
		error : function(xhr ,status){
			alert("error");
		}
	});
	
}
function showPerson(personWithImage){
	var firstName = personWithImage.firstName;
	var lastName = personWithImage.lastName;
	var fullName = firstName+' '+lastName; 
	var image = personWithImage.image;
	var imageSrc = 'data:'+image.contentType+';base64,'+image.bytes ;
	
	$("#showPerson").append('<div name="showPersonDiv" class="col-md-3">'+
							  	'<img class="CattoBorderRadius" src='+imageSrc+' />'+
							  	'<h2>'+fullName+'</h2>'+
							'</div>');
}