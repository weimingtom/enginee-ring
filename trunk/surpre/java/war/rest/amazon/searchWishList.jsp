<%@page contentType="text/xml;charset=UTF-8" isELIgnored="false"%><?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<resultList>
<c:forEach var="e" items="${wishIdList}">
  <wishList id="${e.id}" name="${e.name}" />
</c:forEach>
</resultList>
