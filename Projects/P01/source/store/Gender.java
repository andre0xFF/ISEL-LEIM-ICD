package store;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


@Root
public class Gender implements Parser {
    public static final Gender UNISEX = new Gender(0, "Unisex");
    public static final Gender FEMALE = new Gender(1, "Woman");
    public static final Gender CHILDREN = new Gender(2, "Children");
    public static final Gender MALE = new Gender(3, "Man");


    @Attribute
    public final int id;
    public final String description;


    @Override
    public int get_id() {
        return this.id;
    }


    public Gender(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Gender(int id) {
        Parser[] parsers = new Parser[] { UNISEX, FEMALE, CHILDREN, MALE};
        Gender aux = (Gender) this.parse(parsers, id);

        this.id = aux.id;
        this.description = aux.description;
    }
}
