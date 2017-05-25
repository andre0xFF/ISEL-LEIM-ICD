import database.Database;
import org.w3c.dom.Document;

public final class BankDatabase extends Database {

    public BankDatabase(Document document, String xsd_path) {
        super(document, xsd_path);
    }
}