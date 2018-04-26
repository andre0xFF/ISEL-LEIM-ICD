package store.item;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import store.Parser;


public interface Size {
    String get_size();

    @Root
    class Apperal implements Parser, Size {
        public final static Apperal XS = new Apperal(0, "Extra small");
        public final static Apperal S = new Apperal(1, "Small");
        public final static Apperal M = new Apperal(2, "Medium");
        public final static Apperal L = new Apperal(3, "Large");
        public final static Apperal XL = new Apperal(4, "Extra large");
        public final static Apperal XXL = new Apperal(5, "Extra extra large");

        @Attribute
        private final int id;
        private final String description;

        @Override
        public int get_id() {
            return this.id;
        }


        public Apperal(int id, String description) {
            this.id = id;
            this.description = description;
        }


        @Override
        public String get_size() {
            return this.description;
        }
    }


    @Root
    class Shoe implements Size {
        public final static Shoe THIRTY = new Shoe(30);
        public final static Shoe THIRTY_ONE = new Shoe(31);
        public final static Shoe THIRTY_TWO = new Shoe(32);
        public final static Shoe THIRTY_THREE = new Shoe(33);
        public final static Shoe THIRTY_FOUR = new Shoe(34);
        public final static Shoe THIRTY_FIVE = new Shoe(35);
        public final static Shoe THIRTY_SIX = new Shoe(36);
        public final static Shoe THIRTY_SEVEN = new Shoe(37);
        public final static Shoe THIRTY_EIGHT = new Shoe(38);
        public final static Shoe THIRTY_NINE = new Shoe(39);
        public final static Shoe FORTY = new Shoe(40);
        public final static Shoe FORTY_ONE = new Shoe(41);
        public final static Shoe FORTY_TWO = new Shoe(42);
        public final static Shoe FORTY_THREE = new Shoe(43);
        public final static Shoe FORTY_FOUR = new Shoe(44);
        public final static Shoe FORTY_FIVE = new Shoe(45);
        public final static Shoe FORTY_SIX = new Shoe(46);
        public final static Shoe FORTY_SEVEN = new Shoe(47);
        public final static Shoe FORTY_EIGHT = new Shoe(48);
        public final static Shoe FORTY_NINE = new Shoe(49);


        @Attribute
        private final int id;


        public Shoe(int id) {
            this.id = id;
        }


        @Override
        public String get_size() {
            return String.valueOf(this.id);
        }
    }
}

