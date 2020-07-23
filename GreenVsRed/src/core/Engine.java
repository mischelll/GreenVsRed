package core;

import util.TaskHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Engine implements Runnable {

  private BufferedReader bufferedReader;
  private TaskHelper tasker;

    public Engine() {
        this.tasker = new TaskHelper();
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try {
            //Main matrix
            List<List<String>> matrix = new ArrayList<>();
            System.out.println("Please enter the dimensions of the matrix: ");
            //Getting the dimensions of the main matrix
            String[] dimensions = this.bufferedReader.readLine().split(",\\s+");
            int x = Integer.parseInt(dimensions[0]); // width
            int y = Integer.parseInt(dimensions[1]); //height

            System.out.println("Enter the elements at each row of the matrix: ");
            //Filling the main matrix with the user-provided elements
            for (int row = 0; row < x ; row++) {
                matrix.add(new ArrayList<>());
                String[] input = this.bufferedReader.readLine().split("");
                for (int col = 0; col < y ; col++) {
                    matrix.get(row).add(input[col]);
                }
            }
            System.out.println("Enter the specified element index and the turns of the game: ");
            //Getting the turns of the game and which is the specified element to watch
            String[] elementTurns = this.bufferedReader.readLine().split(",\\s+");
            int x1 = Integer.parseInt(elementTurns[0]);
            int y1 = Integer.parseInt(elementTurns[1]);
            int turns = Integer.parseInt(elementTurns[2]);


            //Check the TaskHelper class
            Integer integer = this.tasker.solveTask(matrix, x1, y1, turns, x, y);
            //Prints the result
            System.out.println("The result is: " + integer);


        } catch (Exception e) {
            System.out.println("Error occured " +e.getMessage());
        }

    }
}
