import java.util.Map;
import java.util.HashMap;

class Shop implements ShopInterface {
    Map<String, Integer> asortyment = new HashMap<>();
    Map<String, Object> blokady = new HashMap<>();
    @Override
    public void delivery(Map<String, Integer> goods) {
        for (var towar : goods.keySet()) {
        	synchronized (this) {
        		if (!blokady.containsKey(towar)) {
        			blokady.put(towar, new Object());
        		}
        	}
            synchronized (blokady.get(towar)) {
                if (asortyment.get(towar) == null) {
                        asortyment.put(towar,  goods.get(towar));
                }
                else {
                    asortyment.put(towar,  asortyment.get(towar) + goods.get(towar));
                }
            }
            synchronized (blokady.get(towar)) {
                blokady.get(towar).notifyAll();
            }
        }
    }
    
    @Override
    public boolean purchase(String productName, int quantity) {
        if (!blokady.containsKey(productName)) {
            blokady.put(productName, new Object());
        }
        synchronized (blokady.get(productName)) {
            if (asortyment.get(productName) == null || asortyment.get(productName) < quantity) {
                try {
                    blokady.get(productName).wait();
                } catch (Exception w) {}
                if (asortyment.get(productName) == null || asortyment.get(productName) < quantity) {
                    return false;
                }
                else {
                    asortyment.put(productName,  asortyment.get(productName) - quantity);
                    return true;
                }
            }
            else {
                asortyment.put(productName, asortyment.get(productName) - quantity);
                return true;
            }
        }
    }
    
    @Override
    public Map<String, Integer> stock() {
        return asortyment;
    }
}