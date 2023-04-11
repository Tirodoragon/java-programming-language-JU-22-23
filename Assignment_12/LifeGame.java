import java.util.ArrayList;
import java.util.List;

class LifeGame implements LifeGameInterface {
	private boolean[][] startingBoard;
	List<String> toShrink = new ArrayList<>();
	List<String> toExpand = new ArrayList<>();
	
	private boolean[][] makeBigger(boolean[][] toResize) {
		boolean[][] resizedBoard = new boolean[toResize.length + 2][toResize[0].length + 2];
		
		for (int i = 0; i < resizedBoard.length; i++) {
			for (int j = 0; j < resizedBoard[0].length; j++) {
				if (i == 0 || i == (resizedBoard.length - 1) || j == 0 || j == (resizedBoard[0].length - 1)) {
					resizedBoard[i][j] = false;
				} else {
					resizedBoard[i][j] = toResize[i - 1][j - 1];
				}
			}
		}
		
		return resizedBoard;
	}
	
	private int[][] numericalRepresentation(boolean[][] toEdit) {
		int[][] numericalRep = new int[toEdit.length][toEdit[0].length];
		
		for (int i = 1; i < toEdit.length - 1; i++) {
			for (int j = 1; j < toEdit[0].length - 1; j++) {
				if (toEdit[i - 1][j - 1]) {
					numericalRep[i][j]++;
				}
				if (toEdit[i + 1][j - 1]) {
					numericalRep[i][j]++;
				}
				if (toEdit[i + 1][j + 1]) {
					numericalRep[i][j]++;
				}
				if (toEdit[i - 1][j + 1]) {
					numericalRep[i][j]++;
				}
				if (toEdit[i][j - 1]) {
					numericalRep[i][j]++;
				}
				if (toEdit[i][j + 1]) {
					numericalRep[i][j]++;
				}
				if (toEdit[i - 1][j]) {
					numericalRep[i][j]++;
				}
				if (toEdit[i + 1][j]) {
					numericalRep[i][j]++;
				}
			}
		}
		
		return numericalRep;
	}
	
	private void shrinkOrExpand() {
		boolean shrink = true;
		
		for (int i = 0; i < startingBoard[0].length; i++) {
			if (startingBoard[0][i]) {
				if (!toExpand.contains("Up")) {
					toExpand.add("Up");
				}
				shrink = false;
			} else if (startingBoard[1][i]) {
				shrink = false;
			}
		}
		if (shrink) {
			toShrink.add("Up");
		}
		
		shrink = true;
		for (int i = 0; i < startingBoard[0].length; i++) {
			if (startingBoard[startingBoard.length - 1][i]) {
				if (!toExpand.contains("Down")) {
					toExpand.add("Down");
				}
				shrink = false;
			} else if (startingBoard[startingBoard.length - 2][i]) {
				shrink = false;
			}
		}
		if (shrink) {
			toShrink.add("Down");
		}
		
		shrink = true;
		for (int i = 0; i < startingBoard.length; i++) {
			if (startingBoard[i][0]) {
				if (!toExpand.contains("Left")) {
					toExpand.add("Left");
				}
				shrink = false;
			} else if (startingBoard[i][1]) {
				shrink = false;
			}
		}
		if (shrink) {
			toShrink.add("Left");
		}
		
		shrink = true;
		for (int i = 0; i < startingBoard.length; i++) {
			if (startingBoard[i][startingBoard[0].length - 1]) {
				if (!toExpand.contains("Right")) {
					toExpand.add("Right");
				}
				shrink = false;
			} else if (startingBoard[i][startingBoard[0].length - 2]) {
				shrink = false;
			}
		}
		if (shrink) {
			toShrink.add("Right");
		}
	}
	
