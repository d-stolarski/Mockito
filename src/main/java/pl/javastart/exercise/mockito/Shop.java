package pl.javastart.exercise.mockito;

import java.util.Map;

public class Shop {

    private int money;
    private Map<Item, Integer> stock;

    public Shop(int money, Map<Item, Integer> stock) {
        this.money = money;
        this.stock = stock;
    }

    void playCashSound() {
        /* zakładamy, że ta metoda odtwarza dźwięk https://www.youtube.com/watch?v=Wj_OmtqVLxY, nie musimy jej implementować,
        sprawdzamy tylko czy została uruchomiona */
    }

    public boolean hasItem(Item item) {
        boolean score = stock.containsKey(item);
        return score;
    }

    public Item findItemByName(String itemName) {
        for (Item key : stock.keySet()) {
            if (key.getName().equals(itemName)) {
                return key;
            }
        }
        return null;
    }

    public void decreaseItemNumber(Map<Item, Integer> stock){

    }

    public int getMoney() {
        return money;
    }

    public Map<Item, Integer> getStock() {
        return stock;
    }
    public void setMoney(int money) {
        this.money = money;
    }
}
