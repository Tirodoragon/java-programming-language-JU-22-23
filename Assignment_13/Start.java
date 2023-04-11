import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Start {
	private static BankInterface bi = new Bank();
	private static BankEngine be = new BankEngine();
	private final static int THREADS = 24;
	private final static int INI_VALUE = 20;

	private static Thread[] threads = new Thread[THREADS];
	private static KlientBanku[] klienci = new KlientBanku[THREADS];
	private static CyclicBarrier cb = new CyclicBarrier(THREADS);

	private static long SERIAL_EXECUTION_MAX_TIME;
	
	static {
		bi.setBankEngineInterface(be);
		
		// szacowanie czasu
		SERIAL_EXECUTION_MAX_TIME = BankEngine.OPEN_ACCOUNT_SLEEP * THREADS +  // otwarcie kont
				2 * 2 * ( BankEngine.GET_SLEEP + BankEngine.SET_SLEEP ) + // zasilenie konta
				4 * ( 2 * INI_VALUE + 1 ) * ( BankEngine.GET_SLEEP + BankEngine.SET_SLEEP ); // przelewy
	}

	private static class KlientBanku implements Runnable {
		private int id;
		private int threadID;

		public int getID() {
			return id;
		}

		public KlientBanku(int threadID) {
			this.threadID = threadID;
		}

		@Override
		public void run() {
			try {
				cb.await();
				id = bi.openAccount();

				if (bi.get(id) != 0) {
					System.out
							.println("Poczatkowy stan banku powinien wynosic 0");
				}

				cb.await();
				bi.add(id, INI_VALUE);
				cb.await();
				bi.add(id, INI_VALUE);

				int secondAccount;

				// przelewy z kont 0 i 1 do innych klientow
				if ( threadID > 1 ) {
					secondAccount = klienci[ threadID % 2 ].getID();
					cb.await();
					for (int i = 0; i < INI_VALUE; i++) {
						bi.move(secondAccount, id, 1);
					}					
					cb.await();
					bi.move(id, secondAccount, INI_VALUE); // zwrot pieniedzy
				} else {
					cb.await();
				}
				
				// przelewy parami z kont A->B i B->A rownoczesnie
				if ( ( threadID < THREADS - 1 ) && ( threadID % 2 == 0) ) {
					secondAccount = klienci[threadID + 1].getID();
				} else {
					secondAccount = klienci[threadID - 1].getID();
				}
				cb.await();
				for (int i = 0; i < INI_VALUE; i++) {
					bi.move(id, secondAccount, 1);
				}
				
				cb.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

		}
	}

	private static void startThreads() {
		for (int i = 0; i < THREADS; i++) {
			klienci[i] = new KlientBanku(i);
			threads[i] = new Thread(klienci[i]);
			threads[i].setDaemon(true);
			threads[i].start();
		}
	}

	private static void joinThreads() {
		try {
			Thread.sleep(SERIAL_EXECUTION_MAX_TIME);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < THREADS; i++)
			try {
				threads[i].join(1000); // czekamy max 1 sekunde na join
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	private static int sum() {
		int sum = 0;
		for (int i = 0; i < THREADS; i++)
			sum += bi.get(klienci[i].getID());
		return sum;
	}

	public static void main(String[] args) {
		
		System.out.println( "Szacowany max czas pracy: " + SERIAL_EXECUTION_MAX_TIME );
		
		startThreads();
		joinThreads();

		// sa 2 zasilenia konta na kwote INI_VALUE
		if (sum() != THREADS * INI_VALUE * 2) {
			System.out.println("BLAD: nie zgadza sie stan skarbca!");
			System.out.println("Powinno byc : " + (2*THREADS * INI_VALUE)
					+ " a jest " + sum());
		}

		if (be.ok()) {
			System.out.println("__Testy_na_poziomie_BankEngine_OK__");
		} else {
			System.out.println("!! Wykryto bledy zgloszone przez BankEngine !!");
		}

	}

}