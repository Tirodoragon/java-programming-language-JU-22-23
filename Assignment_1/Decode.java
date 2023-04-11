class Decode extends DecoderInterface {
	private int numberOfBits = 0;
	private int numberOfOnes = -1;
	private StringBuilder decodedData = new StringBuilder();
	
	@Override
	public void input(int bit) {
		if (bit == 1) {
			numberOfBits++;
		}
		else if (numberOfBits != 0) {
			if (numberOfOnes == -1) {
				numberOfOnes = numberOfBits;
			}
			
			decodedData.append(numberOfBits / numberOfOnes - 1);
			numberOfBits = 0;
		}
	}
	
	@Override
	public String output() {
		return decodedData.toString();
	}
	
	@Override
	public void reset() {
		numberOfBits = 0;
		numberOfOnes = -1;
		decodedData.setLength(0);
	}
}