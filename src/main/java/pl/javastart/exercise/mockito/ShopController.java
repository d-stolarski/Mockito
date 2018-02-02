package pl.javastart.exercise.mockito;

import java.util.Map;

public class ShopController {

    private Shop shop;



    public ShopController(ShopRepository shopRepository) {
        shop = shopRepository.findShop();
    }

    public void sellItem(Human human, Item item) {

        Map<Item, Integer> stock = shop.getStock();

        if (shop.hasItem(item)) {
            item = shop.findItemByName(item.getName());
            if (item.getAgeRestriction() > human.getAge()) {
                throw new TooYoungException();
            }
            if (human.getJob().toLowerCase().equals("policeman") && !(item.isLegal())){
                throw new OutOfStockException();
            }
            if ((human.getMoney() < item.getPrice()) || (human.getMoney() == 0)){
                throw new OutOfMoneyException();
            } else {
                human.setMoney(human.getMoney() - item.getPrice());
                shop.setMoney(shop.getMoney() + item.getPrice());
                int itemNumber = stock.get(item);
                stock.replace(item, itemNumber, itemNumber - 1);
                if(itemNumber <= 1){
                    stock.remove(item);
                }
                shop.playCashSound();
            }
        } else {
            throw new OutOfStockException();
        }
    }
}
