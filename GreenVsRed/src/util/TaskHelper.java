package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskHelper {
    public Integer solveTask(List<List<String>> matrix, int x1, int y1, int turns, int x, int y) {
        //matrix => 0 - red, 1- green !!!

        //keeps the count of greenStates of the specified elements during the changes of each Generation.
        int greenStates = 0;


        //The state matrix is a mirror image of the main matrix(which comes from the user).
        //It has the same width and height as the main matrix
        //Below are described what are the purposes and goal of the state matrix.
        //In a few words- it shows whether a specific element in the main matrix should change in the next Generation;
        List<List<Boolean>> stateMatrix = new ArrayList<>();
        fillStateMatrix(stateMatrix, x, y);


        //Here we loop through the specified turns of the game
        for (int counter = 0; counter <= turns; counter++) {
            //Specified element in the main matrix provided by the user;
            String element = matrix.get(y1).get(x1);
            //More info about this method below
            greenStates = incrementIfGreen(element, greenStates);
            //More info about this method below
            makeChangesInStateMatrix(matrix, stateMatrix);
            //More info about this method below
            transformMatrix(matrix, stateMatrix);
            //After each turn the state matrix is cleaned.
            stateMatrix = new ArrayList<>();
            //More info about this method below
            fillStateMatrix(stateMatrix, x, y);

        }
        //Returning the total count of green states in which the specified element has been during the game
        return greenStates;
    }

/*
    The method changes the elements of the state matrix from "false" to "true" when such change is needed.
    The willElementChangeColor(); method returns a result from which it depends whether or not the element should change.
    The willElementChangeColor(); method is described below.
 */
    private void makeChangesInStateMatrix(List<List<String>> matrix, List<List<Boolean>> stateMatrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                String currentElement = matrix.get(i).get(j);
                if (currentElement.equals("0")) {
                    if (willElementChangeColor(matrix, i, j, List.of(3, 6))) {
                        stateMatrix.get(i).set(j, true);
                    }
                } else {
                    if (willElementChangeColor(matrix, i, j, List.of(0, 1, 4, 5, 7, 8))) {
                        stateMatrix.get(i).set(j, true);
                    }
                }
            }
        }
    }

    /*
        The method transforms the main matrix.
        The state matrix gives information about which elements should change color in the next Generation.

        E.g. Element at index [1, 0] in that state matrix is true, meaning that the element at same index in the main matrix
        should change its color either to green or red("1"/"0").
     */

    private void transformMatrix(List<List<String>> matrix, List<List<Boolean>> stateMatrix) {
        for (int i = 0; i < stateMatrix.size(); i++) {
            for (int j = 0; j < stateMatrix.get(i).size(); j++) {
                if (stateMatrix.get(i).get(j)) {
                    if (matrix.get(i).get(j).equals("0")) {
                        matrix.get(i).set(j, "1");
                    } else {
                        matrix.get(i).set(j, "0");
                    }
                }
            }
        }
    }

    /*
        The method fills the state matrix with "false" elements, meaning that all the elements in the state matrix will be false.
     */
    private void fillStateMatrix(List<List<Boolean>> stateMatrix, int x, int y) {
        for (int row = 0; row < x; row++) {
            stateMatrix.add(new ArrayList<>());
            for (int col = 0; col < y; col++) {
                stateMatrix.get(row).add(false);
            }
        }
    }

    /*
        The method increments the greenStates counter when the specified element is "1"->green.
     */
    private int incrementIfGreen(String element, int greenStates) {
        if (element.equals("1")) {
            return greenStates + 1;
        }
        return greenStates;
    }

    /*
        The method checks whether the specified element will change its color in the next Generation.
        Here are applied the 4  ground rules.
        The List<Integer> neededCount is the needed count of green neighbours of an element(green/red) to change its color.
      */
    private boolean willElementChangeColor(List<List<String>> matrix, int x1, int y1, List<Integer> neededCount) {
        String diagonalTopLeft =
                isInRange(matrix, x1 - 1, y1 - 1) ? matrix.get(x1 - 1).get(y1 - 1) : "invalid element";

        String diagonalTopRight =
                isInRange(matrix, x1 - 1, y1 + 1) ? matrix.get(x1 - 1).get(y1 + 1) : "invalid element";

        String diagonalBottomLeft =
                isInRange(matrix, x1 + 1, y1 - 1) ? matrix.get(x1 + 1).get(y1 - 1) : "invalid element";

        String diagonalBottomRight =
                isInRange(matrix, x1 + 1, y1 + 1) ? matrix.get(x1 + 1).get(y1 + 1) : "invalid element";


        String leftElement = isInRange(matrix, x1, y1 - 1) ? matrix.get(x1).get(y1 - 1) : "invalid element";
        String topElement = isInRange(matrix, x1 - 1, y1) ? matrix.get(x1 - 1).get(y1) : "invalid element";
        String rightElement = isInRange(matrix, x1, y1 + 1) ? matrix.get(x1).get(y1 + 1) : "invalid element";
        String bottomElement = isInRange(matrix, x1 + 1, y1) ? matrix.get(x1 + 1).get(y1) : "invalid element";

        List<String> listNeighbours = List.of(diagonalTopLeft, diagonalBottomLeft, diagonalBottomRight,
                diagonalTopRight, rightElement, topElement, bottomElement, leftElement);

        int countGreenNeighbours = (int) listNeighbours
                .stream()
                .filter(element -> !element.equals("invalid element") && !element.equals("0"))
                .count();

        return neededCount.contains(countGreenNeighbours);
    }

    /*
        The method checks if the specified element is inside the matrix.
     */
    private boolean isInRange(List<List<String>> matrix, int rows, int cols) {

        return rows >= 0 && rows < matrix.size() && cols >= 0 && cols < matrix.get(rows).size();
    }
}
