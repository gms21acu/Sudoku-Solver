import java.util.*;

public class SudokuSolver {
     
    
    private static final int s = 9;
    private static final int sGrid = 3;
    
    public String solve(String state) {
        int[][] grid = pState(state);
        if (ac3(grid) && dfs(grid)) {
            return convertToString(grid);
        }
        return convertToString(grid); // Return updated grid even if not fully solved
    }
    
    private int[][] pState(String state) {
        int[][] grid = new int[s][s];
        for (int i = 0; i < state.length(); i++) {
            char c = state.charAt(i);
            grid[i / s][i % s] = (c == '_') ? 0 : Character.getNumericValue(c);
        }
        return grid;
    }
    
    private boolean ac3(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                if (grid[i][j] == 0) {
                    queue.add(new int[]{i, j});
                }
            }
        }
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0], col = cell[1];
            Set<Integer> domain = getDomain(grid, row, col);
            if (domain.size() == 1) {
                grid[row][col] = domain.iterator().next();
            }
        }
        return true;
    }
    
    private Set<Integer> getDomain(int[][] grid, int row, int col) {
        Set<Integer> domain = new HashSet<>();
        for (int i = 1; i <= s; i++) domain.add(i);
        
        for (int i = 0; i < s; i++) {
            domain.remove(grid[row][i]);
            domain.remove(grid[i][col]);
        }
        
        int startRow = (row / sGrid) * sGrid, startCol = (col / sGrid) * sGrid;
        for (int i = 0; i < sGrid; i++) {
            for (int j = 0; j < sGrid; j++) {
                domain.remove(grid[startRow + i][startCol + j]);
            }
        }
        return domain;
    }
    
    private boolean dfs(int[][] grid) {
        int[] empty = findEmpty(grid);
        if (empty == null) return true;
        
        int row = empty[0], col = empty[1];
        for (int num = 1; num <= s; num++) {
            if (isValid(grid, row, col, num)) {
                grid[row][col] = num;
                if (dfs(grid)) return true;
                grid[row][col] = 0;
            }
        }
        return false;
    }
    
    private int[] findEmpty(int[][] grid) {
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                if (grid[i][j] == 0) return new int[]{i, j};
            }
        }
        return null;
    }
    
    private boolean isValid(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < s; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false;
        }
        
        int startRow = (row / sGrid) * sGrid, startCol = (col / sGrid) * sGrid;
        for (int i = 0; i < sGrid; i++) {
            for (int j = 0; j < sGrid; j++) {
                if (grid[startRow + i][startCol + j] == num) return false;
            }
        }
        return true;
    }
    
    private String convertToString(int[][] grid) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                result.append(grid[i][j]);
            }
        }
        return result.toString();
    }
}
