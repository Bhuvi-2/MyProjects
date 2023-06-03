package com.astar;


import java.awt.*;

//By: Brandon Beckwith
public enum SpaceType {
    EMPTY, BLOCK, PATH, INVALID, START, END;

    /**
     * toString method to make printing the values easier
     * @return The abbreviated field.SpaceType
     */
    @Override
    public String toString() {
        switch(this) {
            case EMPTY: return "E";
            case BLOCK: return "B";
            case PATH:  return "P";
            case INVALID: return "I";
            case START: return "S";
            case END: return "F";
            default: throw new IllegalArgumentException();
        }
    }

    /**
     * toColor method to make getting the colors for the board easier
     * @return The Color for the field.SpaceType
     */
    public Color toColor() {
        switch (this){
            case INVALID: return Color.MAGENTA;
            case PATH: return Colors.Path;
            case EMPTY: return Colors.Space;
            case BLOCK: return Colors.Block;
            case START: return Colors.Start;
            case END: return Colors.End;
            default: return Color.YELLOW;
        }
    }
}
