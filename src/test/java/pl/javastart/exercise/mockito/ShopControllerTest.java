package pl.javastart.exercise.mockito;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ShopControllerTest {

    @Mock
    ShopRepository shopRepository;

    private ShopController shopController;
    private Shop shop;
    private Map<Item, Integer> stock;
    private Item item;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Map<Item, Integer> stock = new HashMap<>();
        stock.put(new Item("Piwo", 18, 4, true), 5);
        stock.put(new Item("Narkotyki", 18, 4, false), 5);

        Shop shop = new Shop(0, stock);

        when(shopRepository.findShop()).thenReturn(shop);

        shopController = new ShopController(shopRepository);
    }

    @Test(expected = TooYoungException.class)
    public void shouldNotSellBeerToYoungling() {
        // given
        Human human = new Human("George", 17,"Student",0);
        Map<Item, Integer> stock = new HashMap<>();
        stock.put(new Item("Piwo", 18, 4, true), 5);
        stock.put(new Item("Narkotyki", 18, 4, false), 5);
        item = shop.findItemByName("Piwo");
        // when
        shopController.sellItem(human, item);
    }

    @Test(expected = TooYoungException.class)
    public void shouldNotSellIllegalItemToPoliceman() {
        // given
        Human policeman = new Human("David", 25,"Policeman",5000);
        Item drugs = shop.findItemByName("Narkotyki");
        //when
        shopController.sellItem(policeman, drugs);
    }

    @Test
    public void shouldReturnItemIfItemIsInStock() {
        // when
        Item isItem = shop.findItemByName("Piwo");
        //then
       assertEquals(isItem.getName(),"Piwo");
    }

}
