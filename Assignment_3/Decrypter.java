import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Decrypter implements DecrypterInterface {
	private String coded;
	private String name = "WydziałFizyki,AstronomiiiInformatykiStosowanej";
	
	private ArrayList<Character> tested = new ArrayList<Character>();
	
	private Map<Character, Character> encryption = new HashMap<Character, Character>();
	private Map<Character, Character> decryption = new HashMap<Character, Character>();
	
	@Override
	public Map<Character, Character> getCode() {
		for(Map.Entry<Character, Character> entry : decryption.entrySet()){
            encryption.put(entry.getValue(), entry.getKey());
		}
		return encryption;
	}

	@Override
	public Map<Character, Character> getDecode() {
		return decryption;
	}
	
	@Override
	public void setInputText(String encryptedDocument) {
		encryption.clear();
		decryption.clear();
		
		if(encryptedDocument == null) {
			return;
		}
		
		coded = encryptedDocument.replaceAll("\\s+","");
		for(int i = 0; i <= coded.length() - name.length(); i++) {
			if(checkName(i)) {
				break;
			}
		}
	}
	
	private boolean checkName(int beginning) {
		tested.clear();
		for(int i = beginning; i < name.length() + beginning; i++) {
			char n = name.charAt(i - beginning);
			char c = coded.charAt(i);
			if((c == ',') || (n == ',')) {
				if(c != n) {
					return false;
				}
				else {
					continue;
				}
			}
			if(tested.contains(c)) {
				char d = decryption.get(c);
				if(d != n) {
					decryption.clear();
					encryption.clear();
					
					return false;
				}
			}
			else {
				tested.add(c);
				
				decryption.put(c, n);
			}
		}
		return true;
	}
}