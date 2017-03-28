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
			PUBHUB <small> View Book Tag - ${bookTags.isbn13} </small>
		</h1>
		<hr class="book-primary">
		
		<form action="UpdateBookTag" method="post" class="form-horizontal">
			<input type="hidden" class="form-control" id="isbn13" name="isbn13" required="required" value="${bookTags.isbn13 }"/>
			
			<div class="form-group">
				<label for="tagName" class="col-sm-3 control-label">Tag Name:</label>
				<div class="col-sm-5 col-sm-offset-1">
					<input type="text" class="form-control" id="tagName" name="tagName" placeholder="Tag Name" required="required" value="${bookTags.tagName}"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-1">
					<button type="submit" class="btn btn-info">Update</button>
				</div>
			</div>		
		</form>

	</div>
</header>

<jsp:include page="footer.jsp" />