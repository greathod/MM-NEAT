package boardGame.othello;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import boardGame.BoardGameState;
import boardGame.TwoDimensionalBoardGameState;

public class OthelloState extends TwoDimensionalBoardGameState {
	
	private int numPasses;
	
	private final int BOARD_WIDTH = 8;

	private final int BOARD_CORE1 = 3; // Keeps track of the center of the Board
	private final int BOARD_CORE2 = 4; // Keeps track of the center of the Board
	
	private final int EMPTY = -1;
	private final int BLACK_CHIP = 0;
	private final int WHITE_CHIP = 1;
	
	/**
	 * Default Constructor
	 */
	public OthelloState(){
		super(2);
	}
	
	/**
	 * Copy Constructor; Allows the User to specify which pieces are where on the Board
	 * 
	 * @param newBoard int[][] representing the new Board
	 * @param nextPlayer Player whose turn it is
	 * @param newWinners List<Integer> containing the indexes of the current Winners
	 */
	public OthelloState(OthelloState state){
		super(state);
	}

	/**
	 * Returns true if the Game is over, else returns false
	 * 
	 * @return True if the Board is completely filled, else returns false
	 */
	@Override
	public boolean endState() {
		// Schrum: Should be able to reduce this method to only checking if two passes have occurred.
		// Most games will end with two passes because the board will be full, but you avoid the inefficiency
		// of checking for an empty space every move.
		if(numPasses == 2) return true;
		return false;
	}

	/**
	 * Checks the Winners of this current BoardGameState and updates the winners List
	 */
	private void checkWinners(){
		if(endState()){
			
			int blackChipCount = 0;
			int whiteChipCount = 0;
			
			for(int i = 0; i < BOARD_WIDTH; i++){
				for(int j = 0; j < BOARD_WIDTH; j++){
					if(boardState[i][j] == BLACK_CHIP){
						blackChipCount++;
					}else if(boardState[i][j] == WHITE_CHIP){
						whiteChipCount++;
					}
				}
			}
			
			if(blackChipCount > whiteChipCount){
				winners.add(BLACK_CHIP);
			}else if(whiteChipCount > blackChipCount){
				winners.add(WHITE_CHIP);
			}else{
				winners.add(BLACK_CHIP);
				winners.add(WHITE_CHIP);
			}
		}
	}
	
	/**
	 * Allows a Player to make a Move on the current BoardGameState; Returns true if the Move was valid,
	 * and updates the List of winners
	 * 
	 * @param goTo Point representing an Empty Space being played towards
	 * @return True if the Move was successful, else returns false
	 */
	// TODO: Schrum: this method needs to be completely redesigned to simply take a goTo location
	//         into account. There is no point being moved from.
	public boolean move(Point goTo){
		
		// goTo will play valid Moves with y == 0; not a problem with possibleBoardStates()
		
		int goX = (int) goTo.getX();
		int goY = (int) goTo.getY();

		assert goX >= 0 && goX < BOARD_WIDTH;
		assert goY >= 0 && goY < BOARD_WIDTH;
						
		boolean check1 = boardState[goX][goY] == EMPTY;
		if(!check1) return false; // Cannot move to a Non-Empty Space; y problem not due to Non-Empty Spaces
		
		boolean[] moves = new boolean[8]; // Used to keep track of able to Move or not
		boolean ableToMove = false;
		int index = 0;
		
		for(int dX = -1; dX <=1; dX++){ // Works off of the same idea as possibleBoardStates()
			for(int dY = -1; dY <=1; dY++){
				if(dX != 0 || dY != 0){ // Does not run if both dX and dY are 0
					
					if((goX + dX < 0 || goX + dX >= BOARD_WIDTH) || (goY + dY < 0 || goY + dY >= BOARD_WIDTH)){ // y can be 0 here and not be OoB
						break;
					} // Out of Bounds; cannot use this Offset
					
					boolean foundEnemy = boardState[goX + dX][goY + dY] == (nextPlayer + 1) % 2; // Found an Enemy Chip
					// Unable to find Enemies at y == 0; probably due to unable to Place there for some reason
					if(foundEnemy){ // Only runs if an Enemy is present
						// TODO: y is never == 0 for some reason. Fix it.
						// x can be 0 at this part, so it must be something above here...

						int x = goX + dX; // Stores the offset Space
						int y = goY + dY;
						do{ // Y never starts at 0 here
							x += dX;  // Searches the next Space over
							y += dY;
							// Y == 0 is always considered In-Bounds

						}while((x >= 0 && x < BOARD_WIDTH) && (y >= 0 && y < BOARD_WIDTH) && (boardState[x][y] == (nextPlayer + 1) % 2)); // Continues while within bounds and Space has an Enemy Chip
						// y == 0 Error is not affected by the order of these Checks
						if((x >= 0 && x < BOARD_WIDTH) && (y >= 0 && y < BOARD_WIDTH)){ // Within Bounds? If False, above Check continued off of edge of Board; Enemy Chips continue to edge.
							if(boardState[x][y] == nextPlayer){ // Found Player Chip at end of Line; able to make the Move
								ableToMove = true; // Was able to make at least 1 Move
								moves[index] = true; // Sets this direction to true for the Update
								// Y != 0 here
							} // End Able-to-Play If Statement
						}
					} // End foundEnemy Check
					index++;
				} // End dX, dY Check
			}
		} // End Offset For-Loop
		
		int indexCheck = 0;
		for(int dX = -1; dX <= 1; dX++){ // Update made external from the above Check to avoid interfering with future Checks
			for(int dY = -1; dY <= 1; dY++){
				if(dX != 0 || dY != 0){
					if(moves[indexCheck]){
						
						int x = goX; // Stores the offset Space
						int y = goY;
						do{
							boardState[x][y] = nextPlayer;
							x += dX;  // Searches the next Space over
							y += dY;
						}while((x >= 0 && x < BOARD_WIDTH) && (y >= 0 && y < BOARD_WIDTH) && (boardState[x][y] == (nextPlayer + 1) % 2));
					}
				indexCheck++;
				}
			}
		}
	
		if(ableToMove){ // Was able to Move; Update the nextPlayer and boardState, and return True
			boardState[goX][goY] = nextPlayer;
			nextPlayer = (nextPlayer + 1) % 2;
			checkWinners();
			return true;			
		}else{ // Unable to Move; return False
			return false;
		}
	}
	
