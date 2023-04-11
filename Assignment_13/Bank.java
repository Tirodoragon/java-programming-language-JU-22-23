import java.util.HashMap;
import java.util.Map;

class Bank implements BankInterface {
	private BankEngineInterface bei;
	private Map<Integer, Object> accounts = new HashMap<>();
	private Map<Integer, Integer> money = new HashMap<>();
	
	@Override
	public void setBankEngineInterface(BankEngineInterface bei) {
		this.bei = bei;
	}
	
	@Override
	public int openAccount() {
		int accountID;
		synchronized (this) {
			accountID = bei.openAccount();
		}
		accounts.put(accountID, new Object());
		money.put(accountID, 0);
		return accountID;
	}
	
	@Override
	public void add(int accountID, int value) {
		synchronized (accounts.get(accountID)) {
			money.put(accountID, money.get(accountID) + value);
			bei.set(accountID, money.get(accountID));
		}
	}
	
	@Override
	public void move(int accountSource, int accountDestination, int value) {
		add(accountSource, -value);
		add(accountDestination, value);
	}
	
	@Override
	public int get(int accountID) {
		if (accounts.get(accountID) != null) {
			synchronized (accounts.get(accountID)) {
				return bei.get(accountID);
			}
		} else {
			return Integer.MIN_VALUE;
		}
	}
	
	@Override
	public int sum() {
		int sum = 0;
		for (var mapen : accounts.entrySet()) {
			sum += get(mapen.getKey());
		}
		return sum;
	}
}