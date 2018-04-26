package store.item;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import store.Gender;


@Root
public class Item {
    @Attribute
    public final int id;
    @Element
    public final Gender gender;
    @Element
    public final Type type;
    @Element
    public final Size size;
    @Element
    public final String brand;
    @Element
    public final String model;
    @Element(required=false)
    public final String description;
    @Attribute(required=false)
    public final String photo_path;


    public Item(int id, Gender gender, Type type, Size size, String brand, String model, String description,
             String photo_path) {
        this.id = id;
        this.gender = gender;
        this.type = type;
        this.size = size;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.photo_path = photo_path;
    }

    @Override
    public boolean equals(Object obj) {
        Item item = (Item) obj;

        return item.id == this.id
                && item.gender.equals(this.gender)
                && item.type.equals(this.type)
                && item.size.equals(this.size)
                && item.brand.equals(this.brand)
                && item.model.equals(this.model)
                && (item.description == null && this.description == null || item.description.equals(this.description))
                && (item.photo_path == null && this.photo_path == null || item.photo_path.equals(this.photo_path))
                && super.equals(obj);
    }
}
