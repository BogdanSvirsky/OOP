package ru.nsu.svirsky.task_2_3_1.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;
import ru.nsu.svirsky.task_2_3_1.utils.DirectionOfMovement;
import ru.nsu.svirsky.task_2_3_1.utils.GameConfig;
import ru.nsu.svirsky.task_2_3_1.utils.GameState;
import ru.nsu.svirsky.task_2_3_1.utils.RobotSnake;
import ru.nsu.svirsky.task_2_3_1.utils.interfaces.WinCondition;

public class SnakeGame {
    private final Snake snake;
    private final FoodManager foodManager;
    private GameState state = GameState.RUNNING;
    private final GameField field;
    private final WinCondition winCondition;
    private final List<RobotSnake> robotSnakes = new ArrayList<>();
    private final int robotsCount;

    public SnakeGame(GameConfig config) {
        this.field = new GameField(config.columnsCount(), config.rowsCount());
        snake = new Snake(config.snakeStartCoords());

        List<Coordinates> availCoords = field.getCells();
        availCoords.remove(config.snakeStartCoords());
        robotsCount = config.robotSnakesCount();
        spawnRobots(availCoords);

        winCondition = () -> snake.getCoordinatesList().size() == config.winLen();
        foodManager = new FoodManager(config.foodCount());
        foodManager.fillField(getFreeCells());
    }

    private void spawnRobots(List<Coordinates> availCoords) {
        robotSnakes.clear();
        List<Coordinates> coords = new ArrayList<>(availCoords);
        Random random = new Random();

        robotSnakes.add(new MyRobotSnake(coords.remove(random.nextInt(coords.size()))));
        robotSnakes.add(new StupidRobotSnake(coords.remove(random.nextInt(coords.size()))));
    }

    public void gameLoop(DirectionOfMovement snakeDirection) {
        if (state != GameState.RUNNING) {
            return;
        }

        switch (processSnakeMove(snake, snakeDirection)) {
            case FOOD:
                if (winCondition.isWin()) {
                    state = GameState.WIN;
                } else {
                    foodManager.fillField(getFreeCells());
                }
                break;
            case BOARDS:
            case SNAKE:
                state = GameState.GAME_OVER;
                break;
            case null:
                break;
        }
        for (Iterator<RobotSnake> iterator = robotSnakes.iterator(); iterator.hasNext(); ) {
            RobotSnake myRobotSnake = iterator.next();
            CollisionType collisionType = processSnakeMove(myRobotSnake,
                    myRobotSnake.getNextMove(getFoodCoords()));
            if (collisionType == CollisionType.BOARDS || collisionType == CollisionType.SNAKE) {
                iterator.remove();
            } else if (collisionType == CollisionType.FOOD) {
                foodManager.fillField(getFreeCells());
            }
        }
    }

    private CollisionType processSnakeMove(Snake snake, DirectionOfMovement direction) {
        Coordinates nextHead = snake.getHead().getNeighbor(direction);

        if (checkBoardCollision(nextHead)) {
            return CollisionType.BOARDS;
        }

        if (checkSnakeCollision(nextHead)) {
            return CollisionType.SNAKE;
        }

        boolean hasEaten = foodManager.eat(nextHead);
        snake.move(direction, hasEaten);

        if (hasEaten) {
            return CollisionType.FOOD;
        }
        return null;
    }


    public List<Coordinates> getFreeCells() {
        List<Coordinates> freeCells = field.getCells();
        freeCells.removeAll(getSnakeCoords());
        freeCells.removeAll(getFoodCoords());
        return freeCells;
    }

    public void restart() {
        state = GameState.RUNNING;
        snake.reset();
        foodManager.reset();
        List<Coordinates> coords = field.getCells();
        coords.remove(snake.getCoordinatesList().getFirst());
        spawnRobots(coords);
        foodManager.fillField(getFreeCells());
    }

    public boolean checkSnakeCollision(Coordinates coords) {
        return (!coords.equals(snake.getHead()) && getSnakeCoords().contains(coords))
                || getRobotSnakesCoords().contains(coords);
    }

    public boolean checkBoardCollision(Coordinates coords) {
        return coords.x() < 0 || coords.x() >= field.columnsCount() || coords.y() < 0
                || coords.y() >= field.rowsCount();
    }

    public List<Coordinates> getSnakeCoords() {
        return snake.getCoordinatesList();
    }

    public List<Coordinates> getFoodCoords() {
        return foodManager.getFoodCoords();
    }

    public List<Coordinates> getRobotSnakesCoords() {
        List<Coordinates> result = new ArrayList<>();
        for (RobotSnake robotSnake : robotSnakes) {
            result.addAll(robotSnake.getCoordinatesList());
        }
        return result;
    }

    public GameState getState() {
        return state;
    }
}