	/**
	 * Creates a List of all possible valid Moves from this BoardGameState
	 * 
	 * @param currentState The BoardGameState being played from
	 * @return List<T> of all BoardGameStates possible from the given BoardGameState
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends BoardGameState> List<T> possibleBoardGameStates(T currentState) {
		
		List<OthelloState> possible = new ArrayList<OthelloState>();
		List<Point> chipMoves = new ArrayList<Point>();
		
		for(int i = 0; i < BOARD_WIDTH; i++){ // This part works
			for(int j = 0; j < BOARD_WIDTH; j++){
				if(boardState[i][j] == nextPlayer){
					chipMoves.add(new Point(i, j));
				}
			}
		}
		
		for(Point chip : chipMoves){ // Cycles through all Chips
			int chipX = (int) chip.getX();
			int chipY = (int) chip.getY();
			
			for(int dX = -1; dX <= 1; dX++){ // Doesn't handle Diagonals well; probably part of the Move check				
				for(int dY = -1; dY <= 1; dY++){
					if(dX != 0 || dY != 0){ // Cycles through the dX and dY correctly
						
						int x = chipX;
						int y = chipY;
						
						do{
							x += dX;
							y += dY;
						}while((x >= 0 && x < BOARD_WIDTH) && (y >= 0 && y < BOARD_WIDTH) && boardState[x][y] == (nextPlayer + 1) % 2);
						
						if((x >= 0 && x < BOARD_WIDTH) && (y >= 0 && y < BOARD_WIDTH)){
							if(boardState[x][y] == EMPTY){
								OthelloState temp = (OthelloState) currentState.copy();
								if(temp.move(new Point(x, y))){
									possible.add(temp);
								}
							}
						}
					}
				}
			}
		}
		
		List<T> returnThis = new ArrayList<T>();
		// Schrum: this case here annoys me. I feel that there is a way to avoid it
		returnThis.addAll((Collection<? extends T>) possible);
		
		if(returnThis.isEmpty()){ // If unable to make a Move, must return the currentState; counts as a Pass
			returnThis.add(currentState);
			numPasses++;
			return returnThis;
		}else{
			return returnThis;			
		}
	}

	/**
	 * Creates a copy of this BoardGameState
	 * 
	 * @return Copy of this BoardGameState
	 */
	@Override
 	public BoardGameState copy() {
		return new OthelloState(this);
	}

	/**
	 * Returns a List of all Winners from this BoardGameState
	 * 
	 * @return List<Integer> containing the Indexes from this BoardGameState
	 */
	@Override
	public List<Integer> getWinners() {
		return winners;
	}

	@Override
	public void setupStartingBoard() {
		numPasses = 0;

		boardState[BOARD_CORE1][BOARD_CORE1] = BLACK_CHIP;
		boardState[BOARD_CORE2][BOARD_CORE2] = BLACK_CHIP;
		
		boardState[BOARD_CORE1][BOARD_CORE2] = WHITE_CHIP;
		boardState[BOARD_CORE2][BOARD_CORE1] = WHITE_CHIP;

	}

	@Override
	public int getBoardWidth() {
		return BOARD_WIDTH;
	}

	@Override
	public int getBoardHeight() {
		return BOARD_WIDTH;
	}

	@Override
	public char[] getPlayerSymbols() {
		return new char[]{'B', 'W'};
	}
	
}
