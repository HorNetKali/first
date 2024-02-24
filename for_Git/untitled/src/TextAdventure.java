import java.util.Scanner;
import java.util.HashMap;

// Основной класс игры
public class TextAdventure {

    public static void main(String[] args) {
        AdventureModel adventure = new AdventureModel();
        adventure.start();
    }
}

// Класс, отвечающий за логику игры
class AdventureModel {
    private Adventurer player;
    private HashMap<String, Room> rooms;

    public AdventureModel() {
        player = new Adventurer("Adventurer", "You are the brave adventurer seeking treasure!");
        rooms = new HashMap<>();
        Room startRoom = new Room("Start Room", "You are in a dimly lit room. There are doors to the north and east.");
        Room treasureRoom = new Room("Treasure Room", "You found the treasure room! There is a shiny treasure chest here.");
        startRoom.setExit("north", treasureRoom);
        treasureRoom.setExit("south", startRoom);
        rooms.put("start", startRoom);
        rooms.put("treasure", treasureRoom);
    }

    // Начало игры и обработка команд
    public void start() {
        player.printLocation();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0].toLowerCase();

            if (command.equals("go")) {
                if (parts.length < 2) {
                    System.out.println("Please specify a direction to go.");
                } else {
                    String direction = parts[1].toLowerCase();
                    player.go(direction, rooms);
                }
            } else if (command.equals("look")) {
                player.look();
            } else if (command.equals("exit")) {
                System.out.println("Exiting game. Goodbye!");
                break;
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }
        scanner.close();
    }
}

// Класс, представляющий игрока
class Adventurer {
    private String name;
    private String description;
    private Room currentRoom;

    public Adventurer(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Вывод информации о текущем местоположении игрока
    public void printLocation() {
        System.out.println(name + ": " + description);
    }

    // Перемещение игрока в указанном направлении
    public void go(String direction, HashMap<String, Room> rooms) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("You cannot go that way.");
        } else {
            currentRoom = nextRoom;
            currentRoom.describe();
        }
    }

    // Просмотр описания текущей комнаты
    public void look() {
        currentRoom.describe();
    }
}

// Класс, представляющий комнату
class Room {
    private String name;
    private String description;
    private HashMap<String, Room> exits;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
    }

    // Установка выхода из комнаты в указанном направлении
    public void setExit(String direction, Room room) {
        exits.put(direction, room);
    }

    // Получение комнаты, в которую можно попасть из текущей по указанному направлению
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    // Вывод описания комнаты
    public void describe() {
        System.out.println(name + ": " + description);
    }
}