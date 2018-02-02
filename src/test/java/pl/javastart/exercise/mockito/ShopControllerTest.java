package pl.javastart.exercise.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ShopControllerTest {

    @Mock
    ShopRepository shopRepository;
    @Mock
    SoundPlayer soundPlayer;

    private ShopController shopController;
    private Shop shop;
    private Map<Item, Integer> stock;
    private Item item;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        stock = new HashMap<>();
        stock.put(new Item("Piwo", 18, 4, true), 5);
        stock.put(new Item("Narkotyki", 18, 4, false), 50);
        stock.put(new Item("Cukier", 5, 5, true), 3);

        shop = new Shop(0, stock);

        when(shopRepository.findShop()).thenReturn(shop);

        shopController = new ShopController(shopRepository);
    }

    @Test(expected = TooYoungException.class)
    public void shouldNotSellBeerToYoungling() {
        // given
        Human student = new Human("George", 17, "Student", 1000);

        //when
        item = shop.findItemByName("Piwo");
        shopController.sellItem(student, item);
    }

    @Test(expected = OutOfStockException.class)
    public void shouldNotSellIllegalItemToPoliceman() {
        // given
        Human policeman = new Human("David", 25, "Policeman", 5000);

        //when
        Item drugs = shop.findItemByName("Narkotyki");
        shopController.sellItem(policeman, drugs);

    }

    @Test
    public void shouldReturnItemIfItemIsInStock() {
        // when
        Item item = shop.findItemByName("Piwo");

        //then
        assertEquals(item.getName(), "Piwo");
    }

    @Test
    public void shouldReturnTrueIfItemIsInStock() {
        // when
        boolean isItem = shop.hasItem(shop.findItemByName("Piwo"));

        //then
        assertEquals(true, isItem);
    }

    @Test
    public void shouldDecreaseMoneyAfterHumanBuyItem() {
        //given
        Human miner = new Human("Pawel", 20, "Miner", 5000);
        Item beer = shop.findItemByName("Piwo");

        //when
        shopController.sellItem(miner, beer);

        //then
        assertEquals(miner.getMoney(), 4996);
    }

    @Test
    public void shouldAddMoneyToShopAfterBuyItemByCustomer() {

        //given
        Human actor = new Human("John", 45, "Actor", 20000);
        Item beer = shop.findItemByName("Piwo");

        //when
        for (int i = 0; i < 5; i++) {
            shopController.sellItem(actor, beer);
        }

        //then
        assertEquals(shop.getMoney(), 20);

    }

    @Test
    public void shouldDecreaseNumberOfItemAfterSale() {
        //given
        Human actor = new Human("John", 45, "Actor", 20000);
        Item beer = shop.findItemByName("Piwo");
        Item drugs = shop.findItemByName("Narkotyki");

        //when
        shopController.sellItem(actor, beer);
        shopController.sellItem(actor, drugs);
        shopController.sellItem(actor, drugs);
        int numberOfBeers = stock.get(beer);
        int numberOfDrugs = stock.get(drugs);

        //then
        assertEquals(numberOfBeers, 4);
        assertEquals(numberOfDrugs, 48);
    }

    @Test
    public void shouldReturnNullForItemWithZeroNumber() {
        //given
        Human actor = new Human("John", 45, "Actor", 20000);
        Item sugar = shop.findItemByName("Cukier");

        //when
        for (int i = 0; i <= 2; i++) {
            shopController.sellItem(actor, sugar);
        }

        //then
        assertFalse(stock.containsKey(sugar));
    }

    @Test
    public void shouldCallCashShoundMethodOneTime() {

        //when
        soundPlayer.playCashSound();

        //then
        verify(soundPlayer, times(1)).playCashSound();
    }

}
