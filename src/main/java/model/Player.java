package model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Модель игрока, хранящаяся в сессии.
 * 
 * Lombok аннотации:
 * @Data - генерирует геттеры, сеттеры, toString, equals и hashCode
 * @NoArgsConstructor - генерирует конструктор без аргументов
 * @AllArgsConstructor - генерирует конструктор со всеми аргументами
 * 
 * Реализует Serializable для корректной работы с HTTP сессией
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Serializable {
    /**
     * Имя игрока, вводится на стартовой странице
     */
    private String name;
    
    /**
     * Счетчик сыгранных игр, увеличивается после каждой завершенной игры
     */
    private int gamesPlayed = 0;
    
    /**
     * Увеличивает счетчик сыгранных игр на 1
     * @return обновленный объект Player
     */
    public Player incrementGames() {
        this.gamesPlayed++;
        return this;
    }
    
    /**
     * Вспомогательный метод для получения игрока из сессии
     * @param session HTTP сессия
     * @return объект Player из сессии или новый экземпляр
     */
    public static Player getFromSession(HttpSession session) {
        Player player = (Player) session.getAttribute("player");
        if (player == null) {
            player = new Player();
            session.setAttribute("player", player);
        }
        return player;
    }
}