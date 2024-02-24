package game.Flappy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private char currentPlayer = 'X'; // Текущий игрок (начинает игру с 'X')
    private Button[][] buttons = new Button[3][3]; // Массив кнопок для игрового поля 3x3

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane(); // Создание сетки для расположения кнопок

        // Создание и размещение кнопок на игровом поле
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(); // Создание новой кнопки
                button.setMinSize(100, 100); // Установка минимального размера кнопки
                button.setOnAction(event -> {
                    if (button.getText().isEmpty()) { // Проверка, что кнопка пуста
                        button.setText(String.valueOf(currentPlayer)); // Установка символа текущего игрока
                        if (checkForWin()) { // Проверка на победу после каждого хода
                            System.out.println(currentPlayer + " wins!"); // Вывод сообщения о победе
                        } else {
                            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Смена игрока после хода
                        }
                    }
                });
                buttons[i][j] = button; // Добавление кнопки в массив
                gridPane.add(button, i, j); // Добавление кнопки на сетку
            }
        }

        Scene scene = new Scene(gridPane, 300, 300); // Создание сцены
        primaryStage.setTitle("Tic Tac Toe"); // Установка заголовка окна
        primaryStage.setScene(scene); // Установка сцены
        primaryStage.show(); // Отображение окна
    }

    // Метод для проверки наличия выигрышной комбинации
    private boolean checkForWin() {
        // Проверка строк
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer))
                    && buttons[i][1].getText().equals(String.valueOf(currentPlayer))
                    && buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }

        // Проверка столбцов
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer))
                    && buttons[1][i].getText().equals(String.valueOf(currentPlayer))
                    && buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }

        // Проверка диагоналей
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer))
                && buttons[1][1].getText().equals(String.valueOf(currentPlayer))
                && buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer))
                && buttons[1][1].getText().equals(String.valueOf(currentPlayer))
                && buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}