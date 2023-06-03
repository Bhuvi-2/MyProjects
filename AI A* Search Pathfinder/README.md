# A Star Search Algorithm
This assignment focuses on the implementation of the A Star search algorithm.

# Objectives
After completing this exercise you will be able to:
* Implement **Java code** that uses an AI search algorithm
* **Solve a path finding problem** using a search.
* Implement the **A Star** algorithm and a viable **heuristic**

# Code Base Details

For this program you will be implementing the A Star algorithm into an already made GUI. The **only** file you should be editing is `src/main/java/com/astar/AStar/java`. This file contains a single method named **findPath**, which expects 3 paremeters: **Board** and two **Points**, a start and end point. This method returns an `ArrayList<Space>` of **Space**s that make up the path from *start* to *end*.

```Java
public static ArrayList<Space> findPath(Board board, Point start, Point end) {

        //Setup an ArrayList to hold the path to  return to the GUI
        ArrayList<Space> path = new ArrayList<Space>();

        // TODO: Implement AStar

        return path;
        }
```

* **Board** is a class defined in the assignment and contains the *state* of the board along with several helper methods.
  * Contains a 2D representation of the environment, methods that get surrounding neighbors and other utilites. 
* The [**Point**](https://docs.oracle.com/javase/7/docs/api/java/awt/Point.html) class is defined by Java's own libraries and is used to represent a location in a 2D space. 
* **Space** is another class defined in this package that holds information about a space on the **Board**.  
  * Contains methods that return the **SpaceType**, position, parent / previous node (useful for getting the path) and helper methods.
  * To be successful it's important to understand the information provided by the **Board** and its **Spaces**.
* The **findPath** method returns an **ArrayList** of **Spaces** in order from *start* to *end*.

# A Star Implementation
This assignment only requires a path finding implementation, in this case A Star. All code should be contained in the `AStar.java` file, you shouldn't need to edit any other files. If you need a function that defines a **heuristic**, you can define it in the same file. 

There are many great references out there that have AStar pseudocode, [Wikipedia](https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode) being one of many.

A good way to check your work is to run the program often. If there is any issues with the implementation they should be easy to spot.

# Up and Running
This codebase has been run with the latest version of [Java (17)](https://www.oracle.com/java/technologies/downloads/#java17) using [Eclipse](https://www.eclipse.org/downloads/), [IntelliJ](https://www.jetbrains.com/idea/) and [VSCode](https://code.visualstudio.com/). After cloning and opening the assignment with your IDE of choice you should be able to run the program and see a GUI. 

You can either *clone* or download the assignment. Using [Git](https://git-scm.com/) and forking / cloning the codebase is encouraged. However, **DO NOT MAKE YOUR CODE PUBLIC**.

## Using Maven (Optional)
Included is a `pom.xml` that defines some basic lifecycle operations. It's not needed for this assignment, but if you're comfortable or interested in getting familiar with Maven you can find some more information here [Maven Getting Started](https://maven.apache.org/guides/getting-started/). Most IDEs will pick up on the `pom.xml` and you shouldn't need to do anything special. 

# GUI Controls
 * Left/Primary click on a tile to specify the **start** then click on another tile to specify the **end**. After both are defines the `findPath` method is called and the given path is displayed.
 * Right/Secondary click on a tile to toggle it between a Block and an open space.
 * The slider at the top can be used to adjust the number of blocks randomly placed in the environment.