package br.com.nobrecoder.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int row;
    private final int column;

    private boolean open = false;
    private boolean marked = false;
    private boolean undermined = false;

    private List<Field> neighbors = new ArrayList<>();

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
