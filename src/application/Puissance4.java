package application;

import java.util.ArrayList;

// class that extends the GameSearch class to apply it to the Connect Four game (puissance4 in french)
public class Puissance4 extends GameSearch {

	public int depthBae;
	
	//	    should return a Boolean true value if the given position evaluates to a draw situation
	public boolean drawnPosition(Position p) {
		boolean ret = true;
		Puissance4Position pos = (Puissance4Position)p;
		for (int i=0; i<42; i++) {
			if (pos.board[i] == Puissance4Position.BLANK){
				ret = false;
				break;
			}
		}
		return ret;
	}



	//	    should return a true value if the input position is won for the indicated player
	public boolean wonPosition(Position p, boolean player) {
		boolean ret = false;
		Puissance4Position pos = (Puissance4Position)p;
		for (int i = 0; i < 42; i++) {
			if(i+3 < (i/7)*7 + 7 ){
				if (winCheck(i,i+1,i+2,i+3, player, pos)) ret = true;
			} 
			if((i/7)+(3)<=5){
				if (winCheck(i,i+7,i+(7*2),i+(7*3), player, pos)) ret = true;
			} 
			if((i/7)+(3)<=5 && i+3 < (i/7)*7 + 7 ){
				if (winCheck(i,i+1+7,i+2+(7*2),i+3+(7*3), player, pos)) ret = true;
			}
			if((i/7)+(3)<=5 && i-3 >= (i/7)*7 ){
				int a1=i-1+7,a2=i-2+(7*2),a3=i-3+(7*3);
				if (winCheck(i,i-1+7,i-2+(7*2),i-3+(7*3), player, pos)){ ret = true;}
			}
		}
		return ret;
	}

	private boolean winCheck(int i1, int i2, int i3,int i4,
			boolean player, Puissance4Position pos) {
		int b = 0;
		if (player) b = Puissance4Position.HUMAN;
		else        b = Puissance4Position.PROGRAM;
		if (pos.board[i1] == b &&
				pos.board[i2] == b &&
				pos.board[i3] == b &&
				pos.board[i4] == b) return true;
		return false;
	}


	//	    returns a position evaluation for a specified board position and player
	public float positionEvaluation(Position p, boolean player) {
		int count = 0;
		Puissance4Position pos = (Puissance4Position)p;
		for (int i=0; i<42; i++) {
			if (pos.board[i] == 0) count++;
		}
		count = 10 - count;
		// prefer the center square:
		float base = 1.0f;
		if (pos.board[4] == Puissance4Position.HUMAN &&
				player) {
			base += 0.4f;
		}
		if (pos.board[4] == Puissance4Position.PROGRAM &&
				!player) {
			base -= 0.4f;
		}
		float ret = (base - 1.0f);
		if (wonPosition(p, player))  {
			return base + (1.0f / count);
		}
		if (wonPosition(p, !player))  {
			return -(base + (1.0f / count));
		}
		return ret;
	}

	public void printPosition(Position p) {
		Puissance4Position pos = (Puissance4Position)p;
		int count = 0;
		for (int row=0; row<6; row++) {
			System.out.println();
			for (int col=0; col<7; col++) {
				if (pos.board[count] == Puissance4Position.HUMAN) {
					System.out.print("H");
				} else if (pos.board[count] == Puissance4Position.PROGRAM) {
					System.out.print("P");
				} else {
					System.out.print("o");
				}
				count++;
			}
		}
	}


	//  returns an array of objects belonging to the class Puissance4Position
	public Position [] possibleMoves(Position p, boolean player) {
		if (GameSearch.DEBUG) System.out.println("posibleMoves("+p+","+player+")");
		Puissance4Position pos = (Puissance4Position)p;
		int count = 0,i;
		ArrayList<Integer> possibles=new ArrayList<Integer>();
		for (int j=0; j<7; j++) {

			if(pos.board[j+7*5]==0){i=j+7*5; count++;possibles.add(i);}
			else if(pos.board[j+7*4]==0){i=j+7*4; count++;possibles.add(i);}
			else if(pos.board[j+7*3]==0){i=j+7*3; count++;possibles.add(i);}
			else if(pos.board[j+7*2]==0){i=j+7*2; count++;possibles.add(i);}
			else if(pos.board[j+7]==0){i=j+7; count++;possibles.add(i);}
			else if(pos.board[j]==0){i=j; count++;possibles.add(i);}
		}
		if (count == 0) return null;
		Position [] ret = new Position[count];
		count = 0;
		for (int k : possibles) {
			Puissance4Position pos2 = new  Puissance4Position();
			for (int j=0; j<42; j++) pos2.board[j] = pos.board[j];
			if (player) pos2.board[k] = 1; else pos2.board[k] = -1;
			ret[count++] = pos2;
			if (GameSearch.DEBUG) System.out.println("    "+pos2);
		}
		return ret;
	}

	//	    will return a new position object for a speciï¬�ed board position, side to move, and move. 
	public Position makeMove(Position p, boolean player, Move move) {
		Puissance4Position pos = (Puissance4Position) p ;
		Puissance4Move m = (Puissance4Move) move;
		
		int whichPlayer;
		if(player){
			whichPlayer = 1;
		}else{
			whichPlayer = -1;
		}
		pos.board[m.moveIndex] = whichPlayer;

		return pos;
	}


	//	    returns a Boolean true value if the search process has reached a satisfactory depth
	//	    it does not return true unless either side has won the game or the board is full
	public boolean reachedMaxDepth(Position p, int depth) {
		boolean ret = false;
		if(depth>=depthBae) ret = true;
		// else 
		if (wonPosition(p, false)) ret = true;
		else if (wonPosition(p, true))  ret = true;
		else if (drawnPosition(p))      ret = true;
		if (GameSearch.DEBUG) {
			System.out.println("reachedMaxDepth: pos=" + p.toString() + ", depth="+depth
					+", ret=" + ret);
		}
		return ret;
	}

	//	    returns an object of a class derived from the class Move
	public Move createMove(Position p,int col) {
		if (GameSearch.DEBUG) System.out.println("Enter blank square index [0,8]:");
		int i = 0,posi;
		Puissance4Position pos = (Puissance4Position)p;
		if(col==-1){
			while(true){
				try {
					if(pos.board[i+7*5]==0){i=i+7*5; break;}
					else if(pos.board[i+7*4]==0){i=i+7*4; break;}
					else if(pos.board[i+7*3]==0){i=i+7*3; break;}
					else if(pos.board[i+7*2]==0){i=i+7*2; break;}
					else if(pos.board[i+7]==0){i=i+7; break;}
					else if(pos.board[i]==0){ break;}
					System.out.println("the position is full!!!!!\n choose column(from 0 to 6) :");
				} catch (Exception e) { System.out.println("choose between 0 and 6"); }

			}

		}
		else{
			i=col;
			if(pos.board[i+7*5]==0){i=i+7*5;}
			else if(pos.board[i+7*4]==0){i=i+7*4;}
			else if(pos.board[i+7*3]==0){i=i+7*3;}
			else if(pos.board[i+7*2]==0){i=i+7*2;}
			else if(pos.board[i+7]==0){i=i+7;}
			else if(pos.board[i]==0){}
			else return null;

		}

		System.out.println("i = "+i); 
		Puissance4Move mm = new Puissance4Move();
		mm.moveIndex = i;

		return mm;
	}

	@Override
	public Move createMove(Position p) {
		// TODO Auto-generated method stub
		return null;
	}


}
