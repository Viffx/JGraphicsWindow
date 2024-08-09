package com.adrianlong;

// V2

import com.adrianlong.display.ShapeClasses.Rect;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameOfLife {
    private final Point size;  // Size of the grid
    private boolean[][] grid;
    private boolean[][] bufferGrid;
    private int alive = 0;
    private int[] data = new int[20];
    boolean stop = false;
    public String[] terminal = new String[100];

    // Constructor to initialize the grid
    public GameOfLife(int x, int y, int radii) {
        this.size = new Point(x, y);
        grid = new boolean[x][y];
        bufferGrid = new boolean[x][y];
        initializeGrid(radii);
    }
    public GameOfLife(int x, int y, int radii, String[] terminal) {
        this.size = new Point(x, y);
        grid = new boolean[x][y];
        bufferGrid = new boolean[x][y];
        this.terminal = terminal;
        initializeGrid(radii);
    }

    // Initialize the grid with random values
    private void initializeGrid(int radii) {
        int r = (Math.min(size.x, size.y) / 2) - 200;
        CircleGenerator.generateConcentricCircles(grid, radii, size.x / 2, size.y / 2 - 15);
    }

    // Compute the next generation of the grid
    public List<com.adrianlong.display.ShapeClasses.Shape> step(int cellSize, int gen, int radii) {
        // Declare Code variables
        alive = 0;

        // Multi Threading Variables
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int rowsPerThread = size.x / numThreads;

        // Calculate new board state
        for (int t = 0; t < numThreads; t++) {
            final int startRow = t * rowsPerThread;
            final int endRow = (t == numThreads - 1) ? size.x : startRow + rowsPerThread;

            executor.execute(() -> {
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < size.y; j++) {
                        int liveNeighbors = countLiveNeighbors(i, j);

                        // Apply the rules of the Game of Life
                        boolean state = (grid[i][j] && (liveNeighbors == 2 || liveNeighbors == 3)) || (!grid[i][j] && liveNeighbors == 3);
                        bufferGrid[i][j] = state;
                        if(state) {
                            alive++;
                        }
                    }
                }
            });
        }

        // Await termination of all threads
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Swap the grids
        boolean[][] temp = grid;
        grid = bufferGrid;
        bufferGrid = temp;

        stop = terminate(radii, gen);
        return toDisplay(cellSize);
    }

    private boolean terminate(int radii, int gen) {
        // move each element in data one to the right and add one to the end
        for (int i = 1; i < data.length; i++) {
            data[i-1] = data[i];
        }
        data[data.length -1] = alive;

        int mod = 1000; // Tweak this value
        int min = data[0]; // Initialise min value
        int max = data[0]; // Initialise max value
        for (int num : data) {
            min = Math.min(min,num);
            max = Math.max(max,num);
        } // Determine min and max value
        int dif = max - min; // Determine the difference between the min and max value

        // Do nothing if the game hasn't lasted ten generations
        if (gen < 10) {
            return end(radii,gen,dif,false);
        }

        // If the life in the game dies out end the game
        if (data[data.length - 1] == 0) {
            return end(radii,gen,dif,true);
        }

        // If the difference between the change in life between generations is to low end the game
        return end(radii,gen,dif,dif <= 50);
    }
    // add some functionality to the return values
    // print the output to the terminal
    // print the output to the output file
    // return the tf value
    private boolean end(int radii, int gen,int dif, boolean tf) {
        String outputText = String.format("Radius: %d Gen: %10d Alive: %10d Dif: %d\n",radii,gen,alive,dif);
        updateTerminal(outputText);
        writeOutput(String.format("%d %d %d %d\n",radii,gen,alive,dif));
        if (tf) {
            writeOutput("Terminated\n");
        }
        return tf;
    }
    private void updateTerminal(String text) {
        for (int i = 1; i < terminal.length; i++) {
            terminal[i-1] = terminal[i];
        }
        terminal[terminal.length - 1] = text;
    }
    private static void writeOutput(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\adria\\Documents\\Java Projects\\ALGraphics\\src\\main\\resources\\output.text", true))) {
            writer.write(text);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }


    public List<com.adrianlong.display.ShapeClasses.Shape> toDisplay(int cellSize) {
        List<com.adrianlong.display.ShapeClasses.Shape> shapes = new ArrayList<>(size.x * size.y / 4);
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                if (grid[i][j]) {
                    shapes.add(new Rect.TwoDem(i * cellSize, j * cellSize, cellSize, cellSize, Color.WHITE, true));
                }
            }
        }
        return shapes;
    }

    // Count the number of live neighbors for a cell
    private int countLiveNeighbors(int x, int y) {
        int count = 0;
        int[] offsets = {-1, 0, 1};
        for (int i : offsets) {
            for (int j : offsets) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                int nx = x + i;
                int ny = y + j;
                if (nx >= 0 && nx < size.x && ny >= 0 && ny < size.y && grid[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        //String[] chars = {"⬛","⬜"};
        StringBuilder text = new StringBuilder();
        Point min = new Point(grid.length,grid.length);
        Point max = new Point(0,0);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j]) {
                    min.x = Math.min(min.x,i);
                    min.y = Math.min(min.y,j);
                    max.x = Math.max(max.x,i);
                    max.y = Math.max(max.y,j);
                }
            }
        }
        for (int i = min.x; i < max.x + 1; i++) {
            for (int j = min.y; j < max.y + 1; j++) {
                text.append(grid[i][j] ? "⬜" : "⬛");
            }
            text.append("\n");
        }
        return text.toString();
    }
}

