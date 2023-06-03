package com.astar;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

//By: Brandon Beckwith
public class Board {

    //Represent the board as a 2d array of spaces
    private final Space[][] spaces;

    /*
        Used to get the neighbors
        +---+
        |oxo|
        |x0x|
        |oxo|
        +---+
     */
    private static final int[][] NEIGH_LOC = {{1,0},{-1,0},{0,1},{0,-1}};

    //Keep track of SIZE
    private final int SIZE;

    /**
     * Initialize the board
     * @param size The SIZE of the board (NxN)
     */
    public Board(int size) {
        this.SIZE = size;
        spaces = new Space[this.SIZE][this.SIZE];
        for (int i = 0; i < this.SIZE; i++) {
            for (int j = 0; j < this.SIZE; j++) {
                this.spaces[i][j] = new Space(SpaceType.INVALID, i, j);
            }
        }
    }

    /**
     * Checks to see if the space is in range
     * @param val The x or y of the space
     * @return true if the space is safe
     */
    private boolean isSafe(int val){
        return (val >= 0 && val < SIZE);
    }

    /**
     * Checks to see if teh space is in range
     * @param val1 Either the x or y of the space
     * @param val2 Either the x or y of the space
     * @return true if the space is safe
     */
    private boolean isSafe(int val1, int val2){
        return (isSafe(val1) && isSafe(val2));
    }

    /**
     * Generates the board based off of the level of randomness given
     * @param randomness The amount of randomness
     */
    public void generateBoard(float randomness){
        for (int i = 0; i < this.SIZE; i++){
            for (int j = 0; j < this.SIZE; j++){

                //Either set the space to be empty or be blocked
                if (Math.random() > randomness){
                    this.spaces[i][j].setType(SpaceType.EMPTY);
                } else {
                    this.spaces[i][j].setType(SpaceType.BLOCK);
                }
            }
        }
    }


    /**
     * Gets a space
     * @param x The space's x coordinate
     * @param y The space's y coordinate
     * @return The Space
     */
    public Space getSpace(int x, int y){
        return isSafe(x,y) ? this.spaces[x][y] : null;
    }

    /**
     * Gets a space
     * @param point The space's Point
     * @return The Space
     */
    public Space getSpace(Point point){
        return getSpace(point.x, point.y);
    }

    /**
     * Swaps the space type between block and empty
     * @param x The space's x coordinate
     * @param y The space's y coordinate
     */
    public void swapSpace(int x, int y) {
        if (isSafe(x, y)) {
            this.getSpace(x, y).swapType();
        }
    }

    /**
     * Gets all empty neighboring spaces
     * @param space
     * @return
     */
    public Space[] getNeighbors(Space space){
        ArrayList<Space> neighbors = new ArrayList<Space>();

        //Visit each immediately adjacent space (+)
        for (int[] i : NEIGH_LOC){
            int x = space.getX() + i[0];
            int y = space.getY() + i[1];

            if (isSafe(x,y)) {
                Space s = this.getSpace(x, y);

                if (s.getType() == SpaceType.EMPTY)
                    //If the space is safe and empty add it to the neighbor list
                    neighbors.add(s);
            }
        }
        //Convert from an ArrayList and return the neighbors
        return neighbors.toArray(new Space[neighbors.size()]);
    }

    /**
     * Reset the state of the board
     * by calling reset on each spacea
     */
    public void resetState(){
        for (Space spc[] : spaces){
            for (Space space: spc){
                space.reset();
            }
        }
    }

    /**
     * To string to print the board
     * @return A string representing the board
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Space[] s: this.spaces){
            out.append(Arrays.toString(s)).append('\n');
        }
        return out.toString();
    }
}
