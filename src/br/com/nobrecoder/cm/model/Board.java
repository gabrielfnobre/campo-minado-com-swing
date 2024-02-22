package br.com.nobrecoder.cm.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.xml.bind.NotIdentifiableEvent;

public class Board implements ObserverField{
    private int rows;
    private int columns;
    private int mines;
    private boolean win;

    private final List<Field> fields = new ArrayList<>();
    
    //O tabuleiro também terá ser um subject, assim como o campo, visto que o campo fica assistindo uma
    //interação por parte do usuário para poder notificar o tabuleiro se um campo foi aberto, ou se o
    //usuário ganhou o jogo, o tabuleiro também será um subject para os campos, pois assim que receber
    //a notificação de que qualquer um dos campos quanto a uma explosão ou vitória, ele deverá notificar
    //todos os demais campos, para que eles sejam abertos...
    
    //Abaixo temos uma collection para armazenar todos os campos como observers, note que nesse exemplo
    //ao invés de criar nossa própria interface, estamos usando uma pré-existente do java...
    private final Set<Consumer<Boolean>> observers = new LinkedHashSet<>();

    /**
     * @category
     * Is the constructor method of board of the game. It's in charge for generate the fields,
     * associate the neighbors of the field and draw the mines around of the board.
     */
    public Board(int rows, int columns, int mines){
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        associateNeighbors();
        drawMines();
    }

    public int getRows(){
        return rows;
    }

    public int getColumns(){
        return columns;
    }

    public int getMines(){
        return mines;
    }

    public List<Field> getFields(){
        return fields;
    }
    
    public boolean isWin() {
    	return win;
    }
    
    
    /**@Objetivo
     * Um método genérico que utilizará um consumer para fazer operações consectivas sobre os campos do tabuleiro.
     * Permitindo que qualquer lambda sem retorno possa executada sobre todos os campos do tabuleiro.
     * */
    public void forEachFields(Consumer<Field> function) {
    	fields.forEach(function);
    }

    //Método registrador dos campos...
    public void registerObservers(Consumer<Boolean> observer) {
    	observers.add(observer);
    }
    
    //Método notificador dos campos...
    public void notifyObservers(Boolean resultOfGame) {
    	observers.stream().forEach(o -> o.accept(resultOfGame));
    }
    
    /**
     * @category
     * Draws the mines randomly according to the number of mines passed.
     * */
    private void drawMines() {
        long minesUndermined = 0;
        Predicate<Field> undermined = f -> f.getUndermined();

        do{
            int randon = (int) (Math.random() * fields.size());
            fields.get(randon).toUndermine();
            minesUndermined = fields.stream().filter(undermined).count();
        } while (minesUndermined < mines);
    }

    /**
     * @category
     * Associate the field neighborhood to the field according to the proximity between them.
     * */
    private void associateNeighbors() {
        for (Field f1: fields) {
            for (Field f2: fields) {
                f1.addNeighbors(f2);
            }
        }
    }

    private void generateFields() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
            	Field field = new Field(r, c);
            	field.registerObserver(this);
                fields.add(field);
            }
        }
    }

    public boolean goalAchieved(){
        return (fields.stream().allMatch(f -> f.goalAchieved()));
    }

    public void restart(){
        fields.stream().forEach(f -> f.restart());
        drawMines();
    }

    public void toOpen(int row, int column){
        int rowRefactor = row - 1;
        int columnRefactor = column -1;
        fields.parallelStream()
                .filter(f -> f.getRow() == rowRefactor && f.getColumn() == columnRefactor)
                .findFirst()
                .ifPresent(f -> f.toOpen());
    }
    
    public void toShowAllMines(){
        fields.stream()
        	.filter(f -> f.isUndermined())
        	.filter(f -> !f.isMarked())
       		.forEach(f -> f.setOpen(true));
    }

    public void toMark(int row, int column){
        int rowRefactor = row - 1;
        int columnRefactor = column -1;
        fields.parallelStream()
                .filter(f -> f.getRow() == rowRefactor && f.getColumn() == columnRefactor)
                .findFirst()
                .ifPresent(f -> f.alternateMark());
    }
    
    @Override
    public void eventOccured(Field field, FieldEvent event) {
    	if(event == FieldEvent.EXPLODE) {
    		toShowAllMines();
    		notifyObservers(false);
    	} else if (goalAchieved()){
    		notifyObservers(true);
    	}
    }
}
