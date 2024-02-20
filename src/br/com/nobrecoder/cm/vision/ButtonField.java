package br.com.nobrecoder.cm.vision;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.nobrecoder.cm.model.Field;
import br.com.nobrecoder.cm.model.FieldEvent;
import br.com.nobrecoder.cm.model.ObserverField;

/**@About_Class
 * Essa é a classe que implementa a interface gráfica dos campos do jogo. Todos os campos do jogo são formados por um botões, que ao serem clicados irão executar um dos seguintes comportamentos: Explodir, Marcar, Abrir ou Desmarcar;
 */

@SuppressWarnings("serial")
public class ButtonField extends JButton implements ObserverField, MouseListener { //Fizemos a classe se extender de JButton, o que torna a classe um JButton, facilitando as chamadas de métodos de JButton. Além disso, essa classe implementa a interface ObserverField, o que a força a criar um método que escutará um evento e notificará o Tabuleiro do que ocorreu com aquela campo.
//Note também nós estamos usando a interface "Mouse Listener" para poder ouvir os eventos de mouse, quando os botões do mouse for pressionado...
	
	//Aqui temos a definição das cores que serão usadas:
	private final Color BG_PATTERN = new Color(184, 184, 184); //Para campo fechado
	private final Color BG_MARKED = new Color(8, 179, 247); //Para campo marcado
	private final Color BG_EXPLODED = new Color(189, 66, 68); //Para campo explodido
	private final Color BG_GREEN = new Color(0, 100, 0); //Para campo seguro
	
	private Field field;
	
	public ButtonField(Field field) { //O construtor de ButtonField recebe um campo, que será usado para identificar o botão como pertencente (na verdade como "sendo") um campo.
		this.field = field;
		setBackground(BG_PATTERN); //Definimos uma cor padrã para o campo... 
		setBorder(BorderFactory.createBevelBorder(0)); //Criamos uma borda parecida com a do campo minado real...
		
		addMouseListener(this); //Esse método registra a classe atual na interface do MouseListener, tornando a classe um observer que será notificado quando o evento do mouse acontecer.
		field.registerObserver(this); //Registramos o botão como observador, que ficará responsável por notificar a classe campo quando um evento ocorrer...
	}
	
	
	@Override
	public void eventOccured(Field field, FieldEvent event) { //Aqui temos a implementação do método da interface, esse método possuí um switch que irá executar um método que será chamado de acordo com o evento que tiver ocorrido. Cada método implementa uma interface gráfica diferenciada para o botão, dependendo do resultado.
		switch (event) {
			case OPEN :
				buttonStyleOPEN();
				break;
			case MARK :
				buttonStyleMARK();
				break;
			case EXPLODE :
				buttonStyleEXPLODE();
				break;
			default :
				buttonStylePattern();
				break;
		}
		
	}


	private void buttonStylePattern() {
		// TODO Auto-generated method stub
		
	}


	private void buttonStyleEXPLODE() {
		// TODO Auto-generated method stub
		
	}


	private void buttonStyleMARK() {
		// TODO Auto-generated method stub
		
	}


	private void buttonStyleOPEN() {
		setBackground(BG_PATTERN);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		switch (field.minesInTheNeighborhood()) {
			case 1:
				setForeground(BG_GREEN);
				break;
			case 2:
				setForeground(Color.BLUE);
				break;
			case 3:
				setForeground(Color.YELLOW);
				break;
			case 4:
			case 5:
			case 6:
				setForeground(Color.RED);
				break;
			default:
				setForeground(Color.PINK);
		}
		
		String value = !field.safeNeighborhood() ? field.minesInTheNeighborhood() + "" : "";
		setText(value);
		
	}
	
	
	//==================================================================================
	//MÉTODOS DA INTERFACE MouseListener
	//==================================================================================
	
	@Override
	public void mousePressed(MouseEvent e) { //Esse método identifica quando um botão do mouse é clicado
		if(e.getButton() == 1) { //Valores "1" sempre são usados para identificar o botão principal do mouse, na maioria das vezes o botão "esquerdo"
			field.toOpen();
		} else {
			field.alternateMark();
		}
		
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	
	
}
