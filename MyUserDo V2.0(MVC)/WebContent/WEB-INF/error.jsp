<%@ page language="java" pageEncoding="utf-8"%>
<html>
<head>
    <title>系统错误</title>
</head> 
<body>  
	<h1>系统错误</h1>	
	<h3>
		<%
		Object error=request.getAttribute("error");
		if(error!=null)out.print((String)error);
		%>	
   	</h3>

</body> 
</html>