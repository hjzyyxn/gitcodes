<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Product Form</title>
<style type="text/css">@import url(css/main.css);</style>
</head>
<body>

<div id="global">
<form action="save-product" method="post">
	<fieldset>
		<legend>Add a product</legend>
		<label for="name">Product Name: </label>
		<input type="text" id="name" name="name" value=""
			tabindex="1">
		<label for="description">Description: </label>
		<input type="text" id="description" name="description" value=""
			tabindex="2">
		<label for="price">Price: </label>
		<input type="text" id="price" name="price" tabindex="3">
		<div id="buttons">
			<label for="dummy"> </label>
			<input id="reset" type="reset" tabindex="4">
			<input id="submit" type="submit" tabindex="5"
				value="Add Product">
		</div>
	</fieldset>
	</form>
	</div>

</body>
</html>