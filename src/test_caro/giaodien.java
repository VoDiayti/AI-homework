package test_caro;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import test_caro.CaroActionListener;

public class giaodien {

	private JFrame frame;
	private int row = 20;
	private int col = 20;
	public XOButton[][] Buttons  = new XOButton[col][row];
	private ArrayList<Point> availablesPoint = new ArrayList<Point>();
	private static final int winScore = 100000000;
	
	
	JPanel p = new JPanel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					giaodien window = new giaodien();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public giaodien() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.setLayout(new GridLayout(col,row));
		for (int i = 0; i < Buttons.length; i++) {
			for (int j = 0; j < Buttons.length; j++) {
				Point point = new Point(i, j);
				Buttons[i][j] = new XOButton(i, j);
				MouseListener mouseEvent = new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						handleClickButton(point);
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				};
				Buttons[i][j].addMouseListener(mouseEvent);
				
				p.add(Buttons[i][j]);
				availablesPoint.add(point);
			}
		}
		
		frame.add(p);
		frame.setVisible(true);
	}
	
	
	
	private void handleClickButton(Point point) {
		
		// TODO: CALC LOGIC HERE
		point.log();
		if (hasXWon(point, 2)) {
			System.out.println("OK OK");
		};
		Buttons[point.x][point.y].setState(true);
		int board[][] = getMatrixBoard();
		
		ArrayList<int[]> board2 = generateMoves(board);
		
		
		Object[] bestMove = minimaxSearchAB(4, board, true, -1, 100000000);
		
		int nextMoveX = 0 , nextMoveY = 0;
		
		if (bestMove[1] != null && bestMove[2] != null) {
			
			nextMoveX = (Integer)bestMove[1];
			nextMoveY = (Integer)bestMove[2];
		} else {
			System.out.println("LOI IIIIII");
		}
		Buttons[nextMoveX][nextMoveY].setState(false);
		displayInConsole();
	
	}
	
	
	
	
	private void displayInConsole() {
		for (int i = 0; i < Buttons.length; i++) {
			for (int j = 0; j < Buttons.length; j++) {
				XOButton button = Buttons[i][j];
				
				System.out.print(button.value + " ");
			}
			System.out.println();
		}
	}
	private void printBoard(int[][] board) {
		int lenght = board.length;
		System.out.println();
		for (int i = 0; i < lenght; i++)  {
			for (int j = 0; j < lenght; j++) {
				System.out.print(String.format("%d ", board[i][j]));
			}
			System.out.println();
		}
			
		
	}
	private void printMoves(ArrayList<int[]> moves) {
		System.out.println();
		for (int[] move: moves) {
			System.out.println("( " + move[0] + ", " + move[1] + ")");
		}
		System.out.println();
	}
	
	public int[][] playNextMove(int[][] board, int[] move, boolean isUserTurn) {
		int i = move[0], j = move[1];
		int [][] newBoard = new int[row][col];
		for (int h = 0; h < row; h++) {
			for (int k = 0; k < col; k++) {
				newBoard[h][k] = board[h][k];
			}
		}
		newBoard[i][j] = isUserTurn ? 2 : 1;
		return newBoard;
	}
	
	public Object[] minimaxSearchAB(int depth, int[][] board, boolean max, double alpha, double beta) {
		if(depth == 0) {
			Object[] x = {evaluateBoardForWhite(board, !max), null, null};
			return x;
		}
		
		
		ArrayList<int[]> allPossibleMoves = generateMoves(board);
		
		if(allPossibleMoves.size() == 0) {
			
			Object[] x = {evaluateBoardForWhite(board, !max), null, null};
			
			return x;
		}
		
		Object[] bestMove = new Object[3];
		
		
		if(max) {
			bestMove[0] = -1.0;
			// Iterate for all possible moves that can be made.
			for(int[] move : allPossibleMoves) {
				// Create a temporary board that is equivalent to the current board
				int[][] dummyBoard = playNextMove(board, move, false);
				
				// Call the minimax function for the next depth, to look for a minimum score.
				Object[] tempMove = minimaxSearchAB(depth-1, dummyBoard, !max, alpha, beta);
				
				// Updating alpha
				if((Double)(tempMove[0]) > alpha) {
					alpha = (Double)(tempMove[0]);
				}
				// Pruning with beta
				if((Double)(tempMove[0]) >= beta) {
					return tempMove;
				}
				if((Double)tempMove[0] > (Double)bestMove[0]) {
					bestMove = tempMove;
					bestMove[1] = move[0];
					bestMove[2] = move[1];
				}
			}
			
		}
		else {
			bestMove[0] = 100000000.0;
			bestMove[1] = allPossibleMoves.get(0)[0];
			bestMove[2] = allPossibleMoves.get(0)[1];
			for(int[] move : allPossibleMoves) {
				int[][] dummyBoard = playNextMove(board, move, true);
				
				Object[] tempMove = minimaxSearchAB(depth-1, dummyBoard, !max, alpha, beta);
				
				// Updating beta
				if(((Double)tempMove[0]) < beta) {
					beta = (Double)(tempMove[0]);
				}
				// Pruning with alpha
				if((Double)(tempMove[0]) <= alpha) {
					return tempMove;
				}
				if((Double)tempMove[0] < (Double)bestMove[0]) {
					bestMove = tempMove;
					bestMove[1] = move[0];
					bestMove[2] = move[1];
				}
			}
		}
		return bestMove;
	}
	
	
	public double evaluateBoardForWhite(int[][] board, boolean userTurn) {
		
		
		double blackScore = getScore(board, true, userTurn);
		double whiteScore = getScore(board, false, userTurn);
		
		if(blackScore == 0) blackScore = 1.0;
		
		return whiteScore / blackScore;
		
	}
	
	
	
	public ArrayList<int[]> generateMoves(int[][] boardMatrix) {
		ArrayList<int[]> moveList = new ArrayList<int[]>();
		
		int boardSize = boardMatrix.length;
		
		
		// Tìm những tất cả những ô trống nhưng có đánh XO liền kề
		for(int i=0; i<boardSize; i++) {
			for(int j=0; j<boardSize; j++) {
				
				if(boardMatrix[i][j] > 0) continue;
				
				if(i > 0) {
					if(j > 0) {
						if(boardMatrix[i-1][j-1] > 0 ||
						   boardMatrix[i][j-1] > 0) {
							int[] move = {i,j};
							moveList.add(move);
							continue;
						}
					}
					if(j < boardSize-1) {
						if(boardMatrix[i-1][j+1] > 0 ||
						   boardMatrix[i][j+1] > 0) {
							int[] move = {i,j};
							moveList.add(move);
							continue;
						}
					}
					if(boardMatrix[i-1][j] > 0) {
						int[] move = {i,j};
						moveList.add(move);
						continue;
					}
				}
				if( i < boardSize-1) {
					if(j > 0) {
						if(boardMatrix[i+1][j-1] > 0 ||
						   boardMatrix[i][j-1] > 0) {
							int[] move = {i,j};
							moveList.add(move);
							continue;
						}
					}
					if(j < boardSize-1) {
						if(boardMatrix[i+1][j+1] > 0 ||
						   boardMatrix[i][j+1] > 0) {
							int[] move = {i,j};
							moveList.add(move);
							continue;
						}
					}
					if(boardMatrix[i+1][j] > 0) {
						int[] move = {i,j};
						moveList.add(move);
						continue;
					}
				}
				
			}
		}
		return moveList;
		
	}
	
	
	
	
	public int getScore(int[][] board, boolean forBlack, boolean blacksTurn) {
		
		return evaluateHorizontal(board, forBlack, blacksTurn) +
				evaluateVertical(board, forBlack, blacksTurn) +
				evaluateDiagonal(board, forBlack, blacksTurn);
	}
	
