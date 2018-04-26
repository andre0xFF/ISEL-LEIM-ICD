package store;


public interface Parser {
    int get_id();


    default Parser parse(Parser[] parsers, int id) {
        for (Parser parser : parsers) {
            if (parser.get_id() == id) {
                return parser;
            }
        }

        return null;
    }
}
