package br.com.nobrecoder.cm.vision;

import br.com.nobrecoder.cm.model.Board;

public class Test {
	public static void main(String[] args) {
		Board board = new Board(3, 3, 9);
		board.toMark(1, 1);
		board.toMark(1, 2);
		board.toMark(1, 3);
		board.toMark(2, 1);
		board.toMark(2, 2);
		board.toMark(2, 3);
		board.toMark(3, 1);
		board.toMark(3, 2);
		board.toMark(3, 3);
	}
}
