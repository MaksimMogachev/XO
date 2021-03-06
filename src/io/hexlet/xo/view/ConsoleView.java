package io.hexlet.xo.view;

import io.hexlet.xo.controllers.CurrentMoveController;
import io.hexlet.xo.controllers.MoveController;
import io.hexlet.xo.controllers.WinnerController;
import io.hexlet.xo.model.Field;
import io.hexlet.xo.model.Figure;
import io.hexlet.xo.model.Game;
import io.hexlet.xo.model.exceptions.AlreadyOccupiedException;
import io.hexlet.xo.model.exceptions.InvalidPointExceptions;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {

    private CurrentMoveController currentMoveController = new CurrentMoveController();

    private final WinnerController winnerController = new WinnerController();

    private final MoveController moveController = new MoveController();

    public void show(final Game<Figure> game) {
        System.out.format("Game name %s\n", game.getName());
        final Field<Figure> field = game.getField();
        for (int x = 0; x < field.getSize(); x++){
            if (x != 0)
                printSeparator();
            printLine(field, x);
        }
    }



    public boolean move(final Game<Figure> game) {
        final Field<Figure> field = game.getField();
        final Figure winner = winnerController.getWinner(field);
        if (winner != null) {
            System.out.format("Winner is: %s\n", winner);
            return false;
        }
        final Figure currentFigure = currentMoveController.currentMove(field);
        if (currentFigure == null) {
            System.out.println("No winner and no moves left.");
            return false;

        }
        System.out.format("Please enter move point for: %s\n", currentFigure);
        final Point point = askPoint();
        try {
            moveController.applyFigure(field, point, currentFigure);
        } catch (final InvalidPointExceptions | AlreadyOccupiedException e) {
            System.out.println("Point is invalid.");
        }
        return true;
    }

    private Point askPoint() {
        return new Point(askCoordinate("X") - 1, askCoordinate("Y") - 1);
    }

    private int askCoordinate(final String coordinateName) {
        System.out.format("Please input %s:", coordinateName);
        final Scanner in = new Scanner(System.in);
        try {
            return in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("0_0 kek");
            return askCoordinate(coordinateName);
        }
    }

    private void printLine(final Field<Figure> field, final int x) {

        for (int y = 0; y < field.getSize(); y++) {
            System.out.print(" ");
            final Figure figure;
            try {
                figure = field.getFigure(new Point(y, x));
            } catch (InvalidPointExceptions invalidPointExceptions) {
                invalidPointExceptions.printStackTrace();
                throw new RuntimeException(invalidPointExceptions);
            }
            System.out.print(figure != null ? figure : " ");
            System.out.print(" ");
            if (y != field.getSize() - 1)
            System.out.print("|");
        }
        System.out.println();
    }

    private void printSeparator() {
        System.out.println("~~~~~~~~~~~");
    }

}
