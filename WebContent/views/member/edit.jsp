<%@ page pageEncoding="UTF-8"%>
<%@ include file = "../layouts/include.jsp" %>
<%
	Member member = (Member)request.getAttribute("member");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="update" method="post">
	<input type="hidden" name="id" value="<%=member.id%>"/>
	<div class="form-group">
        <label>姓名</label>
        <input class="form-control" id="name" name="name" placeholder="请输入姓名" size="30" type="text"  value="<%=member.name%>"/>
      </div>
      <div class="form-group">
        <label>年龄</label>
        <input class="form-control" id="age" name="age" placeholder="请输入年龄" size="30" type="text" value="<%=member.age%>"/>
      </div>
      <input type="submit" value="保存" />
</form>
</body>
</html>