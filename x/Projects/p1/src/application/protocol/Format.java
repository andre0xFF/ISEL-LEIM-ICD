package application.protocol;

public interface Format {
    String encode(Message message);
    Message decode(String text);
    void register(Request request);
    void register(Response response);
}
