<%@ page import="mypack.*" %>
<%@ page import="java.util.Properties" %>
<%@ page errorPage="errorpage.jsp" %>

<jsp:useBean id="bookDB" scope="application" class="mypack.BookDB"/>

<%!
   public String convert(String s){
    try{
    	return new String(s.getBytes("GB2312"),"GB2312");
    }catch(Exception e){return null;}
   }
%>
