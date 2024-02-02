package br.com.nobrecoder.cm.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

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
    
    //================================== Por que usar o BiConsumer ================================================
    //Estamos usando também uma FunctionalInterface do tipo "BiConsumer" para executar um lambda notificador sobre
    //todos os observers...
    private Set<BiConsumer<Field, BoardEvent>> observers = new LinkedHashSet<>();

    public Field(int row, int column){
        this.row = row;
        this.column = column;
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

    public void alternateMark(){
        if(!open){
            marked = !marked;
        }
    }

    public void setOpen(boolean open){
        this.open = open;
    }
    public boolean isOpen(){
        return open;
    }

    public boolean toOpen(){
        if(!open && !marked){
            open = true;
            if(undermined){
                //FIXME:Change to GUI version!!
            	//throw new ExplosionException();
            }
            if(safeNeighborhood()){
                neighbors.forEach(n -> n.toOpen());
            }
            return true;
        } else {
            return false;
        }
    }

    boolean safeNeighborhood(){
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

    public long minesInTheNeighborhood(){
        return neighbors.stream().filter(n -> n.undermined).count();
    }

    public void restart(){
        open = false;
        undermined = false;
        marked = false;
    }

}
