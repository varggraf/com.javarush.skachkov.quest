package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель состояния игры.
 * 
 * Lombok аннотации:
 * @Data - генерирует геттеры, сеттеры, toString, equals и hashCode
 * @Builder - реализует паттерн Builder для удобного создания объектов
 * @NoArgsConstructor - конструктор без аргументов
 * @AllArgsConstructor - конструктор со всеми аргументами
 * 
 * Хранит текущее состояние игры, включая:
 * - текущий шаг
 * - сообщение для пользователя
 * - флаги завершения игры и победы
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameState {
    /**
     * Текущий шаг игры, определяет доступные варианты выбора
     * Возможные значения:
     * - "start" - начальное состояние
     * - "acceptChallenge" - выбор принятия вызова
     * - "goToBridge" - выбор поднятия на мостик
     * - "identifyYourself" - выбор идентификации
     * - "end" - завершение игры
     */
    private String currentStep;
    
    /**
     * Текст сообщения, отображаемый пользователю
     */
    private String message;
    
    /**
     * Флаг завершения игры (true - игра окончена)
     */
    private boolean gameOver;
    
    /**
     * Флаг победы (true - игрок победил)
     */
    private boolean victory;
    
    /**
     * Создает начальное состояние игры
     * @return новый объект GameState с начальными значениями
     */
    public static GameState initial() {
        return GameState.builder()
                .currentStep("start")
                .message("Ты потерял память. Принять вызов НЛО?")
                .gameOver(false)
                .victory(false)
                .build();
    }
    
    /**
     * Создает новое состояние на основе текущего с обновленными полями
     */
    public GameState withNewState(String step, String msg, boolean over, boolean win) {
        return GameState.builder()
                .currentStep(step)
                .message(msg)
                .gameOver(over)
                .victory(win)
                .build();
    }
}