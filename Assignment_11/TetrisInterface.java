
public interface TetrisInterface {
	/**
	 * Ustawia szerokosc szybu do gry
	 * @param width szerokosc planszy
	 */
	void setWidth( int width );
	
	/**
	 * Ustawia glebokosc szybu 
	 * @param depth glebokosc planszy
	 */
	void setDepth( int depth );
	
	/**
	 * Zrzuca na dno szybu ksztalt.
	 * 
	 * @param shape ksztalt, ktory jest zrzucany do szybu. 
	 * Pierwszy indeks tablicy powiazany jest z szerokoscia.
	 * Indeks drugi powiazany jest z glebokoscia ksztaltu.
	 * Sam ksztal definiuja pozycje tablicy, ktore
	 * sa ustawione na true. Czyli ksztal krzyza
	 * zapisany byly np. tak: { { false, true, false }, { true, true, true}, { false, true, false } } 
	 * @param firstColumn pierwsza kolumna - od niej zaczyna sie ksztalt
	 * @return liczba usunietych linii - liczba linii, ktore zostaly
	 * usuniete na skutek zrzucenia ksztaltu
	 */
	int addShape( boolean [][] shape, int firstColumn );
}