package service;


import model.GameState;

/**
 * Сервис, содержащий основную логику игры.
 * Обрабатывает выбор игрока и возвращает новое состояние игры.
 * 
 * Реализует всю игровую логику, включая:
 * - обработку выбора игрока
 * - переходы между состояниями
 * - определение победы/поражения
 */
public class GameService {
    
    /**
     * Обрабатывает выбор игрока и возвращает новое состояние игры
     * @param currentState текущее состояние игры
     * @param choice выбор игрока (может быть null для начального состояния)
     * @return новое состояние игры после обработки выбора
     * @throws IllegalArgumentException если передан недопустимый выбор
     */
    public GameState processChoice(GameState currentState, String choice) {
        // Проверяем текущий шаг игры и обрабатываем выбор соответствующим образом
        return switch (currentState.getCurrentStep()) {
            case "start" -> handleStartStep();
            case "acceptChallenge" -> handleAcceptChallenge(choice);
            case "goToBridge" -> handleGoToBridge(choice);
            case "identifyYourself" -> handleIdentifyYourself(choice);
            default -> throw new IllegalStateException("Неизвестное состояние игры: " + currentState.getCurrentStep());
        };
    }
    
    /**
     * Обработка начального шага игры
     * @return состояние с предложением принять вызов
     */
    private GameState handleStartStep() {
        return GameState.initial().withNewState(
            "acceptChallenge", 
            "Ты потерял память. Принять вызов НЛО?", 
            false, 
            false
        );
    }
    
    /**
     * Обработка выбора принятия/отклонения вызова
     * @param choice выбор игрока ("1" - принять, "2" - отклонить)
     * @return новое состояние игры
     */
    private GameState handleAcceptChallenge(String choice) {
        return switch (choice) {
            case "1" -> new GameState(
                "goToBridge", 
                "Ты принял вызов. Поднимаешься на мостик к капитану?", 
                false, 
                false
            );
            case "2" -> new GameState(
                "end", 
                "Ты отклонил вызов. Поражение.", 
                true, 
                false
            );
            default -> throw new IllegalArgumentException("Неверный выбор: " + choice);
        };
    }
    
    /**
     * Обработка выбора поднятия на мостик
     * @param choice выбор игрока ("1" - подняться, "2" - отказаться)
     * @return новое состояние игры
     */
    private GameState handleGoToBridge(String choice) {
        return switch (choice) {
            case "1" -> new GameState(
                "identifyYourself", 
                "Ты поднялся на мостик. Ты кто?", 
                false, 
                false
            );
            case "2" -> new GameState(
                "end", 
                "Ты не пошел на переговоры. Поражение.", 
                true, 
                false
            );
            default -> throw new IllegalArgumentException("Неверный выбор: " + choice);
        };
    }
    
    /**
     * Обработка выбора идентификации
     * @param choice выбор игрока ("1" - сказать правду, "2" - солгать)
     * @return новое состояние игры с результатом
     */
    private GameState handleIdentifyYourself(String choice) {
        return switch (choice) {
            case "1" -> new GameState(
                "end", 
                "Тебя вернули домой. Победа!", 
                true, 
                true
            );
            case "2" -> new GameState(
                "end", 
                "Твою ложь разоблачили. Поражение.", 
                true, 
                false
            );
            default -> throw new IllegalArgumentException("Неверный выбор: " + choice);
        };
    }
}