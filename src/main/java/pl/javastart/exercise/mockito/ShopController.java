package pl.javastart.exercise.mockito;

public class ShopController {

    private Shop shop;

    public ShopController(ShopRepository shopRepository) {
        shop = shopRepository.findShop();

    }

    public void sellItem(Human human, Item item) {

        if (shop.hasItem(item)) {
            item = shop.findItemByName(item.getName());
            if (item.getAgeRestriction() > human.getAge()) {
                throw new TooYoungException();
            }
            if (human.getJob().equals("policeman") && !(item.isLegal())){
                throw new TooYoungException();
            }
            if (human.getMoney() < item.getPrice()){
                throw new OutOfMoneyException();
            } else {
                human.setMoney(human.getMoney() - item.getPrice());
                shop.setMoney(shop.getMoney() + item.getPrice());
            }

        } else {
            throw new OutOfStockException();
        }

    }


}
