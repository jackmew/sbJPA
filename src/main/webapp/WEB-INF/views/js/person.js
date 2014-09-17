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
	$("#btnFindOne").on("click",findOneClick);
	$("#btnFindByFirstName").on("click",findByFirstNameClick);
	$("#btnFindByLastName").on("click",findByLastNameClick);
	$("#btnFindByLastNameStartingWith").on("click",findByLastNameStartingWithClick);
	$("#btnFindByCreationTimeBefore").on("click",findByCreationTimeBeforeClick);
	$("#btnUpdate").on("click",updateClick);
	$("#btnDelete").on("click",deleteClick);
	$("#btnFindPersonPage").on("click",findPersonPageClick);
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

function findOneClick() {
	
	$("div[name='showPersonDiv']").remove();
	
	var personId = $("#findOneText").val();
	
	$.ajax({
		url: '/person/findOne',
		type: 'GET',
		data: {
			id:personId
		},
		success: function(dto){
			showPerson(dto);
		},
		error: function(xhr,status){
			alert("error");
		}
	});
}

function findByFirstNameClick() {
	
	$("div[name='showPersonDiv']").remove();
	
	var firstName = $("#firstNameText").val();
	
	$.ajax({
		url: '/person/findByFirstName',
		type: 'GET',
		data: {
			firstName:firstName
		},
		success: function(dtoArr){
			$.each(dtoArr,function(index,value){
				showPerson(value);
			});
			
		},
		error: function(xhr,status){
			alert("error");
		}
	});
}
function findByLastNameClick() {
	
	$("div[name='showPersonDiv']").remove();
	
	var lastName = $("#lastNameText").val();
	
	$.ajax({
		url: '/person/findByLastName',
		type: 'GET',
		data: {
			lastName:lastName
		},
		success: function(dtoArr){
			$.each(dtoArr,function(index,value){
				showPerson(value);
			});
			
		},
		error: function(xhr,status){
			alert("error");
		}
	});
}
function findByLastNameStartingWithClick(){
	
	$("div[name='showPersonDiv']").remove();
	
	var lastName = $("#lastNameStartingWithText").val();
	
	$.ajax({
		url: '/person/findByLastNameStartingWith',
		type: 'GET',
		data: {
			lastName:lastName
		},
		success: function(dtoArr){
			$.each(dtoArr,function(index,value){
				showPerson(value);
			});
			
		},
		error: function(xhr,status){
			alert("error");
		}
	});
}

function findByCreationTimeBeforeClick(){
	
	$("div[name='showPersonDiv']").remove();

	$.ajax({
		url: '/person/findByCreationTimeBefore',
		type: 'GET',
		success: function(dtoArr){
			$.each(dtoArr,function(index,value){
				showPerson(value);
			});
			
		},
		error: function(xhr,status){
			alert("error");
		}
	});
}

function updateClick(){
	
	$("div[name='showPersonDiv']").remove();
	
	
	var id = $("#updateIdText").val();
	var firstName = $("#updateFirstNameText").val();
	var lastName = $("#updateLastNameText").val();
		
	var personData = [];
	
	personData.push({
		id:id,
		firstName:firstName,
		lastName:lastName
	});
	
	var jsonPersonData = JSON.stringify(personData);
	
	$.ajax({
		url: '/person/update',
		contentType: "application/json; charset=UTF-8",
		type: 'POST',
		data: jsonPersonData,
		success: function(dto){
			showPerson(dto);
		},
		error: function(xhr,status){
			alert("error");
		}
	});
}

function deleteClick(){
	$("div[name='showPersonDiv']").remove();
	
	var id = $("#deleteIdText").val();
	
	$.ajax({
		url: '/person/delete',
		type: 'GET',
		data: {
			id:id
		},
		success: function(dto){
			alert(dto);
		},
		error: function(xhr,status){
			alert("error");
		}
	});
}

function findPersonPageClick(){
	$("div[name='showPersonDiv']").remove();
	
	var pageNumber = $("#pageNumberText").val();
	
	$.ajax({
		url: '/person/findPersonPage',
		type: 'GET',
		data: {
			pageNumber:pageNumber
		},
		success: function(dto){
			var personArr = dto.content;
			console.log(personArr);
			$.each(personArr , function(index,value){
				showPerson(value);
			});
		},
		error: function(xhr,status){
			alert("error");
		}
	});
}




















