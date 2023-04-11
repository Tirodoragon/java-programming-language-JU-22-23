import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

class Wezel {
	public int x;
	public int y;
	
	public Wezel(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Krawedz {
	Wezel a;
	Wezel b;
	int waga;
	
	public Krawedz(Wezel a, Wezel b, int waga) {
		this.a = a;
		this.b = b;
		this.waga = waga;
	}
}

class Graf {
	int gora;
	int lewo;
	int prawo;
	int dol;
	
	public double proporcja() {
		return (double) (prawo - lewo) / (double) (gora - dol);
	}
}

class Panel extends JPanel {
	private static final long serialVersionUID = 60029722639739556L;
	
	Graf graf;
	List<Wezel> wezly;
	List<Krawedz> krawedzie;
	Map<Integer, Double> wagi;
	
	public Panel(Graf graf, List<Wezel> wezly, List<Krawedz> krawedzie, Map<Integer, Double> wagi) {
		this.graf = graf;
		this.wezly = wezly;
		this.krawedzie = krawedzie;
		this.wagi = wagi;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		double czynnikX = 1;
		double czynnikY = 1;
		int marginesX = 0;
		int marginesY = 0;
		float gruboscWezla = 3;
		int promienWezla = 15;
		double czynnikKrawedzi = 1;
		if (getWidth() / (double) getHeight() < graf.proporcja()) {
			czynnikX = getWidth() * 0.9 / (double) (graf.prawo + 1);
			marginesX = (int) ((double) getWidth() * 0.1 / 2.0);
			czynnikY = (getWidth() / graf.proporcja()) * 0.9 / (double) (graf.gora + 1);
			marginesY = (int) ((double) (getHeight() - (getWidth() / graf.proporcja())) / 2.0) + (int) ((double) getHeight() * 0.1 / 2.0);
			gruboscWezla = (float) (getWidth() * 0.005);
			promienWezla = (int) (getWidth() * 0.05);
			czynnikKrawedzi = (double) (getWidth() * 0.002);
		}
		else {
			czynnikX = (getHeight() * graf.proporcja()) * 0.9 / (double) (graf.prawo + 1);
			marginesX = (int) ((double) (getWidth() - (getHeight() * graf.proporcja())) / 2.0) + (int) ((double) getWidth() * 0.1 / 2.0);
			czynnikY = getHeight() * 0.9 / (double) (graf.gora + 1);
			marginesY = (int) ((double) getHeight() * 0.1 / 2.0);
			gruboscWezla = (float) (getHeight() * 0.005);
			promienWezla = (int) (getHeight() * 0.05);
			czynnikKrawedzi = (double) (getHeight() * 0.002);
		}
		for (Wezel wezel : wezly) {
			g2d.setStroke(new BasicStroke (gruboscWezla));
			g2d.drawOval((int) (czynnikX * wezel.x) + marginesX - promienWezla / 2, (int) (czynnikY * wezel.y) + marginesY - promienWezla / 2, promienWezla, promienWezla);
		}
		for (Krawedz krawedz : krawedzie) {
			int p1x = (int) (czynnikX * krawedz.a.x) + marginesX;
			int p1y = (int) (czynnikY * krawedz.a.y) + marginesY;
			int p2x = (int) (czynnikX * krawedz.b.x) + marginesX;
			int p2y = (int) (czynnikY * krawedz.b.y) + marginesY;
			g2d.setStroke(new BasicStroke ((float) (wagi.get (krawedz.waga) * (float) czynnikKrawedzi)));
			g2d.drawLine(p1x, p1y, p2x, p2y);
		}	
	}
}

public class Start {
	JFrame okno;
	JButton przycisk;
	Panel rysunek;
	
	Graf graf = new Graf();
	List<Wezel> wezly = new LinkedList<>();
	List<Krawedz> krawedzie = new LinkedList<>();
	Map<Integer, Double> wagi = new HashMap<>();
	
	class ladowaniePliku implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			int rezultat = jfc.showOpenDialog(okno);
			if (rezultat == JFileChooser.APPROVE_OPTION) {
				pobieranieDanych(jfc.getSelectedFile().getAbsolutePath());
				rysunek.repaint();
			}
		}
	}
	
	void pobieranieDanych(String dane) {
		wezly.clear();
		krawedzie.clear();
		wagi.clear();
		
		Scanner in = null;
		try {
			in = new Scanner(new File(dane));
		}
		catch (Exception e) {}
		int wartoscOperacji = in.nextInt();
		int x;
		int y;
		int waga;
		for (int i = 0; i < wartoscOperacji; i++) {
			x = in.nextInt();
			y = in.nextInt();
			if (i == 0) {
				graf.lewo = x;
				graf.prawo = x;
				graf.gora = y;
				graf.dol = y;
			}
			if (x < graf.lewo) {
				graf.lewo = x;
			}
			else if (x > graf.prawo) {
				graf.prawo = x;
			}
			if (y < graf.dol) {
				graf.dol = y;
			}
			else if (y > graf.gora) {
				graf.gora = y;
			}
			wezly.add(new Wezel(x, y));
		}
		wartoscOperacji = in.nextInt();
		for (int i = 0; i < wartoscOperacji; i++) {
			x = in.nextInt();
			y = in.nextInt();
			waga = in.nextInt();
			krawedzie.add(new Krawedz(wezly.get(x - 1), wezly.get(y - 1), waga));
		}
		for (Wezel wezel : wezly) {
			wezel.x -= graf.lewo;
			wezel.y -= graf.dol;
		}
		graf.prawo -= graf.lewo;
		graf.gora -= graf.dol;
		graf.lewo = 0;
		graf.dol = 0;
		graf.prawo--;
		for (Wezel wezel : wezly) {
			wezel.y -= (int) (2.0 * ((double) wezel.y - (double) (graf.gora - graf.dol) / 2.0));
		}
		graf.gora--;
		in.close();
		int wagaMax = 0;
		for (Krawedz krawedz : krawedzie) {
			if (krawedz.waga > wagaMax)
				wagaMax = krawedz.waga;
		}
		for (Krawedz krawedz : krawedzie) {
			wagi.put(krawedz.waga, (double) (krawedz.waga / (double) wagaMax * 11));
		}
	}
	
	public Start() {
		okno = new JFrame("Moje okienko do rysowania grafu");
		przycisk = new JButton("Load");
		przycisk.addActionListener(new ladowaniePliku());
		rysunek = new Panel(graf, wezly, krawedzie, wagi);
		okno.getContentPane().add(BorderLayout.NORTH, przycisk);
		okno.getContentPane().add(BorderLayout.CENTER, rysunek);
		okno.setSize(450, 300);
		okno.setVisible(true);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Start();
			}
		});
	}
}