import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class BankEngine implements BankEngineInterface {

	public static long OPEN_ACCOUNT_SLEEP = 50;
	public static long GET_SLEEP = 100;
	public static long SET_SLEEP = 125;
	private static int ACCOUNTS = 1000;

	private AtomicBoolean openAccountTester = new AtomicBoolean(false);
	private AtomicBoolean openAccountError = new AtomicBoolean(false);

	private AtomicIntegerArray simultaneousSetThreadsPerAccount = new AtomicIntegerArray(
			ACCOUNTS);
	private AtomicInteger simultaneousSetThreads = new AtomicInteger(0);
	private AtomicInteger maxSimultaneousSetThreads = new AtomicInteger(0);
	private AtomicBoolean setThreadsError = new AtomicBoolean(false);

	private AtomicInteger accountsCounter = new AtomicInteger(0);
	private AtomicIntegerArray accounts = new AtomicIntegerArray(ACCOUNTS);
	private AtomicInteger threadsCounter = new AtomicInteger(0);
	private AtomicInteger maxThreadsCounter = new AtomicInteger(0);

	private void sleep(long msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int openAccount() {
		if (!openAccountTester.compareAndSet(false, true)) {
			openAccountError.set(true);
		}

		sleep(OPEN_ACCOUNT_SLEEP);

		if (!openAccountTester.compareAndSet(true, false)) {
			System.out
					.println("BLAD: Wykryto > 1 watek wykonujacy rownoczesnie openAccount");
			openAccountError.set(true);
		}

		return accountsCounter.incrementAndGet();
	}

	@Override
	public int get(int accountID) {
		int ths = threadsCounter.incrementAndGet();

		if (ths > maxThreadsCounter.get()) {
			synchronized (this) {
				if (ths > maxThreadsCounter.get())
					maxThreadsCounter.set(ths);
			}
		}

		int tmp = accounts.get(accountID);
		sleep(GET_SLEEP);

		threadsCounter.decrementAndGet();
		return tmp;
	}

	@Override
	public void set(int accountID, int value) {
		int ths = simultaneousSetThreadsPerAccount.incrementAndGet(accountID);
		int max = simultaneousSetThreads.incrementAndGet();

		if (max > maxSimultaneousSetThreads.get()) {
			synchronized (this) {
				if (max > maxSimultaneousSetThreads.get()) {
					maxSimultaneousSetThreads.set(max);
				}
			}
		}

		if (ths > 1) {
			System.out
					.println("BLAD: Wykryto > 1 watek wykonujacy set na tym samym koncie");
			setThreadsError.set(true);
		}

		sleep(SET_SLEEP);

		simultaneousSetThreads.decrementAndGet();
		simultaneousSetThreadsPerAccount.decrementAndGet(accountID);
		accounts.set(accountID, value);
	}

	public boolean ok() {
		boolean result = true;
		if ( setThreadsError.get() ) {
			System.out.println( "BLAD w obsludze rownoczesnych operacji set na jednym koncie");
			result = false;
		}
		if ( openAccountError.get() ) {
			System.out.println( "BLAD w obsludze rownoczesnego zakladania kont");
			result = false;
		}
		if ( maxSimultaneousSetThreads.get() < 2 ) {
			System.out.println( "BLAD: brak jednoczesnych operacji na roznych kontach");
			result = false;
		}
		return result;
	}
	
}