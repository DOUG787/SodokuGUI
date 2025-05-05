import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGUI extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];

    // Tabuleiro inicial (0 = vazio)
    private int[][] board = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    public SudokuGUI() {
        setTitle("Sudoku");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 20));

                if (board[row][col] != 0) {
                    cell.setText(String.valueOf(board[row][col]));
                    cell.setEditable(false);
                    cell.setBackground(Color.LIGHT_GRAY);
                } else {
                    final int r = row;
                    final int c = col;
                    cell.addKeyListener(new KeyAdapter() {
                        public void keyReleased(KeyEvent e) {
                            String text = cell.getText();
                            if (text.matches("[1-9]")) {
                                int value = Integer.parseInt(text);
                                if (isValidMove(r, c, value)) {
                                    board[r][c] = value;
                                    if (isBoardFull()) {
                                        JOptionPane.showMessageDialog(null, "Parabéns! Sudoku completo.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Movimento inválido!");
                                    cell.setText("");
                                }
                            } else {
                                cell.setText("");
                            }
                        }
                    });
                }

                cells[row][col] = cell;
                add(cell);
            }
        }

        setVisible(true);
    }

    private boolean isValidMove(int row, int col, int value) {
        // Checar linha e coluna
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == value || board[i][col] == value) return false;
        }

        // Checar subgrade 3x3
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == value) return false;
            }
        }

        return true;
    }

    private boolean isBoardFull() {
        for (int[] row : board)
            for (int cell : row)
                if (cell == 0) return false;
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}
