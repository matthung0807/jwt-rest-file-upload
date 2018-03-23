<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
  
<title></title>
<style>
.progress {
  position:relative; 
  width:400px; 
  border: 1px solid #ddd; 
  padding: 1px; 
  border-radius: 3px; 
}
.bar { 
  background-color: #B4F5B4; 
  width:0%; 
  height:20px; 
  border-radius: 3px; 
}
.percent { 
  position:absolute; 
  display:inline-block; 
  top:3px; 
  left:48%; 
}
</style>
</head>
<body>
<h1>Login</h1>
<div>
<span>選擇使用者</span>:<button id="matt">可上傳檔案</button><button id="john">無上傳權限</button>
</div>
<br>
<div>
  <form name='loginForm' id="loginForm" action="doLogin" method='POST'>
    <table>
      <tr>
        <td>User:</td>
        <td><input type='text' name='username' value=''><span id="result"></span></td>
      </tr>
      <tr>
        <td>Password:</td>
        <td><input type='password' name='password' /></td>
      </tr>
      
    </table>
  </form>
</div>
<div>
  <form id="myForm">
    <table>
      <tr>
        <td><input type="file" name="file" id="file"/></td>
      </tr>
      <tr>
        <td><input name="submit" type="submit" value="submit" id="submit" /></td>
      </tr>
    </table>
  </form>
</div>
<div class='progress' id="progress_div">
  <div class='bar' id='bar'></div>
  <div class='percent' id='percent'>0%</div>
</div>
<div></div>
</body>
<script>
$(function(){
  $("#matt").click(function(){
	  $("input[name='username']").val("matt");
	  $("input[name='password']").val("matt");
  });
  $("#john").click(function(){
	  $("input[name='username']").val("john");
	  $("input[name='password']").val("john");
  });
	
	
	
  $("#myForm").submit(function(e){
	  e.preventDefault();
	  document.cookie = 'jwt=;expires=Thu, 01 Jan 1970 00:00:01 GMT;'; // delete jwt cookie
	  
	  $("#result").empty();
	  $("#percent").text("0%");
      $("#bar").width("0%");
	  
	  if($("#file").val() === ""){
		alert('請選擇上傳檔案');
		return;
	  }
	  $.ajax({
		  type: "POST",
		  url: "doLogin",
		  data:$("#loginForm").serialize(),
		  dataType: 'text',
		  success: function (data){
			  
		    var formData = new FormData($("#myForm")[0]);
			$.ajax({
			  type: "POST",
			  url: "api/test/upload",
			  data:formData,
			  cache:false,
			  processData: false,
			  contentType: false,
			  dataType: 'text',
			  beforeSend: function (xhr) {
				  xhr.setRequestHeader("Authorization", "Bearer " + getCookie("jwt"));
			    },
			  success: function (data){
			    $("#result").text(data);
			  },
			  error: function(jqXHR, textStatus, errorThrown){
		        switch(jqXHR.status){
		          case 401:{
		        	$("#result").text("帳號或密碼錯誤");
		            $("#percent").text("0%");
		            $("#bar").width("0%");
		            break;
		          }
		          case 403:{
      		        $("#result").text("無此權限");
      		        $("#percent").text("0%");
		            $("#bar").width("0%");
      		        break;
		          }
		      	  
		        }
		      },
		      xhr:function(){
		    	var xhr = new window.XMLHttpRequest();
		    	//Upload progress
		        xhr.upload.addEventListener("progress", function(evt){
		          if (evt.lengthComputable) {
		            var percentComplete = evt.loaded / evt.total;
		            var percentVal = percentComplete*100 + "%";
		            $("#percent").text(percentVal);
		            $("#bar").width(percentVal);
		          }
		        }, false);
		    	return xhr;
		      }
			}).fail(function(){
				$("#result").text("無此權限");
				$("#percent").text("0%");
				$("#bar").width("0%");
			});
		    
		  },
		  error: function(jqXHR, textStatus, errorThrown){
	        switch(jqXHR.status){
	          case 401:$("#result").text("帳號或密碼錯誤");break;
	          case 403:$("#result").text("無此權限");break;
	      	  
	        }
	          
	      },
	      
		  
		  
		  
		});
	  
  });
	
});

function getCookie(cookieName) {
  var name = cookieName + "=";
  var ca = document.cookie.split(';');
  for(var i=0; i<ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0)==' ') c = c.substring(1);
    if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
  }
  return "";
}



</script>
</html>