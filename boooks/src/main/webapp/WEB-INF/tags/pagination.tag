<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="url" required="true" type="java.lang.String" rtexprvalue="true"%>
<%@ attribute name="currentIndex" required="true" type="java.lang.Long" rtexprvalue="true"%>
<%@ attribute name="beginIndex" required="true" type="java.lang.Long" rtexprvalue="true"%>
<%@ attribute name="endIndex" required="true" type="java.lang.Long" rtexprvalue="true"%>
<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page" rtexprvalue="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="util" uri="http://boooks.fr/functions"%>


<c:url var="firstUrl" value="${url}" >
	<c:param name="p">1</c:param>
</c:url>
<c:url var="lastUrl" value="${url}" >
	<c:param name="p">${bookPage.totalPages}</c:param>
</c:url>
<c:url var="prevUrl" value="${url}"  >
	<c:param name="p">${currentIndex - 1}</c:param>
</c:url>
<c:url var="nextUrl" value="${url}" >
	<c:param name="p">${currentIndex + 1}</c:param>
</c:url>

<div class="pagination">
    <ul>
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><a >&lt;&lt;</a></li>
                <li class="disabled"><a >&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${firstUrl}">&lt;&lt;</a></li>
                <li><a href="${prevUrl}">&lt;</a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="${url}"  >
            	<c:param name="p">${i}</c:param>
            </c:url>
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex == page.totalPages}">
                <li class="disabled"><a >&gt;</a></li>
                <li class="disabled"><a >&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${nextUrl}">&gt;</a></li>
                <li><a href="${lastUrl}">&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
