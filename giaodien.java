package test_caro;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
				Buttons[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						handleClickButton(Buttons[point.x][point.y]);
						
					}
				});
				p.add(Buttons[i][j]);
				availablesPoint.add(point);
			}
		}
		
		frame.add(p);
		frame.setVisible(true);
	}
	
	private void handleClickButton(XOButton button) {
		Point point = button.point;
		boolean isXMove = XOButton.isXMove;
		// Note: Luc nay chua nhan XO tren ma tran button tren o dang click!
		
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
	
	private boolean hasXWon() {
		
		return false;
	}
	
	



}
