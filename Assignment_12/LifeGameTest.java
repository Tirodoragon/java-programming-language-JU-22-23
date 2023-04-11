
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class LifeGameTest {
	private LifeGameInterface lgi;

	@Before
	public void init() {
		lgi = new LifeGame();
	}

	private static boolean[][] string2bools(String lab) {
		int rows = (int) Math.sqrt(lab.length());
		if (rows * rows != lab.length()) {
			System.out.println("Blad w tablicy");
			System.exit(1);
		}

		boolean[][] res = new boolean[rows][rows];

		int pos = 0;
		for (int j = 0; j < rows; j++)
			for (int i = 0; i < rows; i++) {
				res[j][i] = (lab.charAt(pos) == (char) 'x');
				pos++;
			}

		return res;
	}

	private static void show(boolean[][] lab) {
		for (int i = 0; i < lab.length; i++) { // po wierszach
			for (int j = 0; j < lab[0].length; j++)
				// po kolumnach w wierszach
				System.out.print(lab[i][j] ? " x" : " .");
			System.out.println();
		}
	}

	private static boolean assertArrayEquals(boolean[][] ok, boolean[][] toTest) {
		for (int i = 0; i < ok.length; i++) {
			org.junit.Assert.assertArrayEquals(ok[i], toTest[i]);
		}
		return true;
	}

	private void show( boolean[][] ini, boolean[][] exp ) {
		System.out.println( "__________________________");
		show( ini );
		show( exp );
		assertNotNull( "Wynik pracy nie moze byc null", lgi.get() );
		show( lgi.get() );		
	}
	
	
	@Test
	public void test1() {
		boolean[][] tmp = string2bools("....." + "....." + "..x.." + "....."
				+ ".....");
		boolean[][] outExpected = string2bools("..." + ".x." + "...");

		lgi.set(tmp);
		lgi.oneStep(0, 1, 3, 3);
		show( tmp, outExpected );

		assertTrue("test 1 - jedna komorka, ktora ma przezyc",
				assertArrayEquals(outExpected, lgi.get()));
	}

	@Test
	public void test2() {
		boolean[][] tmp = string2bools("....." + "....." + "..x.." + "....."
				+ ".....");
		boolean[][] outExpected = string2bools("....." + ".xxx." + ".x.x."
				+ ".xxx." + ".....");
		lgi.set(tmp);
		lgi.oneStep(2, 2, 1, 1);
		show( tmp, outExpected );

		assertTrue(
				"test 2 - jedna komorka, ktora ma pozwolic na pojawienie sie 4-nowych",
				assertArrayEquals(outExpected, lgi.get()));
	}

	@Test
	public void test3() {
		boolean[][] tmp = string2bools("....." + 
	                                   ".xxx." + 
				                       ".x.x." + 
	                                   ".xxx." + ".....");
		
		boolean[][] outExpected = 
				string2bools( "....." + 
		                      ".xxx." + 
						      ".xxx."
				            + ".xxx." 
						    + ".....");
		lgi.set(tmp);
		lgi.oneStep(0, 8, 8, 8 );
		show( tmp, outExpected );

		assertTrue(
				"test 3 - okienko - ma sie pojawic nowa komorka",
				assertArrayEquals(outExpected, lgi.get()));
	}
	
	@Test
	public void test4() {
		boolean[][] tmp = string2bools("....." + 
	                                   ".xxx." + 
				                       ".x.x." + 
	                                   ".xxx." + ".....");
		
		boolean[][] outExpected = 
				string2bools( "......." +
						      "...x..." +
		                      "..xxx.." + 
						      ".xx.xx."
				            + "..xxx.." 
						    + "...x..."
				            + "......." );
		lgi.set(tmp);
		lgi.oneStep(0, 8, 3, 3 );
		show( tmp, outExpected );

		assertTrue(
				"test 4 - okienko - maja sie pojawic 4 nowe komorka",
				assertArrayEquals(outExpected, lgi.get()));
	}
	
	@Test
	public void test5() {
		boolean[][] tmp = string2bools("....." + 
	                                   ".xxx." + 
				                       ".x.x." + 
	                                   ".xxx." + ".....");
		
		boolean[][] outExpected = 
				string2bools( "......." +
						      "...x..." +
		                      "......." + 
						      ".x...x."
				            + "......." 
						    + "...x..."
				            + "......." );
		lgi.set(tmp);
		lgi.oneStep(0, 1, 3, 3 );
		show( tmp, outExpected );

		assertTrue(
				"test 5 - okienko ma zniknac - maja sie pojawic 4 nowe komorka",
				assertArrayEquals(outExpected, lgi.get()));
	}
	
	@Test
	public void test6() {
		boolean[][] tmp = string2bools("....." + 
	                                   "..x.." + 
				                       "...x." + 
	                                   ".xxx." + 
				                       ".....");
		
		lgi.set(tmp);
		
		for ( int i = 0; i < 64; i++ )
			lgi.oneStep(2, 3, 3, 3 );
		
		show( tmp, tmp );
		assertTrue(
				"test 6 - 64 kroki standardowej gry, init - Dacota",
				assertArrayEquals( tmp, lgi.get()));
	}
}
