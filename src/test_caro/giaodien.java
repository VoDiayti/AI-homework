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
		boolean isXMove = XOButton.isXMove;
		
		displayInConsole();
		// TODO: CALC LOGIC HERE
		point.log();
		if (hasXWon(point, 1)) {
			System.out.println("OK OK");
		}; // 
		int board[][] = getMatrixBoard();
		
		double scoreUser = getScore(board, false, isXMove);
		double scoreAI = getScore(board, true, isXMove);
		
		System.out.println("SCORE USER: " + scoreUser);
		System.out.println("SCORE AI: " + scoreAI);
			
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
	
	
	
	
	public int getScore(int[][] board, boolean forBlack, boolean blacksTurn) {
		
		return evaluateHorizontal(board, forBlack, blacksTurn) +
				evaluateVertical(board, forBlack, blacksTurn) +
				evaluateDiagonal(board, forBlack, blacksTurn);
	}
	
	public int evaluateHorizontal(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
			
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
	
	public  int evaluateVertical(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
		
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
	public  int evaluateDiagonal(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
		
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
	
	public  int getConsecutiveSetScore(int count, int blocks, boolean currentTurn) {
		final int winGuarantee = 1000000;
		if(blocks == 2 && count < 5) return 0;
		switch(count) {
		case 5: {
			return 100000;
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
		return 100000*2;
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
