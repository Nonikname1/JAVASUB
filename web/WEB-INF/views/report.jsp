<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчёт</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/groups">← Все группы</a>
<h2>Отчёт по группе</h2>

<c:choose>
    <c:when test="${empty selectedGroup}">
        <p>Выберите группу:</p>
        <ul>
            <c:forEach var="g" items="${groups}">
                <li><a href="${pageContext.request.contextPath}/report?groupId=${g.id}">${g.name}</a></li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <h3>${selectedGroup.name}</h3>

        <c:choose>
            <c:when test="${empty students}">
                <p>В группе нет студентов.</p>
            </c:when>
            <c:otherwise>
                <table border="1" cellpadding="6">
                    <tr>
                        <th>Студент</th>
                        <th>Задача 1</th>
                        <th>Задача 2</th>
                        <th>Задача 3</th>
                        <th>Итог</th>
                    </tr>
                    <c:forEach var="s" items="${students}">
                        <tr>
                            <td>${s.name}</td>
                            <td>${s.tasks[0] ? '✓' : '✗'}</td>
                            <td>${s.tasks[1] ? '✓' : '✗'}</td>
                            <td>${s.tasks[2] ? '✓' : '✗'}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${s.tasks[0] and s.tasks[1] and s.tasks[2]}">
                                        Все сдано
                                    </c:when>
                                    <c:otherwise>
                                        Не сдано:
                                        <c:if test="${not s.tasks[0]}"> Задача 1</c:if>
                                        <c:if test="${not s.tasks[1]}"> Задача 2</c:if>
                                        <c:if test="${not s.tasks[2]}"> Задача 3</c:if>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

</body>
</html>
