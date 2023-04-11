/**
 * Interfejs narzÄdzia graficznego.
 *
 */
public interface GraphicsInterface {

	/**
	 * WyjÄtek zgĹaszany w przypadku braku dostÄpu do pĹĂłtna.
	 */
	public class NoCanvasException extends Exception {
		private static final long serialVersionUID = 8263666547167500356L;
	}

	/**
	 * WyjÄtek zgĹaszany, gdy poczÄtkowe poĹoĹźenie, od ktĂłrego wypeĹniany jest
	 * obszar jest nieprawidĹowe (poza obszarem pĹĂłtna) lub powodujÄce zgĹoszenie
	 * wyjÄtku {@link CanvasInterface.BorderColorException}.
	 *
	 */
	public class WrongStartingPosition extends Exception {
		private static final long serialVersionUID = -8582620817646059440L;
	}

	/**
	 * Metoda, za pomocÄ ktĂłrej uĹźytkownik dostarcza pĹĂłtno, na ktĂłrym wykonywane
	 * bÄdÄ operacje graficzne.
	 * 
	 * @param canvas referencja do pĹĂłtna
	 */
	public void setCanvas(CanvasInterface canvas);

	/**
	 * Metoda wypeĹniajÄca obszar kolorem color. WypeĹnianie obszaru rozpoczynane
	 * jest od pozycji startingPosition.
	 * 
	 * @param startingPosition poĹoĹźenie poczÄtkowe
	 * @param color            kolor, ktĂłrym wypeĹniany jest obszar
	 * @throws WrongStartingPosition poĹoĹźenie poczÄtkowe jest niepoprawne
	 * @throws NoCanvasException     referencja do pĹĂłtna jest niepoprawna
	 */
	public void fillWithColor(Position startingPosition, Color color) throws WrongStartingPosition, NoCanvasException;
}