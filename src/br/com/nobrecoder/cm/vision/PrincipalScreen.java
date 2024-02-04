package br.com.nobrecoder.cm.vision;

import javax.swing.JFrame;

import br.com.nobrecoder.cm.model.Board;

@SuppressWarnings("serial")
public class PrincipalScreen extends JFrame{
	public PrincipalScreen(int width, int height) {
		Board board = new Board(16, 30, 50);
		PrincipalPanel ppanel = new PrincipalPanel(board);
		
		setTitle("Campo Minado");
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		add(ppanel);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {	
		new PrincipalScreen(690, 438);
	}
}
