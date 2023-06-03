package com.astar;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

//By: Brandon Beckwith
class AStar {

    // NOTE: This is the only class you need to edit.
    //Feel free to add whatever methods you need here!

    /**
     * Runs A star on the given board
     * @param board The board to run A Star on
     * @param start The starting Point
     * @param end The ending Point
     * @return The spaces in order from the start Point to the end Point
     */
    public static ArrayList<Space> findPath(Board board, Point start, Point end) {

        //Setup an ArrayList to hold the path to return to the GUI (given)
        ArrayList<Space> path = new ArrayList<Space>();

        
        // TODO: Implement AStar

        Space startSpace = board.getSpace(start.x, start.y);
        startSpace.setDistFrom(0);
        startSpace.setDistTo(ManhattanDistance(start, end));

        PriorityQueue<Space> frontier = new PriorityQueue<Space>();
        HashSet<Space> visited = new HashSet<Space>();
        frontier.add(startSpace);

       while (frontier.size()!=0){
            Space current = frontier.poll(); //Get the space with the loweest estimated total distance

            //Check if we reached the goal space
            if (current.getPoint().equals(end)){
                return reconstructPath(current, path);
            }
            
            visited.add(current);   //adding current space to visited list

            //Check each neighbor of the current space
            for (Space neighbor: board.getNeighbors(current)){
                //Skip the neighbor if it is already in the visited list
                if (visited.contains(neighbor)){
                    continue;
                }

                //Calculate the tentative distance from the start space
                int tempDistFrom = current.getDistFrom()+1;

                //Add the neighbor to the frontier if it's not already there or checks if the current space has a better path to the goal
                if (!frontier.contains(neighbor) || current.getDistFrom() + ManhattanDistance(neighbor.getPoint(), end) < neighbor.getDistFrom() + ManhattanDistance(neighbor.getPoint(), end)) {
                    
                   
                //Update the neighbor's distance and previous space
                neighbor.setDistFrom(tempDistFrom);
                neighbor.setDistTo(ManhattanDistance(neighbor.getPoint(), end));
                neighbor.setPrevious(current);

                //removes the neighbor from the frontier if it already there
                if (frontier.contains(neighbor)){
                   frontier.remove(neighbor);
                }

                //adds neighbor to frontier
                frontier.add(neighbor);
                

            }

       }
    }
        return null; //return null if there is no path from start to end
        
    }
    

    //Calculates the ManhattanDistance between 2 points
    private static int ManhattanDistance(Point one, Point two) {
        return (int)(Math.abs(one.getX() - two.getX()) + Math.abs(one.getY() - two.getY()));
    }

    //Method to reconstruct the best path found by the AI
    private static ArrayList<Space> reconstructPath(Space endSpace, ArrayList<Space> path) {
        Space current = endSpace;   //Assigning a current space to the endspace parameter
        
        //while the current space has something in it, reconstructs the path by getting the previous space continuously
        while (current != null) {
            path.add(current);
            current = current.getPrevious();
        }
        Collections.reverse(path);  //reverses the order of the inserted spaces
        return path;    //returns final path
    }
    

}
