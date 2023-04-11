class Tetris implements TetrisInterface {
	private int width = 0;
	private int depth = 0;
	private boolean[][] board;
	private int linesRemoved = 0;
	
	private void moveDown(boolean [][] board, int depth) {
		for (int r = depth; r < this.depth; r++) {
			for (int s = 0; s < width; s++) {
				board[s][r - 1] = board[s][r];
			}
		}
		for (int t = 0; t < width; t++) {
			board[t][this.depth - 1] = false;
		}
	}
	
	private void removeLines(boolean [][] board) {
		boolean emptyCell;
		for (int m = 0; m < depth - 1; m++) {
			emptyCell = false;
			for (int n = 0; n < width; n++) {
				if (board[n][m] == false) {
					emptyCell = true;
					break;
				}
			}
			if (!emptyCell) {
				linesRemoved++;
				moveDown(board, m + 1);
				m--;
			}
		}
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
		board = new boolean[width][depth];
	}
	
	@Override
	public void setDepth(int depth) {
		this.depth = depth;
		board = new boolean[width][depth];
	}
	
	@Override
	public int addShape(boolean [][] shape, int firstColumn) {
		int shapeWidth = shape.length;
		int shapeHeight = shape[0].length;
		int shapeDepth = depth - shapeHeight;
		boolean collision = false;
		while (shapeDepth >= 0) {
			for (int i = 0; i < shapeWidth; i++) {
				for (int j = 0; j < shapeHeight; j++) {
					if (shape[i][j] == true && board[firstColumn + i][shapeDepth + j] == true) {
						collision = true;
					}
				}
			}
			if (collision) {
				shapeDepth--;
				collision = false;
				continue;
			}
			for (int k = 0; k < shapeWidth; k++) {
				for (int l = 0; l < shapeHeight; l++) {
					if (shape[k][l] == true) {
						board[firstColumn + k][depth - (shapeDepth + l + 1)] = true;
					}
				}
			}
			break;
		}
		linesRemoved = 0;
		removeLines(board);
		return linesRemoved;
	}
}