package application;

public class Puissance4Position extends Position{

	final static public int BLANK = 0;
	final static public int HUMAN = 1;
	final static public int PROGRAM = -1;
	int [] board = new int[42];



	public Puissance4Position() {

	}


	public Puissance4Position( int [] board) {
		this.board=board;
	}
	public int[] getBoard() {
		return board;
	}
	public void setBoard(int[] board) {
		this.board = board;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("[");
		for (int i=0; i<42; i++) {
			sb.append(""+board[i]+",");
		}
		sb.append("]");
		return sb.toString();
	}

}
