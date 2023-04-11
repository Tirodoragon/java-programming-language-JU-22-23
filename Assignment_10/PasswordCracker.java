import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PasswordCracker implements PasswordCrackerInterface {
	@Override
	public String getPassword(String host, int port) {
		Socket so = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		
		try {
			so = new Socket(host, port);
			br = new BufferedReader(new InputStreamReader(so.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(so.getOutputStream()));
			
			pw.println("Program");
			pw.flush();
			
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				
				if (line.startsWith("schema : ")) {
					
				}
				else if (line.equals("Zaczynamy! Zgadnij hasło.")) {
					
				}
				else if (line.equals("+OK")) {
					
				}
				else if (line.startsWith("To nie hasło, odgadnięto ")) {
					
				}
			}
		}
		catch (UnknownHostException exc) {
			System.out.println("Nie znam takiego hosta " + host);
		}
		catch (ConnectException exc) {
			System.out.println("Blad polaczenia z serwerem " + host);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
	