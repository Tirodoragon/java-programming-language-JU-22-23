
public interface PasswordCrackerInterface {
	/**
	 * Metoda zwraca hasĹo do serwisu znajdujÄcego siÄ na komputerze host i
	 * oczekujÄcego na poĹÄczenia na porcie o numerze port.
	 * 
	 * @param host adres serwera
	 * @param port numer portu
	 * @return hasĹo
	 */
	public String getPassword(String host, int port);
}