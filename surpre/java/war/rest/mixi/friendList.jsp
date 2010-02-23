<%@page contentType="text/xml;charset=UTF-8" isELIgnored="false"%><?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<resultList status="${status}">
<c:forEach var="e" items="${friendList}">
  <friend userId="${e.id}" name="${e.name}" nickname="${e.nickName}" url="${e.thumbnailUrl}" />
</c:forEach>
</resultList>
