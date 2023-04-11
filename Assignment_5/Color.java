
/**
 * Typ wyliczeniowy reprezentujÄcy kolory
 */
public enum Color {
	BLACK, WHITE, GREEN, YELLOW, RED, ORANGE, PINK,  ;
	
	@Override
	public String toString() {
		return name().substring(0, 1);
	}
	
	/**
	 * Metoda main pozwalajÄca zobaczyÄ jak prezentowane sÄ
	 * poszczegĂłlne kolory.
	 */
	public static void main(String[] args) {
		for ( Color color : Color.values() ) {
			System.out.println( color.name() + " -> " + color );
		}
	}
}