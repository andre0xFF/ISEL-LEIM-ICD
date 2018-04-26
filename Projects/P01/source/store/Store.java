package store;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import store.item.Item;

import java.util.ArrayList;
import java.util.HashMap;


@Root
public class Store {
    @ElementMap(entry="item", key="key", attribute=true)
    private HashMap<Integer, Item> items;
    @ElementMap(entry="item", key="key", attribute=true)
    private HashMap<Integer, Double> prices;
    @ElementMap(entry="item", key="key", attribute=true)
    private HashMap<Integer, Integer> stocks;
    @ElementList
    private ArrayList<Sale> sales = new ArrayList<>();


    public Store(HashMap<Integer, Item> items, HashMap<Integer, Double> prices, HashMap<Integer, Integer> stocks) {
        this.items = items;
        this.prices = prices;
        this.stocks = stocks;
    }


    public void add_item(Item item, double price, int stock) {
        this.items.put(item.id, item);
        this.prices.put(item.id, price);
        this.stocks.put(item.id, stock);
    }


    public void remove_item(Item item) {
        this.remove_item(item.id);
    }


    public void remove_item(int item_id) {
        this.items.remove(item_id);
        this.prices.remove(item_id);
        this.stocks.remove(item_id);
    }


    public void modify_item_price(Item item, double price) {
        this.prices.replace(item.id, price);
    }


    public void modify_item_stock(Item item, int stock) {
        this.stocks.replace(item.id, stock);
    }


    public Item get_item(int item_id) {
        return this.items.get(item_id);
    }


    public int get_item_stock(Item item) {
        return this.stocks.getOrDefault(item.id, -1);
    }


    public double get_item_price(Item item) {
        return this.prices.getOrDefault(item.id, -1.0);
    }


    public boolean add_sale(Sale sale) {
        for (int item_id : sale.get_items()) {
            int sale_stock = sale.get_stocks().get(item_id);
            int stock = this.stocks.get(item_id);

            if (sale_stock > stock) {
                return false;
            }

            this.stocks.replace(item_id, stock - sale_stock);
        }

        this.sales.add(sale);

        return true;
    }
}
