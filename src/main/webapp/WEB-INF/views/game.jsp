<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Космическое приключение</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="container">
  <h1>Космическое приключение</h1>

  <!-- Блок с информацией об игроке -->
  <div class="player-info">
    <p>Игрок: <span class="highlight">${sessionScope.player.name}</span></p>
    <p>Сыграно игр: <span class="highlight">${sessionScope.player.gamesPlayed}</span></p>
  </div>

  <!-- Основное сообщение игры -->
  <div class="game-message">
    <p>${sessionScope.gameState.message}</p>
  </div>

  <!-- Блок с ошибками, если они есть -->
  <c:if test="${not empty error}">
    <div class="error-message">
      <p>${error}</p>
    </div>
  </c:if>

  <!-- Форма с выбором действий (отображается только если игра не завершена) -->
  <c:if test="${not sessionScope.gameState.gameOver}">
    <form method="post" class="choice-form">
      <c:choose>
        <%-- Шаг 1: Принять вызов --%>
        <c:when test="${sessionScope.gameState.currentStep eq 'acceptChallenge'}">
          <button type="submit" name="choice" value="1" class="choice-button">
            1. Принять вызов
          </button>
          <button type="submit" name="choice" value="2" class="choice-button">
            2. Отклонить вызов
          </button>
        </c:when>

        <%-- Шаг 2: Подняться на мостик --%>
        <c:when test="${sessionScope.gameState.currentStep eq 'goToBridge'}">
          <button type="submit" name="choice" value="1" class="choice-button">
            1. Подняться на мостик
          </button>
          <button type="submit" name="choice" value="2" class="choice-button">
            2. Отказаться подниматься
          </button>
        </c:when>

        <%-- Шаг 3: Представиться --%>
        <c:when test="${sessionScope.gameState.currentStep eq 'identifyYourself'}">
          <button type="submit" name="choice" value="1" class="choice-button">
            1. Рассказать правду о себе
          </button>
          <button type="submit" name="choice" value="2" class="choice-button">
            2. Солгать о себе
          </button>
        </c:when>
      </c:choose>
      <button type="submit" name="choice" value="restart" class="choice-button">
        Заново
      </button>
    </form>
  </c:if>
</div>
</body>
</html>