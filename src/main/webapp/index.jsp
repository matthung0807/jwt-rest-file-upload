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
</head>
<body>
<h1>Index</h1>
<div>
  <button type="button" id="button">hello</button>
</div>
<div id="result"></div>
<hr>
<div>
  <form id="myForm">
    <input type="file" name="file" id="file"/>
    <button type="submit">上傳檔案</button>
  </form>
</div>
</body>
<script>
$(function(){
  $("#button").click(function(){
	console.log()
	  
	$.ajax({
	  type: "GET",
	  url: "api/test/string",
	  dataType: 'text',
	  beforeSend: function (xhr) {
		  xhr.setRequestHeader("Authorization", "Bearer " + getCookie("jwt"));
	    },
	  success: function (data){
	    console.log(data);
	    $("#result").text(data);
	  },
	  error: function(jqXHR, textStatus, errorThrown){
        switch(jqXHR.status){
          case 401:console.log(401);
          case 403:console.log(403);
      	  
        }
          
      }      
	});
  });

  $("#myForm").submit(function(e){
    e.preventDefault();
    console.log("upload");
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
	    console.log(data);
	    $("#result").text(data);
	  },
	  error: function(jqXHR, textStatus, errorThrown){
        switch(jqXHR.status){
          case 401:console.log(401);
          case 403:console.log(403);
      	  
        }
          
      }      
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