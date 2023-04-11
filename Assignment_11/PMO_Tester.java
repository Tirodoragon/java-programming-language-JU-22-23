import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PMO_Tester {

	private TetrisInterface wsi;

	private boolean[][] box2x2;
	private boolean[][] box3x3;
	private boolean[][] line2H;
	private boolean[][] line2V;

	@Before
	public void ini() {
		wsi = new Tetris();
		wsi.setWidth(7);
		wsi.setDepth(100);

		box2x2 = createShape(2, 2);
		box2x2[0][0] = true;
		box2x2[0][1] = true;
		box2x2[1][0] = true;
		box2x2[1][1] = true;

		box3x3 = createShape(3, 3);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				box3x3[i][j] = true;

		line2H = createShape(7, 2);
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 2; j++)
				line2H[i][j] = true;

		line2V = createShape(7, 2);
		line2V[0][0] = true;
		line2V[0][1] = true;

		show(box2x2);
		show(box3x3);
		show(line2H);
		show(line2V);
	}

	private boolean[][] createShape(int maxW, int maxH) {
		return new boolean[maxW][maxH];
	}

	private void show(boolean[][] shape) {
		System.out.println();
		for (int h = 0; h < shape[0].length; h++) {
			for (int w = 0; w < shape.length; w++)
				System.out.print(" "
						+ (shape[w][shape[0].length - h - 1] ? "x" : "."));
			System.out.println();
		}
	}

	@Test
	public void test1() {
		try {
			assertEquals( "Zrzucam pierwszy ksztalt, nic nie powinno dac sie usunac", 0, wsi.addShape(box3x3, 0) );
			assertEquals( "Zrzucam drugi ksztalt, nic nie powinno dac sie usunac", 0, wsi.addShape(box3x3, 3) );
			assertEquals( "Dodaje ksztalt, ktory powinien usunac 2 linie", 2, wsi.addShape(line2V, 6) );
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wykryto nieoczekiwany wyjatek " + e.getMessage());
		}
	}

	@Test
	public void test2() {
		try {
			assertEquals( "Zrzucam pierwszy ksztalt, nic nie powinno dac sie usunac", 0, wsi.addShape(box2x2, 0) );
			assertEquals( "Zrzucam drugi ksztalt, nic nie powinno dac sie usunac", 0, wsi.addShape(box2x2, 5) );
			assertEquals( "Dodaje ksztalt, ktory powinien usunac 2 linie", 2, wsi.addShape(box3x3, 2) );
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wykryto nieoczekiwany wyjatek " + e.getMessage());
		}
	}

	@Test
	public void test3() {
		try {
			assertEquals( "Usuwamy 2 linie", 2, wsi.addShape(line2H, 0) );
			assertEquals( "Ponownie usuwamy", 2, wsi.addShape(line2H, 0) );
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wykryto nieoczekiwany wyjatek " + e.getMessage());
		}
	}

	@Test
	public void test4() {
		try {
			assertEquals( "Zrzucam pierwszy ksztalt, nic nie powinno dac sie usunac", 0, wsi.addShape(box3x3, 0) );
			assertEquals( "Zrzucam drugi ksztalt, nic nie powinno dac sie usunac", 0, wsi.addShape(box3x3, 3) );
			assertEquals( "Dodaje ksztalt, ktory powinien usunac 2 linie", 2, wsi.addShape(line2V, 6) );
			assertEquals( "Dodaje ksztalt, ktory powinien usunac 1 linie", 1, wsi.addShape(line2V, 6) );
			assertEquals( "Zrzucam ksztalt, nic nie powinno dac sie usunac", 0, wsi.addShape(box3x3, 0) );
			assertEquals( "Zrzucam ksztalt, powinna zniknac jedna linia", 1, wsi.addShape(box3x3, 3) );
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wykryto nieoczekiwany wyjatek " + e.getMessage());
		}
	}
}