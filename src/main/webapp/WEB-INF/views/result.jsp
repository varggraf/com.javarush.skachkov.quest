<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Результат игры</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="container">
  <!-- Заголовок с результатом (победа/поражение) -->
  <h1 class="result-title">
    <c:choose>
      <c:when test="${sessionScope.gameState.victory}">
        <span class="victory">ПОБЕДА!</span>
      </c:when>
      <c:otherwise>
        <span class="defeat">ПОРАЖЕНИЕ</span>
      </c:otherwise>
    </c:choose>
  </h1>

  <!-- Сообщение с итогом игры -->
  <div class="result-message">
    <p>${sessionScope.gameState.message}</p>
  </div>

<%--  <!-- Статистика игрока -->--%>
<%--  <div class="player-stats">--%>
<%--    <p>Игрок: <span class="highlight">${sessionScope.player.name}</span></p>--%>
<%--    <p>Всего сыграно игр: <span class="highlight">${sessionScope.player.gamesPlayed}</span></p>--%>
<%--  </div>--%>

  <!-- Кнопка для новой игры -->
  <form method="post" class="restart-form">
    <button type="submit" name="choice" value="restart" class="restart-button">
      Играть снова
    </button>
  </form>
</div>
</body>
</html>