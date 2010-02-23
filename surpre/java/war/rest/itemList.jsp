<%@page contentType="text/xml;charset=UTF-8" isELIgnored="false"%><?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<resultList>
<c:forEach var="e" items="${itemList}">
  <item name="${e.name}" url="${e.link}" image="${e.imageUrl}" wishlist="${e.addWishListLink}" detail="${e.detail}">
  <text>
  <![CDATA[
  ${e.text}
  ]]>
  </text>
  </item>
</c:forEach>
</resultList>