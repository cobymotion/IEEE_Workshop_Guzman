<%-- 
    Document   : testing
    Created on : Sep 8, 2016, 1:54:45 AM
    Author     : coby
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Datos!</h1>
        
        <c:forEach items="${lugares}" var="lugar">
            ${lugar.id} <br/>
            ${lugar.nombre} <br/>
            ${lugar.latitud} <br/>
            ${lugar.longitud} <br/>
        </c:forEach>
    </body>
</html>
