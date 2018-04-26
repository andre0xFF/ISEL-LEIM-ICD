package store;

import org.junit.jupiter.api.Test;
import store.item.Item;
import store.item.Size;
import store.item.Type;

import java.util.HashMap;

class StoreTest {
    private Store store;


    private StoreTest() {
        HashMap<Integer, Item> items = new HashMap<Integer, Item>() {{
            put(123, new Item(123, Gender.MALE, Type.PANTS, Size.Apperal.M, "Nike", "xpto", null, null));
            put(124, new Item(124, Gender.FEMALE, Type.SHIRT, Size.Apperal.S, "Nike", "xpto", null, null));
            put(125, new Item(125, Gender.FEMALE, Type.PANTS, Size.Apperal.S, "Merrel", "xpto", null, null));
        }};

        HashMap<Integer, Double> prices = new HashMap<Integer, Double>() {{
            put(123, 50.0);
            put(124, 35.0);
        }};

        HashMap<Integer, Integer> stocks = new HashMap<Integer, Integer>() {{
            put(123, 20);
            put(124, 22);
        }};

        this.store = new Store(items, prices, stocks);

//        Serializer serializer = new Persister();
//        File file = new File("./data/mock.xml");
//
//        try {
//            serializer.write(store, file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    Item add_item() {
        int id = 999;
        double price = 20;
        int stock = 10;
        Item item = new Item(id, Gender.FEMALE, Type.PANTS, Size.Apperal.S, "Merrel", "xpto", null, null);

        this.store.add_item(item, price, stock);

        Item target = this.store.get_item(id);

        assert target != null;
        assert target.equals(item);
        assert price == this.store.get_item_price(target);
        assert stock == this.store.get_item_stock(target);

        return target;
    }


    @Test
    void remove_item() {
        Item item = this.add_item();

        this.store.add_item(item, 100, 100);
        this.store.remove_item(item);

        assert this.store.get_item(item.id) == null;
        assert this.store.get_item_stock(item) < 0;
        assert this.store.get_item_price(item) < 0;
    }

    @Test
    void modify_item_price() {
        double[] prices = new double[2];
        prices[0] = 30;

        Item item = this.add_item();

        this.store.modify_item_price(item, prices[0]);
        prices[1] = this.store.get_item_price(item);
        this.store.remove_item(item);

        assert prices[0] == prices[1];
    }

    @Test
    void modify_item_stock() {
    }

    @Test
    void get_item_stock() {
    }

    @Test
    void get_item_price() {
    }

    @Test
    void add_sale() {
    }
}