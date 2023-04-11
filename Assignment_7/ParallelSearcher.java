class ParallelSearcher implements ParallelSearcherInterface {
	class Helper extends Thread {
		HidingPlaceSupplier hidingPlaceSet;
		Object blockade;	
		public Helper (HidingPlaceSupplier hidingPlaceSetH, Object blockadeH) {
			hidingPlaceSet = hidingPlaceSetH;
			blockade = blockadeH;
		}
		
		public void run () {
			HidingPlaceSupplier.HidingPlace hidingPlace = hidingPlaceSet.get();
			while (hidingPlace != null) {
				synchronized (hidingPlace) {
					if (hidingPlace.isPresent()) {
						synchronized (blockade) {
							valueSum += hidingPlace.openAndGetValue();
						}
					}
				}
				hidingPlace = hidingPlaceSet.get();
			}
		}
	}
	
	double valueSum;
	public void set(HidingPlaceSupplierSupplier supplier) {
		HidingPlaceSupplier hidingPlaceSet = supplier.get(0);
		while (hidingPlaceSet != null) {
			Thread[] threads = new Thread[hidingPlaceSet.threads()];
			
			int i = 0;
			while (i < threads.length) {
				threads[i] = new Helper(hidingPlaceSet, this);
				threads[i].start();
				i++;
			}
			
			i = 0;
			while (i < threads.length) {
				try {
					threads[i].join();
				} 
				catch (Exception exception) {}
				i++;
			}
			
			hidingPlaceSet = supplier.get(valueSum);
			valueSum = 0;
		}
	}
}	