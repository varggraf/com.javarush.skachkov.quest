import model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.GameService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для GameService.
 * Проверяем корректность обработки различных состояний игры.
 */
@DisplayName("Тестирование игрового сервиса")
class GameServiceTest {
    private GameService gameService;
    private GameState initialState;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
        initialState = GameState.builder()
                .currentStep("start")
                .message("Initial message")
                .gameOver(false)
                .victory(false)
                .build();
    }

    @Test
    @DisplayName("Обработка начального состояния")
    void testInitialState() {
        GameState newState = gameService.processChoice(initialState, null);
        
        assertAll(
            () -> assertEquals("acceptChallenge", newState.getCurrentStep()),
            () -> assertEquals("Ты потерял память. Принять вызов НЛО?", newState.getMessage()),
            () -> assertFalse(newState.isGameOver()),
            () -> assertFalse(newState.isVictory())
        );
    }

    @Test
    @DisplayName("Принятие вызова - переход на мостик")
    void testAcceptChallenge_Accept() {
        GameState state = GameState.builder()
                .currentStep("acceptChallenge")
                .build();
        
        GameState newState = gameService.processChoice(state, "1");
        
        assertAll(
            () -> assertEquals("goToBridge", newState.getCurrentStep()),
            () -> assertEquals("Ты принял вызов. Поднимаешься на мостик к капитану?", newState.getMessage()),
            () -> assertFalse(newState.isGameOver())
        );
    }

    @Test
    @DisplayName("Отклонение вызова - поражение")
    void testAcceptChallenge_Decline() {
        GameState state = GameState.builder()
                .currentStep("acceptChallenge")
                .build();
        
        GameState newState = gameService.processChoice(state, "2");
        
        assertAll(
            () -> assertEquals("end", newState.getCurrentStep()),
            () -> assertEquals("Ты отклонил вызов. Поражение.", newState.getMessage()),
            () -> assertTrue(newState.isGameOver()),
            () -> assertFalse(newState.isVictory())
        );
    }

    @Test
    @DisplayName("Поднятие на мостик - идентификация")
    void testGoToBridge_Go() {
        GameState state = GameState.builder()
                .currentStep("goToBridge")
                .build();
        
        GameState newState = gameService.processChoice(state, "1");
        
        assertAll(
            () -> assertEquals("identifyYourself", newState.getCurrentStep()),
            () -> assertEquals("Ты поднялся на мостик. Ты кто?", newState.getMessage()),
            () -> assertFalse(newState.isGameOver())
        );
    }

    @Test
    @DisplayName("Отказ подниматься на мостик - поражение")
    void testGoToBridge_Refuse() {
        GameState state = GameState.builder()
                .currentStep("goToBridge")
                .build();
        
        GameState newState = gameService.processChoice(state, "2");
        
        assertAll(
            () -> assertEquals("end", newState.getCurrentStep()),
            () -> assertEquals("Ты не пошел на переговоры. Поражение.", newState.getMessage()),
            () -> assertTrue(newState.isGameOver()),
            () -> assertFalse(newState.isVictory())
        );
    }

    @Test
    @DisplayName("Правдивая идентификация - победа")
    void testIdentifyYourself_Truth() {
        GameState state = GameState.builder()
                .currentStep("identifyYourself")
                .build();
        
        GameState newState = gameService.processChoice(state, "1");
        
        assertAll(
            () -> assertEquals("end", newState.getCurrentStep()),
            () -> assertEquals("Тебя вернули домой. Победа!", newState.getMessage()),
            () -> assertTrue(newState.isGameOver()),
            () -> assertTrue(newState.isVictory())
        );
    }

    @Test
    @DisplayName("Ложная идентификация - поражение")
    void testIdentifyYourself_Lie() {
        GameState state = GameState.builder()
                .currentStep("identifyYourself")
                .build();
        
        GameState newState = gameService.processChoice(state, "2");
        
        assertAll(
            () -> assertEquals("end", newState.getCurrentStep()),
            () -> assertEquals("Твою ложь разоблачили. Поражение.", newState.getMessage()),
            () -> assertTrue(newState.isGameOver()),
            () -> assertFalse(newState.isVictory())
        );
    }

    @Test
    @DisplayName("Неверный выбор - исключение")
    void testInvalidChoice() {
        GameState state = GameState.builder()
                .currentStep("acceptChallenge")
                .build();
        
        assertThrows(IllegalArgumentException.class, () -> {
            gameService.processChoice(state, "invalid");
        });
    }

    @Test
    @DisplayName("Неизвестное состояние - исключение")
    void testUnknownState() {
        GameState state = GameState.builder()
                .currentStep("unknown_state")
                .build();
        
        assertThrows(IllegalStateException.class, () -> {
            gameService.processChoice(state, "1");
        });
    }
}