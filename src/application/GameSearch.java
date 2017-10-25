package application;
import java.util.Enumeration;
import java.util.Vector;

public abstract class GameSearch {

	public static final boolean DEBUG = false;

	public static boolean PROGRAM = false;
	public static boolean HUMAN = true;
	int playerMove;

	//	    Notes:  PROGRAM false -1,  HUMAN true 1
	//	    by convention : true -> the computer ,false -> the human opponent 

	//	    should return a Boolean true value if the given position evaluates to a draw situation
	public abstract boolean drawnPosition(Position p);

	//	    should return a true value if the input position is won for the indicated player
	public abstract boolean wonPosition(Position p, boolean player);

	//	    returns a position evaluation for a specified board position and player
	public abstract float positionEvaluation(Position p, boolean player);

	public abstract void printPosition(Position p);

	//  returns an array of objects belonging to the class Position
	public abstract Position [] possibleMoves(Position p, boolean player);

	//	    will return a new position object for a speciï¬�ed board position, side to move, and move. 
	public abstract Position makeMove(Position p, boolean player, Move move);

	//	    returns a Boolean true value if the search process has reached a satisfactory depth
	//	    it does not return true unless either side has won the game or the board is full
	public abstract boolean reachedMaxDepth(Position p, int depth);

	//	    returns an object of a class derived from the class Move
	public abstract Move createMove(Position p);




	/*
	 * Search utility methods:
	 */

	protected Vector alphaBeta(int depth, Position p, boolean player) {
		Vector v = alphaBetaHelper(depth, p, player, 1000000.0f, -1000000.0f);
		//System.out.println("^^ v(0): " + v.elementAt(0) + ", v(1): " + v.elementAt(1));
		return v;
	}

	protected Vector alphaBetaHelper(int depth, Position p,
			boolean player, float alpha, float beta) {
		if (GameSearch.DEBUG) System.out.println("alphaBetaHelper("+depth+","+p+","+alpha+","+beta+")");
		if (reachedMaxDepth(p, depth)) {
			Vector v = new Vector(2);
			float value = positionEvaluation(p, player);
			v.addElement(new Float(value));
			v.addElement(null);
			if(GameSearch.DEBUG) {
				System.out.println(" alphaBetaHelper: mx depth at " + depth+
						", value="+value);
			}
			return v;
		}
		Vector best = new Vector();
		Position [] moves = possibleMoves(p, player);
		for (int i=0; i<moves.length; i++) {
			Vector v2 = alphaBetaHelper(depth + 1, moves[i], !player, -beta, -alpha);
			//  if (v2 == null || v2.size() < 1) continue;
			float value = -((Float)v2.elementAt(0)).floatValue();
			if (value > beta) {
				if(GameSearch.DEBUG) System.out.println(" ! ! ! value="+value+", beta="+beta);
				beta = value;
				best = new Vector();
				best.addElement(moves[i]);
				Enumeration enum2 = v2.elements();
				enum2.nextElement(); // skip previous value
				while (enum2.hasMoreElements()) {
					Object o = enum2.nextElement();
					if (o != null) best.addElement(o);
				}
			}
			/**
			 * Use the alpha-beta cutoff test to abort search if we
			 * found a move that proves that the previous move in the
			 * move chain was dubious
			 */
			if (beta >= alpha) {
				break;
			}
		}
		Vector v3 = new Vector();
		v3.addElement(new Float(beta));
		Enumeration enum2 = best.elements();
		while (enum2.hasMoreElements()) {
			v3.addElement(enum2.nextElement());
		}
		return v3;
	}


public void playGame(Position startingPosition, boolean humanPlayFirst) {

}

}
