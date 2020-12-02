package io.hexlet.xo.model;

import io.hexlet.xo.model.exceptions.AlreadyOccupiedException;
import io.hexlet.xo.model.exceptions.InvalidPointExceptions;

import java.awt.*;

public class Field<T> {

    private final int fieldSize;

    private static final int MIN_COORDINATE = 0;

    private final T[][] field;

    public Field(int fieldSize) {
        this.fieldSize = fieldSize;
        field = (T[][])new Object[fieldSize][fieldSize];
    }

    public int getSize() {
        return fieldSize;
    }

    public T getFigure(final Point point) throws InvalidPointExceptions {
        if (!checkPoint(point)) {
            throw new InvalidPointExceptions();
        }
        return field[point.x][point.y];
    }

    public void setFigure(final Point point, final T figure) throws InvalidPointExceptions, AlreadyOccupiedException {
        if (!checkPoint(point)) {
            throw new InvalidPointExceptions();
        }
        field[point.x][point.y] = figure;
    }

    private boolean checkPoint(final Point point) {
        return checkCoordinate(point.x, field.length) && checkCoordinate(point.y, field[point.x].length);
    }

    private boolean checkCoordinate(final int coordinate, final int maxCoordinate) {
        return coordinate >= MIN_COORDINATE && coordinate < maxCoordinate;
    }

}
