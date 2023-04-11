
public interface BankEngineInterface {
	/**
	 * Tworzy nowe konto w systemie. Poczatkowy stan konta to 0.
	 * @return Unikalny identyfikator - numer konta
	 */
	int openAccount();
	
	/**
	 * Zwraca stan konta o podanym numerze. Bledny numer konta powoduje
	 * zwrocenie Integer.MIN_VALUE
	 * @param accountID numer konta
	 * @return stan konta
	 */
	int get( int accountID );
	
	/**
	 * Ustwia stan konta o podanym numerze. Bledne dane sa ignorowane.
	 * 
	 * @param accountID numer konta
	 * @param value nowy stan konta
	 */
	void set( int accountID, int value );
}