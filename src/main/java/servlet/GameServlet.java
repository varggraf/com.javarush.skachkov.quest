package servlet;

import model.GameState;
import model.Player;
import service.GameService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Основной сервлет игры, обрабатывающий игровую логику.
 * <p>
 * Функционал:
 * - Управление состоянием игры через сессию
 * - Обработка выбора игрока
 * - Определение перехода между состояниями
 * - Обработка завершения игры
 * <p>
 * Использует GameService для обработки игровой логики
 */
@WebServlet(name = "GameServlet", value = "/game")
public class GameServlet extends HttpServlet {

    // Экземпляр сервиса для обработки игровой логики
    private final GameService gameService = new GameService();

    /**
     * Обработка GET запроса - отображение текущего состояния игры
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Устанавливаем кодировку
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        // Получаем или создаем состояние игры в сессии
        HttpSession session = req.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            gameState = GameState.initial();
            session.setAttribute("gameState", gameState);
        }

        // Обрабатываем начальное состояние (без выбора игрока)
        GameState newState = gameService.processChoice(gameState, null);
        session.setAttribute("gameState", newState);

        // Перенаправляем на JSP страницу
        req.getRequestDispatcher("/WEB-INF/views/game.jsp").forward(req, resp);
    }

    /**
     * Обработка POST запроса - обработка выбора игрока
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Устанавливаем кодировку
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        // Получаем сессию и объекты игры
        HttpSession session = req.getSession();
        GameState currentState = (GameState) session.getAttribute("gameState");
        Player player = Player.getFromSession(session);

        // Получаем выбор игрока
        String choice = req.getParameter("choice");

        // Обработка перезапуска игры
        if ("restart".equals(choice)) {
            session.removeAttribute("gameState");
            //session.setAttribute("player", player.incrementGames());
            resp.sendRedirect(req.getContextPath() + "/game");
            return;
        }

        try {
            // Обрабатываем выбор игрока
            GameState newState = gameService.processChoice(currentState, choice);
            session.setAttribute("gameState", newState);

            // Если игра завершена - увеличиваем счетчик игр
            if (newState.isGameOver()) {
                session.setAttribute("player", player.incrementGames());
                req.getRequestDispatcher("/WEB-INF/views/result.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/views/game.jsp").forward(req, resp);
            }
        } catch (IllegalArgumentException e) {
            // Обработка неверного выбора
            req.setAttribute("error", "Неверный выбор, попробуйте еще раз");
            req.getRequestDispatcher("/WEB-INF/views/game.jsp").forward(req, resp);
        }
    }
}