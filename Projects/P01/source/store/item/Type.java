package store.item;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import store.Parser;


@Root
public class Type implements Parser {
    public static final Type SNEAKER = new Type(0, "Sneaker");
    public static final Type BOOT = new Type(1, "Boot");
    public static final Type SHIRT = new Type(2, "Shirt");
    public static final Type PANTS = new Type(3, "Pants");
    public static final Type UNDERPANTS = new Type(4, "Underpants");
    public static final Type JERSEY = new Type(5, "Jersey");
    public static final Type SOCKS = new Type(6, "Socks");
    public static final Type HAT = new Type(7, "Hat");


    @Attribute
    public final int id;
    public final String description;


    @Override
    public int get_id() {
        return this.id;
    }


    public Type(int id, String description) {
        this.id = id;
        this.description = description;
    }
}