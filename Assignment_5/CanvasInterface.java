/**
 * Interfejs pĹĂłtna
 *
 */
public interface CanvasInterface {

	/**
	 * WyjÄtek zgĹaszany, gdy zlecana jest operacja poza obszarem pĹĂłtna.
	 */
	public class CanvasBorderException extends Exception {
		private static final long serialVersionUID = 4759606029757073905L;
	}

	/**
	 * WyjÄtek zgĹaszany, gdy piksel zmienia kolor ze stanowiÄcego kolor graniczny
	 * na nowy kolor.
	 */
	public class BorderColorException extends Exception {
		private static final long serialVersionUID = -4752159948902473254L;
		public final Color previousColor;

		public BorderColorException(Color color) {
			previousColor = color;
		}
	}

	/**
	 * Metoda pozwalajÄca na zmianÄ koloru piksela o poĹoĹźeniu position na color.
	 * 
	 * @param position poĹoĹźenie piksela
	 * @param color    nowy kolor piksela
	 * @throws CanvasBorderException poĹoĹźenie wypada poza obszarem pĹĂłtna
	 * @throws BorderColorException  zmieniono kolor piksela granicznego
	 */
	public void setColor(Position position, Color color) throws CanvasBorderException, BorderColorException;
}