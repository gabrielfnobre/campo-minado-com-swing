package br.com.nobrecoder.cm.vision;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.nobrecoder.cm.model.Field;
import br.com.nobrecoder.cm.model.FieldEvent;
import br.com.nobrecoder.cm.model.ObserverField;

/**@About_Class
 * Essa é a classe que implementa a interface gráfica dos campos do jogo. Todos os campos do jogo são formados por um botões, que ao serem clicados irão executar um dos seguintes comportamentos: Explodir, Marcar, Abrir ou Desmarcar;
 */

@SuppressWarnings("serial")
public class ButtonField extends JButton implements ObserverField { //Fizemos a classe se extender de JButton, o que torna a classe um JButton, facilitando as chamadas de métodos de JButton. Além disso, essa classe implementa a interface ObserverField, o que a força a criar um método que escutará um evento e notificará o Tabuleiro do que ocorreu com aquela campo.
	
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
		// TODO Auto-generated method stub
		
	}
	
}
