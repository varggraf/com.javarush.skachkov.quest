<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Космическое приключение - Начало</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="container">
    <h1>Космическое приключение</h1>

    <!-- Блок с предысторией игры -->
    <div class="story">
        <p>Вы просыпаетесь в незнакомой капсуле. Последнее, что вы помните - яркую вспышку света.</p>
        <p>Голос компьютера сообщает: "Системы восстановлены. Вы находитесь на борту исследовательского корабля Zeta-7."</p>
        <p>"Ваши действия в ближайшие минуты определят исход миссии."</p>
    </div>

    <!-- Блок с ошибками, если они есть -->
    <c:if test="${not empty error}">
        <div class="error-message">
            <p>${error}</p>
        </div>
    </c:if>


    <!-- Форма ввода имени игрока -->
    <form method="post" class="player-form">
        <label for="playerName">Введите ваше имя:</label>
        <input type="text" id="playerName" name="playerName" required
               placeholder="Имя игрока" class="form-input">

        <button type="submit" class="start-button">Начать игру</button>
    </form>
</div>
</body>
</html>