class CircleGenerator {
    // Generates concentric circles with decreasing radius and adds them to a given array
    public static void generateConcentricCircles(boolean[][] array, int maxRadius, int centerX, int centerY) {
        for (int radius = maxRadius; radius > 0; radius -= 2) {
            boolean[][] smallCircle = generateCircle(radius);
            addCircleToArray(array, smallCircle, centerX, centerY);
        }
    }

    // Generates a single circle in a boolean array without adjusting array size
    public static boolean[][] generateCircle(int radius) {
        int diameter = radius * 2 + 1;
        boolean[][] circle = new boolean[diameter][diameter];

        int x = 0;
        int y = radius;
        int d = 3 - 2 * radius;

        while (x <= y) {
            plotPoints(circle, x, y);
            if (d < 0) {
                d = d + (4 * x) + 6;
            } else {
                d = d + 4 * (x - y) + 10;
                y--;
            }
            x++;
        }

        return circle;
    }

    // Plots the symmetric points for a given (x, y) on the circle
    private static void plotPoints(boolean[][] circle, int x, int y) {
        int cx = circle.length / 2;
        int cy = circle[0].length / 2;

        circle[cx + x][cy + y] = true;
        circle[cx + x][cy - y] = true;
        circle[cx - x][cy + y] = true;
        circle[cx - x][cy - y] = true;
        circle[cx + y][cy + x] = true;
        circle[cx + y][cy - x] = true;
        circle[cx - y][cy + x] = true;
        circle[cx - y][cy - x] = true;
    }

    // Adds a smaller circle to a larger array
    private static void addCircleToArray(boolean[][] largeArray, boolean[][] smallCircle, int centerX, int centerY) {
        int smallWidth = smallCircle.length;
        int smallHeight = smallCircle[0].length;
        int offsetX = centerX - smallWidth / 2;
        int offsetY = centerY - smallHeight / 2;

        for (int i = 0; i < smallWidth; i++) {
            for (int j = 0; j < smallHeight; j++) {
                if (smallCircle[i][j]) {
                    int largeX = offsetX + i;
                    int largeY = offsetY + j;

                    if (largeX >= 0 && largeX < largeArray.length && largeY >= 0 && largeY < largeArray[0].length) {
                        largeArray[largeX][largeY] = true;
                    }
                }
            }
        }
    }
}
