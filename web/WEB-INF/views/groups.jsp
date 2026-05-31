<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Учет задач — Группы</title>
</head>
<body>
<h1>Учет задач по Информатике</h1>

<h2>Группы</h2>

<form method="post" action="${pageContext.request.contextPath}/groups">
    <input type="hidden" name="action" value="create">
    <input type="text" name="name" placeholder="Название группы" required>
    <button type="submit">Добавить группу</button>
</form>

<br>

<c:choose>
    <c:when test="${empty groups}">
        <p>Групп ещё нет.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="6">
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Студенты</th>
                <th>Отчёт</th>
                <th>Удалить</th>
            </tr>
            <c:forEach var="g" items="${groups}">
                <tr>
                    <td>${g.id}</td>
                    <td>${g.name}</td>
                    <td><a href="${pageContext.request.contextPath}/students?groupId=${g.id}">Открыть</a></td>
                    <td><a href="${pageContext.request.contextPath}/report?groupId=${g.id}">Отчёт</a></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/groups">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${g.id}">
                            <button type="submit">Удалить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
