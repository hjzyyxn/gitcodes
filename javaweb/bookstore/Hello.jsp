<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/mytaglib" prefix="mm" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>A sample custom tag</title>
   </head>
   
   <body>
      <mm:Hello testA = "This is custom tag" testB = "testB" />
   </body>
</html>