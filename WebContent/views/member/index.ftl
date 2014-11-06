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
	<#list members as member>
	<tr>
		<td>${member.name}</td>
		<td>${member.age}</td>
		<td>
			<a href="edit?id=${member.id}">编辑</a>
			<a href="destroy?id=${member.id}">删除</a>
		</td>
	</tr>
	</#list>
</table>

<br />

<a href="add">New Member</a>
</body>
</html>