package br.com.nobrecoder.cm.vision;

import br.com.nobrecoder.cm.model.Board;

public class Test {
	public static void main(String[] args) {
		Board board = new Board(3, 3, 1);
		board.toOpen(2, 2);
	}
}
