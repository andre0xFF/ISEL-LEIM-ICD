package store;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import store.item.Item;

import java.util.ArrayList;
import java.util.HashMap;


@Root
public class Sale {
    @Attribute
    private int id;
    @Attribute
    private int client_id;
    @Attribute
    private int employee_id;
    @ElementList
    private ArrayList<Integer> items = new ArrayList<>();
    @ElementMap
    private HashMap<Integer, Double> prices = new HashMap<>();
    @ElementMap
    private HashMap<Integer, Integer> stocks = new HashMap<>();
    // TODO: add date


    public ArrayList<Integer> get_items() {
        return this.items;
    }


    public HashMap<Integer, Double> get_prices() {
        return this.prices;
    }


    public HashMap<Integer, Integer> get_stocks() {
        return this.stocks;
    }


    public Sale(Client client) {
        this.client_id = client.id;
    }

    public Sale(Client client, Employee employee) {
        this(client);
        this.employee_id = employee.id;
    }

    public void add(Item item, double price, int stock) {
        this.items.add(item.id);
        this.prices.put(item.id, price);
        this.stocks.put(item.id, stock);
    }

    public void remove(Item item) {
        this.items.remove(item.id);
        this.prices.remove(item.id);
        this.stocks.remove(item.id);
    }
}
