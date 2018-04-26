package store;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

@Root
public class Client {
    @Attribute
    public final int id;
    @Element
    private final String name;
    @Attribute
    private final int tax_number;
    @Element
    private final Gender gender;

    public Client(int id, String name, int tax_number, Gender gender) {

        this.id = id;
        this.name = name;
        this.tax_number = tax_number;
        this.gender = gender;
    }
}