public static int evaluateHorizontal(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
		
		int consecutive = 0;
		int blocks = 2;
		int score = 0;
		
		for(int i=0; i<boardMatrix.length; i++) {
			for(int j=0; j<boardMatrix[0].length; j++) {
				if(boardMatrix[i][j] == (forBlack ? 2 : 1)) {
					consecutive++;
				}
				else if(boardMatrix[i][j] == 0) {
					if(consecutive > 0) {
						blocks--;
						score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
						consecutive = 0;
						blocks = 1;
					}
					else {
						blocks = 1;
					}
				}
				else if(consecutive > 0) {
					score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
					consecutive = 0;
					blocks = 2;
				}
				else {
					blocks = 2;
				}
			}
			if(consecutive > 0) {
				score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
				
			}
			consecutive = 0;
			blocks = 2;
			
		}
		return score;
	}
	
	public static  int evaluateVertical(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
		
		int consecutive = 0;
		int blocks = 2;
		int score = 0;
		
		for(int j=0; j<boardMatrix[0].length; j++) {
			for(int i=0; i<boardMatrix.length; i++) {
				if(boardMatrix[i][j] == (forBlack ? 2 : 1)) {
					consecutive++;
				}
				else if(boardMatrix[i][j] == 0) {
					if(consecutive > 0) {
						blocks--;
						score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
						consecutive = 0;
						blocks = 1;
					}
					else {
						blocks = 1;
					}
				}
				else if(consecutive > 0) {
					score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
					consecutive = 0;
					blocks = 2;
				}
				else {
					blocks = 2;
				}
			}
			if(consecutive > 0) {
				score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
				
			}
			consecutive = 0;
			blocks = 2;
			
		}
		return score;
	}
	public static  int evaluateDiagonal(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
		
		int consecutive = 0;
		int blocks = 2;
		int score = 0;
		// From bottom-left to top-right diagonally
		for (int k = 0; k <= 2 * (boardMatrix.length - 1); k++) {
		    int iStart = Math.max(0, k - boardMatrix.length + 1);
		    int iEnd = Math.min(boardMatrix.length - 1, k);
		    for (int i = iStart; i <= iEnd; ++i) {
		        int j = k - i;
		        
		        if(boardMatrix[i][j] == (forBlack ? 2 : 1)) {
					consecutive++;
				}
				else if(boardMatrix[i][j] == 0) {
					if(consecutive > 0) {
						blocks--;
						score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
						consecutive = 0;
						blocks = 1;
					}
					else {
						blocks = 1;
					}
				}
				else if(consecutive > 0) {
					score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
					consecutive = 0;
					blocks = 2;
				}
				else {
					blocks = 2;
				}
		        
		    }
		    if(consecutive > 0) {
				score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
				
			}
			consecutive = 0;
			blocks = 2;
		}
		// From top-left to bottom-right diagonally
		for (int k = 1-boardMatrix.length; k < boardMatrix.length; k++) {
		    int iStart = Math.max(0, k);
		    int iEnd = Math.min(boardMatrix.length + k - 1, boardMatrix.length-1);
		    for (int i = iStart; i <= iEnd; ++i) {
		        int j = i - k;
		        
		        if(boardMatrix[i][j] == (forBlack ? 2 : 1)) {
					consecutive++;
				}
				else if(boardMatrix[i][j] == 0) {
					if(consecutive > 0) {
						blocks--;
						score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
						consecutive = 0;
						blocks = 1;
					}
					else {
						blocks = 1;
					}
				}
				else if(consecutive > 0) {
					score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
					consecutive = 0;
					blocks = 2;
				}
				else {
					blocks = 2;
				}
		        
		    }
		    if(consecutive > 0) {
				score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn);
				
			}
			consecutive = 0;
			blocks = 2;
		}
		return score;
	}
	public static  int getConsecutiveSetScore(int count, int blocks, boolean currentTurn) {
		final int winGuarantee = 1000000;
		if(blocks == 2 && count < 5) return 0;
		switch(count) {
		case 5: {
			return winScore;
		}
		case 4: {
			if(currentTurn) return winGuarantee;
			else {
				if(blocks == 0) return winGuarantee/4;
				else return 200;
			}
		}
		case 3: {
			if(blocks == 0) {
				if(currentTurn) return 50000;
				else return 200;
			}
			else {
				if(currentTurn) return 10;
				else return 5;
			}
		}
		case 2: {
			if(blocks == 0) {
				if(currentTurn) return 7;
				else return 5;
			}
			else {
				return 3;
			}
		}
		case 1: {
			return 1;
		}
		}
		return winScore*2;
	}
	
	// check 1 or 2 won
	private boolean hasXWon(Point point, int turnValue) {
		int nextTurn = turnValue == 1 ? 2 : 1;
		
		// chỉ check ma trận bán kinh 5 tính từ điểm trung tâm
		int x, y;
		
		// check đường ngan
		int startXCounter = 0;
		x = point.x;
		
		
		for (y = point.findStartXPoint().x; y <= point.findEndXPoint().x; y++) {
			XOButton button = Buttons[x][y];
			
			int valueAtButton = button.value;
			
			if (valueAtButton == turnValue) {
				startXCounter++;
			} else {
				startXCounter = 0;
			}
			
			if (startXCounter == 5) {
				return true;
			}
		}
		// check dường dọc
		int startYCounter = 0;
		y = point.y;
		for (x = point.findStartYPoint().y; x <= point.findEndYPoint().y; x++) {
			XOButton button = Buttons[x][y];
			
			int valueAtButton = button.value;
			if (valueAtButton == turnValue) {
				startYCounter++;
			} else {
				startYCounter = 0;
			}
			
			if (startYCounter == 5) {
				return true;
			}
		}
		// check đường chéo trai sang phai
		x = point.findLeftTopPoint().x;
		y = point.findLeftTopPoint().y;
		int diagonalCounter = 0;
		for (; x <= point.findRightBottomPoint().x && y <= point.findRightBottomPoint().y; x++, y++) {
			XOButton button = Buttons[x][y];
			int valueAtButton = button.value;
			if (valueAtButton == turnValue) {
				diagonalCounter++;
			} else {
				diagonalCounter = 0;
			}
			
			if (diagonalCounter == 5) {
				return true;
			}
		}
		// check đường chéo phải sang trái
		x = point.findRightTopPoint().x;
		y = point.findRightTopPoint().y;
		diagonalCounter = 0;
		
		for (; x >= point.findLeftBottomPoint().x && y <= point.findLeftBottomPoint().y; x--, y++) {
			XOButton button = Buttons[x][y];
			int valueAtButton = button.value;
			if (valueAtButton == turnValue) {
				diagonalCounter++;
			} else {
				diagonalCounter = 0;
			}
			
			if (diagonalCounter == 5) {
				return true;
			}
		}
		
		
		return false;
	}
	public int[][] getMatrixBoard() {
		int matrix[][] = new int[row][col];
		for (int i = 0; i < Buttons.length; i++) {
			for (int j = 0; j < Buttons.length; j++) {
				int value = Buttons[i][j].value;
				matrix[i][j] = value;
			}
		}
		
		return matrix;
	}
	
	
}
