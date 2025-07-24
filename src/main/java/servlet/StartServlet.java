package servlet;

import model.Player;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;



@WebServlet(name = "StartServlet", value = "/start")
public class StartServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        // Устанавливаем кодировку ответа
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Перенаправляем на JSP страницу
        request.getRequestDispatcher("/WEB-INF/views/start.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Устанавливаем кодировку
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        // Получаем сессию и игрока
        HttpSession session = req.getSession();
        Player player = Player.getFromSession(session);

        // Получаем имя из параметров запроса
        String playerName = req.getParameter("playerName");

        // Проверяем и сохраняем имя
        if (playerName != null && !playerName.isBlank()) {
            player.setName(playerName.trim());
        } else {
            // Если имя не введено, добавляем сообщение об ошибке
            req.setAttribute("error", "Пожалуйста, введите ваше имя");
            doGet(req, resp);
            return;
        }

        // Перенаправляем на страницу игры
        resp.sendRedirect(req.getContextPath() + "/game");
    }

    public void destroy() {
    }
}