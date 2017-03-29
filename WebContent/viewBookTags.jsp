<!--  Header   -->
<jsp:include page="header.jsp" />

<!--  JSTL includes  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<header>
	<div class="container">
		<c:choose>
			<c:when test="${not empty message }">
				<p class="alert ${messageClass}">${message}</p>
				<%
					session.setAttribute("message", null);
					session.setAttribute("messageClass", null);
				%>
			</c:when>
		</c:choose>

		<h1>
			PUBHUB <small> View Book Tags </small>
		</h1>
		<hr class="book-primary">
		
		<table class="table table-striped table-hover table-responsive pubhub-datatable">
			<thead>
				<tr>
					<td>Title:</td>
					<td>Author:</td>
					<td>Tag:</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tag" items="${ViewBookTags}">
					<tr>
						<td><c:out value="${tag.title}" /></td>
						<td><c:out value="${tag.author}" /></td>
						<td><c:out value="${tag.tagName}" /></td>
						<td><form action="EditBookTag?title=${tag.title}" method="get">
							<input type="hidden" name="isbn13" value="${tag.isbn13}"/>
							<input type="hidden" name="title" value="${tag.title}">
							<button class="btn btn-primary">Edit</button>
						</form></td>
					</tr>
				</c:forEach>
			</tbody>		
		</table>
		<div class="col-sm-1">
			<div class="btn btn-danger">
				<a href="./BookPublishing">Cancel</a>
			</div>
		</div>

	</div>
</header>

<jsp:include page="footer.jsp" />