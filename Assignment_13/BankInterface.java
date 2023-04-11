
public interface BankInterface {
	
	/**
	 * Metoda podlaczajaca interfejs banku do obiektu realizujacego operacje
	 * na kontach (BankEngine). Metoda wykonywana zawsze jako pierwsza, poprzedza inne operacje.
	 * 
	 * @param bei - referencja do obiektu zgodnego z BankEngineInterface
	 */
	void setBankEngineInterface( BankEngineInterface bei );
	
	/**
	 * Otwiera konto w systemie. Konto otrzymuje unikalny identyfikator - numer konta.
	 * @return numer konta
	 */
	int openAccount();
	
	/**
	 * Dodaje do konta pieniadze. Bledny numer konta powoduje zignorowanie operacji
	 * @param accountID numer konta 
	 * @param value wplacana kwota
	 */
	void add( int accountID, int value );
	
	/**
	 * Przelew pomiedzy kontami. Bledne dane przelewu (bledne numery kont) powoduja
	 * zignornowanie operacji.
	 * @param accountSource konto, z ktorego pieniadze sa pobierane. Uwaga: moze pojawic sie debet
	 * @param accountDestination, konto na ktore pieniadze sa przelewane
	 * @param value kwota zlecenia
	 */
	void move( int accountSource, int accountDestination, int value );
	
	/**
	 * Zwraca stan konta o podanym numerze, Integer.MIN_VALUE w przypadku braku konta.
	 * @param accountID numer konta
	 * @return stan konta
	 */
	int get( int accountID );
	
	/**
	 * Suma stanu kont.
	 * @return suma stanow kont.
	 */
	int sum();
}