import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interfejs systemu obsĹugujÄcego wyszukiwanie skrzyĹźowaĹ linii autobusowych.
 */
public interface BusLineInterface {

	/**
	 * Interfejs reprezentujÄcy parÄ dwĂłch linii autobusowych.
	 */
	public interface LinesPair {
		/**
		 * Metoda zwraca nazwÄ pierwszej z zapamiÄtanych linii.
		 * 
		 * @return nazwa pierwszej z linii
		 */
		public String getFirstLineName();

		/**
		 * Metoda zwraca nazwiÄ drugiej z zapamiÄtanych linii.
		 * 
		 * @return nazwa drugiej z linii
		 */
		public String getSecondLineName();
	}

	/**
	 * Metoda dodaje liniÄ autobusowÄ o podanej nazwie. Wraz z nazwÄ linii
	 * przekazywane sÄ informacje o pierwszym i ostatnim punkcie na trasie.
	 * 
	 * @param busLineName nazwa linii
	 * @param firstPoint  pierwszy punkt na trasie
	 * @param lastPoint   ostatni punkt na trasie
	 */
	public void addBusLine(String busLineName, Position firstPoint, Position lastPoint);

	/**
	 * Metoda dodaje odcinek lineSegment do linii autobusowej o nazwie busLineName.
	 * Odcinki nie muszÄ byÄ dodawane w jakiejkolwiek kolejnoĹci. MoĹźna z nich
	 * utworzyÄ caĹÄ trasÄ poprzez uwzglÄdnienie poĹoĹźenia punktĂłw kraĹcowych.
	 * 
	 * @param busLineName nazwa linii autobusowej
	 * @param lineSegment odcinek trasy
	 */
	public void addLineSegment(String busLineName, LineSegment lineSegment);

	/**
	 * Metoda zleca rozpoczÄcie poszukiwania skrzyĹźowaĹ.
	 */
	public void findIntersections();

	/**
	 * Metoda zwraca mapÄ, ktĂłrej kluczem jest nazwa linii autobusowej zaĹ wartoĹciÄ
	 * lista poĹoĹźeĹ wszystkich punktĂłw naleĹźÄcych do danej linii. Mapa nie zawiera
	 * wĹrĂłd kluczy nazw linii, ktĂłre nie majÄ Ĺźadnego skrzyĹźowania.
	 * 
	 * @return mapa z przebiegiem tras linii autobusowych
	 */
	public Map<String, List<Position>> getLines();

	/**
	 * Metoda zwraca mapÄ, ktĂłrej kluczem jest nazwa linii autobusowej a wartoĹciÄ
	 * lista kolejnych skrzyĹźowaĹ na trasie linii. Mapa nie zawiera wĹrĂłd kluczy
	 * nazw linii, ktĂłre nie majÄ Ĺźadnego skrzyĹźowania.
	 * 
	 * @return mapa skrzyĹźowaĹ dla poszczegĂłlnych linii.
	 */
	public Map<String, List<Position>> getIntersectionPositions();

	/**
	 * Metoda zwraca mapÄ, ktĂłrej kluczem jest nazwa linii autobusowej a wartoĹciÄ
	 * lista nazw kolejnych linii, z ktĂłrymi linia ta ma skrzyĹźowania. ZbiĂłr kluczy
	 * nie zawiera linii, ktĂłre nie majÄ skrzyĹźowania.
	 * 
	 * @return mapa skrzyĹźowaĹ pomiÄdzy liniami
	 */
	public Map<String, List<String>> getIntersectionsWithLines();

	/**
	 * Metoda zwraca mapÄ, ktĂłrej kluczem jest para nazw linii a wartoĹciÄ zbiĂłr
	 * poĹoĹźeĹ, w ktĂłrych para linii ma skrzyĹźowania. JeĹli linie nie majÄ Ĺźadnego
	 * skrzyĹźowania, to mapa zawiera pusty zbiĂłr pozycji skrzyĹźowaĹ
	 * 
	 * @return mapa skrzyĹźowaĹ dla par linii autobusowych
	 */
	public Map<LinesPair, Set<Position>> getIntersectionOfLinesPair();
}