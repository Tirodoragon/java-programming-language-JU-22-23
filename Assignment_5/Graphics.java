import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

class Graphics implements GraphicsInterface {
	CanvasInterface canvas;
	@Override
	public void setCanvas(CanvasInterface canvasI) {
		canvas = canvasI;
	}
	
	List<Position> cells;
	@Override
	public void fillWithColor(Position startingPosition, Color color) throws GraphicsInterface.WrongStartingPosition, GraphicsInterface.NoCanvasException {
		if (canvas == null) {
			throw new NoCanvasException();
		}
		
		try {
			canvas.setColor(startingPosition, color);
		}
		catch (CanvasInterface.CanvasBorderException exception) {
			throw new WrongStartingPosition();
		}
		catch (CanvasInterface.BorderColorException exception) {
			try {
				canvas.setColor(startingPosition, exception.previousColor);
			}
			catch (Exception exception2) {}
			throw new WrongStartingPosition();
		}
		
		cells = new LinkedList<>();
		
		Queue<Position> queue = new LinkedList<>();
		
		queue.add(startingPosition);
		
		while (queue.size() > 0) {
			Position currentPosition = queue.poll();
			
			if (cells.contains(currentPosition)) {
				continue;
			} 
			else {
				cells.add(currentPosition);
			}
			
			try {
				canvas.setColor(currentPosition, color);
			}
			catch (CanvasInterface.CanvasBorderException exception) {
				continue;
			}
			catch (CanvasInterface.BorderColorException exception) {
				try {
					canvas.setColor(currentPosition, exception.previousColor);
					continue;
				}
				catch (Exception exception2) {}
			}
			
			queue.add(new PositionImplementation(currentPosition.getRow(), currentPosition.getCol() + 1));
			queue.add(new PositionImplementation(currentPosition.getRow() + 1, currentPosition.getCol()));
			queue.add(new PositionImplementation(currentPosition.getRow(), currentPosition.getCol() - 1));
			queue.add(new PositionImplementation(currentPosition.getRow() - 1, currentPosition.getCol()));
		}
	}
	
	class PositionImplementation implements Position {
		private final int row;
		private final int col;
		
		public PositionImplementation(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		public int getRow() {
			return row;
		}
		
		public int getCol() {
			return col;
		}
		
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			
			PositionImplementation other = (PositionImplementation) obj;
			
			return row == other.row && col == other.col;
		}
	}
}