package br.com.nobrecoder.cm.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Field {

    private final int row;
    private final int column;

    private boolean open = false;
    private boolean marked = false;
    private boolean undermined = false;

    private List<Field> neighbors = new ArrayList<>();
    
    //================================== Padrão Usado para Implementação do Swing =================================
    //Precisamos implementar um padrão observer para trabalhar a interface gráfica, pois como os demais campos
    //precisarão ser informados caso um campo exploda, seja marcado ou seja seguro, há a necessidade de armazenar
    //os demais campos interessados numa lista de observers que serão notificados...
    
    //================================== Por que usar o Set para armazenar os observers ===========================
    //Optamos pelo "Set" com "LinkedHashSet" pelo fato do LinkedHashSet não aceitar duplicação de campos, pois 
    //não vamos querer carregar o nosso código informando um campo mais de uma vez, e além disso, o LinkedHashSet
    //mantém a ordenação dos campos.
    
    //================================== ObserverField? ===========================================================
    //São campos que implementam a interface que contém o método abstrato que deverá ser chamado assim que o evento
    //acontecer...
    private Set<ObserverField> observers = new LinkedHashSet<>();

    public Field(int row, int column){
        this.row = row;
        this.column = column;
    }
    
    //Método para registrar os observadores...
    public void registerObserver(ObserverField obs) {
    	observers.add(obs);
    }
    
    //Método para notificar os observadores, passando o objeto onde o evento aconteceu e o evento que aconteceu...
    private void notifyObservers(FieldEvent event) {
    	observers.stream().forEach(o -> o.eventOccured(this, event));
    }

    public boolean addNeighbors(Field field){
        boolean diferentRow = row != field.row;
        boolean diferentColumn = column != field.column;
        boolean diagonal = diferentColumn && diferentRow;

        int deltaRow = Math.abs(row - field.row);
        int deltaColumn = Math.abs(column - field.column);
        int generalDelta = deltaRow + deltaColumn;

        if(generalDelta == 1 && !diagonal){
            neighbors.add(field);
            return true;
        } else if(generalDelta == 2 && diagonal){
            neighbors.add(field);
            return true;
        } else {
            return false;
        }
    }

    public boolean isMarked(){
        return marked;
    }
    
    public boolean isUndermined() {
    	return undermined;
    }

    public void alternateMark(){
        if(!open){
            marked = !marked;
            
            if(marked) {            	
            	notifyObservers(FieldEvent.MARK);
            } else {
            	notifyObservers(FieldEvent.MARKOFF);
            }
        }
    }

    public void setOpen(boolean open){
        this.open = open;
        
        if(open) {        	
        	notifyObservers(FieldEvent.OPEN);
        }
    }
    
    public boolean isOpen(){
        return open;
    }

    public boolean toOpen(){
        if(!open && !marked){
        	setOpen(true);
            if(undermined){
            	notifyObservers(FieldEvent.EXPLODE);
                return true;
            }
            //open all neighborhood no have a bomb
            if(safeNeighborhood()){
                neighbors.forEach(n -> n.toOpen());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Objective:
     * Compare if no one of neighbors has one mine
     * */
    public boolean safeNeighborhood(){
        return neighbors.stream().noneMatch(n -> n.undermined);
    }

    public boolean getUndermined(){
        return undermined;
    }

    public void toUndermine(){
        if(!undermined){
            undermined = true;
        }
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public boolean goalAchieved(){
        boolean discovered = !undermined && open;
        boolean protectedIs = undermined && marked;
        return discovered || protectedIs;
    }

    public int minesInTheNeighborhood(){
        return (int) neighbors.stream().filter(n -> n.undermined).count();
    }

    public void restart(){
        open = false;
        undermined = false;
        marked = false;
        notifyObservers(FieldEvent.RESTART);
    }

}
