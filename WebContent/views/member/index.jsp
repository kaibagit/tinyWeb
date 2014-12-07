<%@ page pageEncoding="UTF-8"%>
<%@ include file = "../layouts/include.jsp" %>
<%
	List<Member> members = (List<Member>)request.getAttribute("members");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
	<tr>
		<th>姓名</th>
		<th>年龄</th>
		<th>操作</th>
	</tr>
	<%
		for(Member m:members){
	%>
			<tr>
				<td><%=m.name %></td>
				<td><%=m.age %></td>
				<td>
					<a href="edit?id=<%=m.id%>">编辑</a>
					<a href="destroy?id=<%=m.id%>">删除</a>
				</td>
			</tr>
	<%
		}
	%>
</table>

<br />

<a href="add">New Member</a>
</body>
</html>