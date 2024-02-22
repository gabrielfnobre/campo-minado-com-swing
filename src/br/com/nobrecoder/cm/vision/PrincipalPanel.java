package br.com.nobrecoder.cm.vision;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.nobrecoder.cm.model.Board;

/**@Objetivo
 * Essa classe é usada para gerar um paneil principal que servirá como container para toda a janela da nossa aplicação...
 * */
@SuppressWarnings("serial")
public class PrincipalPanel extends JPanel{ //Essa classe extende de JPanel...
	
	//Método Constructor
	public PrincipalPanel(Board board) { //Recebe um tabuleiro como parâmetro visto que precisaremos das informações de colunas, linhas e bombas que um jogo precisará ter, e essas informações estão todas no tabuleiro.
		setLayout(new GridLayout(board.getRows(), board.getColumns())); //As linhas e colunas do tabuleiro serão usadas para gerar um gri
		
		board.forEachFields(c -> add(new ButtonField(c))); //Nós criamos na classe "board" um método que recebe um consumer que irá iterar sobre cada campo do tabuleiro adicionando um novo botão ao painel principal por através do método "add" de JPainel...
		
		board.registerObservers(e -> {
			if(board.goalAchieved()) {				
				JOptionPane.showMessageDialog(this, "YOU WIN!!! 😃");
			} else {				
				JOptionPane.showMessageDialog(this, "YOU LOSE 😞");
			}
		});
		
	}
}
