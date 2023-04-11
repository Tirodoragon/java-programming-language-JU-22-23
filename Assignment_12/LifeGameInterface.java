/**
 * Interfejs gry w zycie wg. uogolnionych regul gry.<br>
 * Gra toczy sie na prostokatnej planszy. Reguly gry podawane sa
 * poprzez wskazanie warunkow utrzymania przy zyciu zywej komorki
 * i warunkow narodzin nowej. Dla warunkow gry wg. 
 * <a href="http://pl.wikipedia.org/wiki/Gra_w_%C5%BCycie#Regu.C5.82y_gry_wed.C5.82ug_Conwaya">Convaya</a>
 * wywolanie metody {@linkplain oneStep} powinno wygladac nastepujaco:<br>
 * {@code toStep( 2, 3, 3, 3 )}
 * @author oramus
 * 
 * @see <a href="http://pl.wikipedia.org/wiki/Gra_w_%C5%BCycie">Gra w zycie w wikipedii</a>
 *
 */
public interface LifeGameInterface {
	
	/**
	 * Ustawia stan planszy. Komorki tablicy ustawione na true sa zywe, na false - martwe.
	 * Plansza ma miec margines jednej komorki gdzie wszystkie komorki sa martwe.
	 * @param board - plansza do gry z ustalonym stanem komorek.
	 */
	void set( boolean[][] board );
	
	/**
	 * Wykonuje jeden krok ewolucji systemu.
	 * @param minToSurvive - minimalna liczba zywych sasiadow potrzebnych by komorka przetrwala
	 * @param maxToSurvive - maksymalna liczba zywych sasiadow potrzebnych by komorka przetrwala
	 * @param minToBorn - minimalna liczba zywych sasiadow pozwalajaca na narodziny
	 * @param maxToBorn - maksymalna liczba zywych sasiadow pozwalajaca na narodziny
	 */
	void oneStep( int minToSurvive, int maxToSurvive, int minToBorn, int maxToBorn );
	
	/**
	 * Zwraca aktualny stan systemu. Zywe komorki nie znajduja sie na brzegu tablicy. 
	 * Tablica ma rozmiar minimalny potrzebny do zapisania stanu gry z uwzglednieniem
	 * marginesu.
	 * @return - dwuwymiarowa tablica stanow komorek.
	 */
	boolean[][] get();
}