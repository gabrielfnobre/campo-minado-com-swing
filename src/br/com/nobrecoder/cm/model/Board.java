package br.com.nobrecoder.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board implements ObserverField{
    private int rows;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

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

    private void drawMines() {
        long minesUndermined = 0;
        Predicate<Field> undermined = f -> f.getUndermined();

        do{
            int randon = (int) (Math.random() * fields.size());
            fields.get(randon).toUndermine();
            minesUndermined = fields.stream().filter(undermined).count();
        } while (minesUndermined < mines);
    }

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
        try{
        	fields.parallelStream()
                .filter(f -> f.getRow() == rowRefactor && f.getColumn() == columnRefactor)
                .findFirst()
                .ifPresent(f -> f.toOpen());
        } catch (Exception e) {
        	fields.forEach(f -> f.setOpen(true));
        }
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
    		System.out.println("Explode!!!");
    	}
    }
}
