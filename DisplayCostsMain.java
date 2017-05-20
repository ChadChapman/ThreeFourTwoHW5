/*
 * DisplayCostsMain TCSS 342
 */

package application;

import java.util.Scanner;

import structures.Vertex;
import structures.WeightedAdjMatrixGraph;
//import structures.WeightedGraph;

/**
 * A program to display path costs in a weighted directed graph.
 * 
 * @author Alan Fowler
 * @version 1.1
 */
public final class DisplayCostsMain {

    /**
     * A value the user can enter to quit the program.
     */
    private static final int QUIT_OPTION = 0;

    /**
     * The graph used in this program.
     */
    private final WeightedAdjMatrixGraph<String> myGraph;
    
    /** 2d matrix for finding least cost. */
    private double[][] myLeastCostMatrix;
    
    /** User entered value for the origin node to find the path from. */
    private int myOriginNode;
    
    /** User entered value for the terminating node in the path. */
    private int myTerminalNode;
    

    /**
     * Private constructor to inhibit external instantiation.
     */
    private DisplayCostsMain() {
        myGraph = new WeightedAdjMatrixGraph<String>();
        //this is a little long but more readable as is I think
        myLeastCostMatrix = new double[myGraph.getNumberOfVertices()][myGraph.getNumberOfVertices()];
        //System.out.println(myLeastCostMatrix.toString());
    }

    /**
     * The start point for the program.
     * 
     * @param theArgs command line arguments - ignored
     */
    public static void main(final String[] theArgs) {
        new DisplayCostsMain().start();
        
    }

    /**
     * Calls various methods to provide program functionality.
     */
    private void start() {
        FileIO.createGraphFromFile(myGraph);
        final Vertex<String>[] vertices = myGraph.getVertices();

        // temporarily comment in the next line to display a representation of the graph
        // System.out.println(myGraph);

        // report basic statistics correctly
        System.out.println("The number of vertices in the graph : " 
                                                            + myGraph.getNumberOfVertices());
        System.out.println("The number of edges in the graph : " + myGraph.getNumberOfEdges());

        // find diameter
        
        myLeastCostMatrix = WeightedAdjMatrixGraph.floydShortestPaths(myGraph);
        // add code here to set diameter correctly or write a helper method to do it
        final double diameter = getGraphDiameter(myLeastCostMatrix);
        System.out.println("The diameter of this graph is : " + diameter);

        // creates a Scanner for keyboard input
        final Scanner console = new Scanner(System.in);
        boolean runAgain = true;

        // displays an introduction
        displayIntro();

        while (runAgain) { // loops until the user chooses to quit

            int from = 0;
            int to = 0;

            // get the user's choice for a start city
            from =
                            promptForChoice(console, "\nChoose a city to start at " + "(1 - "
                                                     + myGraph.getNumberOfVertices()
                                                     + ") or enter " + QUIT_OPTION
                                                     + " to quit the program : ");

            // perform some processing based on the menu choice
            if (from == QUIT_OPTION) {
                runAgain = false;
            } else {
                
                // edit this to show correct degree for the 'from' city
                final Vertex<String> fromVert = vertices[from - 1];
                System.out.println(fromVert + " has degree " 
                                + myGraph.getNeighbors(fromVert).size() + ".");
                myOriginNode = from;
                // get the user's choice for an end city
                to =
                                promptForChoice(console, "\nChoose a city to end at (1 - "
                                                         + myGraph.getNumberOfVertices()
                                                         + ") or " + QUIT_OPTION
                                                         + " to quit : ");
            }
            if (to == QUIT_OPTION) {
                runAgain = false;
            } else {
                myTerminalNode = to;
                displayPathLength(vertices[from - 1], vertices[to - 1]);
            }
        }
        System.out.println("\nThanks for trying this program. Have a nice day.");
    }
    /**
     * Pretty sure this is a method that is a duplicate but I worked on it so I am leaving it.
     * 
     * @param theLeastCostMatrix 2d array to find diameter of
     * @return int value of the graph's diameter
     */
    private int getGraphDiameter(final double[][] theLeastCostMatrix) {
        int retInt = 0;
        //System.out.println("get vertices length -> " + myGraph.getVertices().length);
        //System.out.println("get myLeastCostMatrix[0] length -> " 
        //+ theLeastCostMatrix.length);
        for (int i = 0; i < myGraph.getVertices().length - 1; i++) {
            for (int j = 0; j < myGraph.getVertices().length - 1; j++) {
                retInt = Math.max(retInt, (int) myLeastCostMatrix[i][j]);
            }
        }
        return retInt;
    }

    /**
     * Displays an introduction to the program.
     */
    public void displayIntro() {
        System.out.println("\nThis program reports the length of "
                           + "the shortest path between two cities.");
        System.out.println("The program will repeat until the user chooses to quit.");
        System.out.println("\nThe cities are:");
        int city = 1;
        for (final Vertex<String> name : myGraph.getVertices()) {
            System.out.printf("%-3d%s\n", city++, name);
        }
    }

    /**
     * Prompts for a menu choice in the range 1 to QUIT_OPTION.
     * 
     * @param theConsole a Scanner used to capture user input
     * @param thePrompt a prompt to the user
     * @return the number entered by the user
     */
    private int promptForChoice(final Scanner theConsole, final String thePrompt) {
        int choice = getInt(theConsole, thePrompt);
        while (choice < 0 || choice > myGraph.getNumberOfVertices()) {
            System.out.println("Invalid selection. Please try again.");
            choice = getInt(theConsole, thePrompt);
        }
        return choice;
    }

    /**
     * Prompts for an integer until an integer is entered.
     * 
     * This method is adopted from getInt() on page 315 of
     * "Building Java Programs" by Reges and Stepp
     * 
     * @param theConsole a Scanner used to capture user input
     * @param thePrompt a prompt to the user
     * @return the integer entered by the user
     */
    private int getInt(final Scanner theConsole, final String thePrompt) {
        System.out.print(thePrompt);
        while (!theConsole.hasNextInt()) {
            theConsole.next();
            System.out.println("Enter an integer. Please try again.");
            System.out.print(thePrompt);
        }
        return theConsole.nextInt();
    }

    /**
     * Displays the length of the path from thePoint1 to thePoint2.
     * 
     * @param thePoint1 the start point for the path
     * @param thePoint2 the end point for the path
     */
    private void displayPathLength(final Vertex<String> thePoint1,
                                   final Vertex<String> thePoint2) {
        // add code here to set path_length correctly
        //final int pt1Element = Integer.parseInt(thePoint1.);
        //final int pt2Element = Integer.parseInt(thePoint2.getLabel());
        //not sure how to pass in vertices of type string and 
        //still get ints w/o a switch statement
        final double pathLength = myLeastCostMatrix[myOriginNode][myTerminalNode];
        System.out.printf("The distance from " + thePoint1 + " to " + thePoint2 + " is: %,.1f",
                          pathLength);
    }

}
