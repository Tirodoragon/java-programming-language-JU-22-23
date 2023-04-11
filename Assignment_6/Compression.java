import static java.util.stream.Collectors.*;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

class Compression implements CompressionInterface {
	Map<String, String> heading = new HashMap<>();
	public Map<String, String> getHeader() {
		return heading;
	}
	
	Map<String, String> reverseHeading = new HashMap<>();
	List<String> input = new LinkedList<>();
	int output;
	public String getWord() {
		if (reverseHeading.get(input.get(output)) == null) {
			return "1" + input.get(output++);
		}
		else {
			return reverseHeading.get(input.get(output++));
		}
	}
	
	int inputLength;
	Map<String, Integer> wordsAmount = new HashMap<>();
	public void addWord(String word) {
		input.add(word);
		
		inputLength += word.length();
		
		if (wordsAmount.get(word) != null) {
			wordsAmount.put(word, wordsAmount.get(word) + 1);
		} 
		else {
			wordsAmount.put(word, 1);
		}
	}
	
	public void compress() {
		Map<String, Integer> descendingWordsAmount = wordsAmount.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (firstElement, secondElement) -> secondElement, LinkedHashMap::new));
		
		int compressed;
		int optimal = inputLength;
		int optimalKeyLength = 0;
		int optimalWordsAmount = 0;
		int i = 1;
		while (i < descendingWordsAmount.keySet().toArray()[0].toString().length()) {
			int j = 1;
			while (j <= Math.pow(2, i - 1)) {
				compressed = inputLength;
				int k = 0;
				while (k < descendingWordsAmount.size()) {
					String word = descendingWordsAmount.keySet().toArray()[k].toString();
					int wordOccurrences = descendingWordsAmount.get(descendingWordsAmount.keySet().toArray()[k]);
					
					if (k < j) {
						compressed -= word.length() * wordOccurrences;
						compressed += (i + word.length()) + wordOccurrences * i;
					}
					else {
						compressed += wordOccurrences;
					}
					k++;
				}
				
				if (compressed < optimal) {
					optimal = compressed;
					optimalKeyLength = i;
					optimalWordsAmount = j;
				}
				j++;
			}
			i++;
		}
		
		if (optimal < inputLength) {
			int l = 0;
			while (l < optimalWordsAmount) {
				heading.put(String.format("%" + optimalKeyLength + "s", Integer.toBinaryString(l)).replaceAll(" ", "0"), descendingWordsAmount.keySet().toArray()[l].toString());
				l++;
			}
			
			reverseHeading = heading.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
		}
	}
}