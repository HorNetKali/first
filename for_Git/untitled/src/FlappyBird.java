import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    final int WIDTH = 800, HEIGHT = 600; // Определение размеров окна игры
    Timer timer; // Таймер для обновления игры
    boolean isPlaying = true; // Флаг, указывающий на активное состояние игры

    // Параметры птицы
    int birdX, birdY, birdWidth = 20, birdHeight = 20;
    int birdYSpeed = 0;

    // Список труб
    ArrayList<Rectangle2D> pipes;
    int pipeWidth = 50, pipeHeightGap = 150;
    int pipeXSpeed = 5;
    int pipeXStart = WIDTH;
    int pipeSpacing = 200;
    Random random;

    int score = 0; // Счет игрока
    Font font = new Font("Arial", Font.BOLD, 30); // Шрифт для отображения счета

    // Конструктор класса FlappyBird
    public FlappyBird() {
        JFrame frame = new JFrame(); // Создание нового окна JFrame
        timer = new Timer(20, this); // Инициализация таймера с периодом 20 миллисекунд
        random = new Random(); // Инициализация генератора случайных чисел
        pipes = new ArrayList<>(); // Создание списка для хранения труб

        frame.setTitle("Flappy Bird"); // Установка заголовка окна
        frame.setSize(WIDTH, HEIGHT); // Установка размеров окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна
        frame.getContentPane().setBackground(Color.CYAN); // Установка цвета фона окна
        frame.addKeyListener(this); // Добавление обработчика событий клавиатуры к окну

        // Добавление текущего экземпляра FlappyBird (который является JPanel) к фрейму
        frame.add(this);

        birdX = WIDTH / 4; // Установка начальной позиции птицы по оси X
        birdY = HEIGHT / 2; // Установка начальной позиции птицы по оси Y

        frame.setVisible(true); // Делаем окно видимым
        startGame(); // Начало игры
    }

    // Метод для начала новой игры
    public void startGame() {
        pipes.clear(); // Очистка списка труб
        birdY = HEIGHT / 2; // Возвращение птицы в центр экрана по оси Y
        birdYSpeed = 0; // Сброс скорости птицы
        score = 0; // Сброс счета игры
        pipes.add(new Rectangle2D.Double(WIDTH, 0, pipeWidth, random.nextInt(HEIGHT / 2) - HEIGHT / 4)); // Добавление первой трубы
        timer.start(); // Запуск таймера
    }

    // Переопределение метода для отрисовки компонентов игры
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Отрисовка птицы
        g.setColor(Color.RED);
        g.fillRect(birdX, birdY, birdWidth, birdHeight);

        // Отрисовка труб
        for (Rectangle2D pipe : pipes) {
            g.setColor(Color.GREEN);
            g.fillRect((int) pipe.getX(), (int) pipe.getY(), pipeWidth, (int) pipe.getHeight());
            g.fillRect((int) pipe.getX(), (int) pipe.getHeight() + pipeHeightGap, pipeWidth, HEIGHT - (int) pipe.getHeight() + pipeHeightGap);
        }

        // Отображение счета игры
        g.setFont(font);
        g.drawString("Score: " + score, 10, 30);
    }

    // Метод для обновления состояния игры
    public void update() {
        birdY += birdYSpeed; // Обновление позиции птицы по оси Y
        birdYSpeed += 1; // Увеличение скорости падения птицы

        // Обновление позиций и удаление труб
        for (int i = 0; i < pipes.size(); i++) {
            Rectangle2D pipe = pipes.get(i);
            pipe.setRect(pipe.getX() - pipeXSpeed, pipe.getY(), pipe.getWidth(), pipe.getHeight());

            // Проверка на столкновение с трубой
            if (pipe.getX() + pipe.getWidth() < 0) {
                pipes.remove(pipe);
                pipes.add(new Rectangle2D.Double(WIDTH, 0, pipeWidth, random.nextInt(HEIGHT / 2) - HEIGHT / 4));
                i--;
            }

            // Увеличение счета при пролете между трубами
            if (pipe.getX() + pipe.getWidth() < birdX && !pipe.intersects(birdX, birdY, birdWidth, birdHeight)) {
                score++;
            }

            // Проверка на столкновение с трубой
            if (pipe.intersects(birdX, birdY, birdWidth, birdHeight)) {
                gameOver();
            }
        }

        // Проверка на столкновение с верхней или нижней границей экрана
        if (birdY > HEIGHT || birdY < 0) {
            gameOver();
        }

        repaint(); // Перерисовка окна
    }

    // Метод для завершения игры
    public void gameOver() {
        timer.stop(); // Остановка таймера
        JOptionPane.showMessageDialog(null, "Game Over! Your score: " + score); // Вывод сообщения о завершении игры и счета
        startGame(); // Начало новой игры
    }

    // Метод для обработки события таймера
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPlaying) {
            update(); // Обновление состояния игры
        }
    }
    // Метод для обработки нажатия клавиши
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            birdYSpeed = -10; // При нажатии пробела птица подпрыгивает
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Метод не используется, но требуется для реализации интерфейса KeyListener
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Метод не используется, но требуется для реализации интерфейса KeyListener
    }

    // Главный метод main для запуска игры
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlappyBird::new);
    }
}
