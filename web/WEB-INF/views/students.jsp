<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Студенты</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/groups">← Все группы</a>

<c:choose>
    <c:when test="${empty selectedGroup}">
        <h2>Выберите группу:</h2>
        <ul>
            <c:forEach var="g" items="${groups}">
                <li><a href="${pageContext.request.contextPath}/students?groupId=${g.id}">${g.name}</a></li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <h2>Группа: ${selectedGroup.name}</h2>

        <form method="post" action="${pageContext.request.contextPath}/students">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="groupId" value="${selectedGroup.id}">
            <input type="text" name="name" placeholder="Имя студента" required>
            <button type="submit">Добавить студента</button>
        </form>

        <br>

        <c:choose>
            <c:when test="${empty students}">
                <p>В группе нет студентов.</p>
            </c:when>
            <c:otherwise>
                <table border="1" cellpadding="6">
                    <tr>
                        <th>ID</th>
                        <th>Имя</th>
                        <th>Задача 1</th>
                        <th>Задача 2</th>
                        <th>Задача 3</th>
                        <th>Удалить</th>
                    </tr>
                    <c:forEach var="s" items="${students}">
                        <tr>
                            <td>${s.id}</td>
                            <td>${s.name}</td>

                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/students">
                                    <input type="hidden" name="action" value="updateTask">
                                    <input type="hidden" name="groupId" value="${selectedGroup.id}">
                                    <input type="hidden" name="studentId" value="${s.id}">
                                    <input type="hidden" name="taskIndex" value="0">
                                    <input type="hidden" name="status" value="${s.tasks[0] ? 'false' : 'true'}">
                                    <button type="submit">${s.tasks[0] ? '✓ Сдал' : '✗ Не сдал'}</button>
                                </form>
                            </td>

                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/students">
                                    <input type="hidden" name="action" value="updateTask">
                                    <input type="hidden" name="groupId" value="${selectedGroup.id}">
                                    <input type="hidden" name="studentId" value="${s.id}">
                                    <input type="hidden" name="taskIndex" value="1">
                                    <input type="hidden" name="status" value="${s.tasks[1] ? 'false' : 'true'}">
                                    <button type="submit">${s.tasks[1] ? '✓ Сдал' : '✗ Не сдал'}</button>
                                </form>
                            </td>

                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/students">
                                    <input type="hidden" name="action" value="updateTask">
                                    <input type="hidden" name="groupId" value="${selectedGroup.id}">
                                    <input type="hidden" name="studentId" value="${s.id}">
                                    <input type="hidden" name="taskIndex" value="2">
                                    <input type="hidden" name="status" value="${s.tasks[2] ? 'false' : 'true'}">
                                    <button type="submit">${s.tasks[2] ? '✓ Сдал' : '✗ Не сдал'}</button>
                                </form>
                            </td>

                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/students">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="groupId" value="${selectedGroup.id}">
                                    <input type="hidden" name="id" value="${s.id}">
                                    <button type="submit">Удалить</button>
                                </form>
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