	private void changeSize() {
		if (toExpand.contains("Up")) {
			boolean[][] tmpBoard = new boolean[startingBoard.length + 1][startingBoard[0].length];
			for (int i = 0; i < tmpBoard.length; i++) {
				for (int j = 0; j < tmpBoard[0].length; j++) {
					if (i == 0) {
						tmpBoard[i][j] = false;
					} else {
						tmpBoard[i][j] = startingBoard[i - 1][j];
					}
				}
			}
			startingBoard = tmpBoard;
		}
		if (toExpand.contains("Down")) {
			boolean[][] tmpBoard = new boolean[startingBoard.length + 1][startingBoard[0].length];
			for (int i = 0; i < tmpBoard.length; i++) {
				for (int j = 0; j < tmpBoard[0].length; j++) {
					if (i == tmpBoard.length - 1) {
						tmpBoard[i][j] = false;
					} else {
						tmpBoard[i][j] = startingBoard[i][j];
					}
				}
			}
			startingBoard = tmpBoard;
		}
		if (toExpand.contains("Left")) {
			boolean[][] tmpBoard = new boolean[startingBoard.length][startingBoard[0].length + 1];
			for (int i = 0; i < tmpBoard.length; i++) {
				for (int j = 0; j < tmpBoard[0].length; j++) {
					if (j == 0) {
						tmpBoard[i][j] = false;
					} else {
						tmpBoard[i][j] = startingBoard[i][j - 1];
					}
				}
			}
			startingBoard = tmpBoard;
		}
		if (toExpand.contains("Right")) {
			boolean[][] tmpBoard = new boolean[startingBoard.length][startingBoard[0].length + 1];
			for (int i = 0; i < tmpBoard.length; i++) {
				for (int j = 0; j < tmpBoard[0].length; j++) {
					if (j == (tmpBoard[0].length - 1)) {
						tmpBoard[i][j] = false;
					} else {
						tmpBoard[i][j] = startingBoard[i][j];
					}
				}
			}
			startingBoard = tmpBoard;
		}
		
		if (toShrink.contains("Up")) {
			boolean[][] tmpBoard = new boolean[startingBoard.length - 1][startingBoard[0].length];
			for (int i = 0; i < tmpBoard.length; i++) {
				for (int j = 0; j < tmpBoard[0].length; j++) {
					tmpBoard[i][j] = startingBoard[i + 1][j];
				}
			}
			startingBoard = tmpBoard;
		}
		if (toShrink.contains("Down")) {
			boolean[][] tmpBoard = new boolean[startingBoard.length - 1][startingBoard[0].length];
			for (int i = 0; i < tmpBoard.length; i++) {
				for (int j = 0; j < tmpBoard[0].length; j++) {
					tmpBoard[i][j] =startingBoard[i][j];
				}
			}
			startingBoard = tmpBoard;
		}
		if (toShrink.contains("Left")) {
			boolean[][] tmpBoard = new boolean[startingBoard.length][startingBoard[0].length - 1];
			for (int i = 0; i < tmpBoard.length; i++) {
				for (int j = 0; j < tmpBoard[0].length; j++) {
					tmpBoard[i][j] = startingBoard[i][j + 1];
				}
			}
			startingBoard = tmpBoard;
		}
		if (toShrink.contains("Right")) {
			boolean[][] tmpBoard = new boolean[startingBoard.length][startingBoard[0].length - 1];
			for (int i = 0; i < tmpBoard.length; i++) {
				for (int j = 0; j < tmpBoard[0].length; j++) {
					tmpBoard[i][j] = startingBoard [i][j];
				}
			}
			startingBoard = tmpBoard;
		}
	}
	
	@Override
	public void set(boolean[][] board) {
		startingBoard = new boolean[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				startingBoard[i][j] = board[i][j];
			}
		}
	}
	
	@Override
	public void oneStep(int minToSurvive, int maxToSurvive, int minToBorn, int maxToBorn) {
		int[][] numRep;
		
		toExpand.clear();
		toShrink.clear();
		
		numRep = numericalRepresentation(makeBigger(startingBoard));
		
		for (int i = 1; i < numRep.length - 1; i++) {
			for (int j = 1; j < numRep[0].length - 1; j++) {
				if (startingBoard[i - 1][j  - 1] && numRep[i][j] >= minToSurvive && numRep[i][j] <= maxToSurvive);
				else {
					startingBoard[i - 1][j - 1] = false;
				}
				if (!startingBoard[i - 1][j - 1] && numRep[i][j] >= minToBorn && numRep[i][j] <= maxToBorn) {
					startingBoard[i - 1][j - 1] = true;
				}
			}
		}
		
		shrinkOrExpand();
		
		changeSize();
	}
	
	@Override
	public boolean[][] get() {
		return startingBoard;
	}
}