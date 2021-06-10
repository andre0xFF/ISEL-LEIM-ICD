import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World! This is the entry point!");

        System.out.println(work());

    }

    public static String work() throws IOException {
        String xml;

        XmlMapper xmlMapper = new XmlMapper();

        SimpleBean bean = new SimpleBean();

        Message message = new Message();
        message.content = xmlMapper.writeValueAsString(bean);
        message.name = SimpleBean.class;

        xml = xmlMapper.writeValueAsString(message);

        xmlMapper.writeValue(
                new File("message.xml"),
                message
        );

        message = xmlMapper.readValue(
                xml,
                Message.class
        );

        bean = xmlMapper.readValue(
                message.content,
                (JavaType) message.name
        );

        bean.write();

        return xml;
    }
}

class Message {
    public String content;
    public Object name;
}

class SimpleBean {
    public int x = 1;
    public int y = 2;

    public void write() {
        System.out.println("Print from simple bean" + x + y);
    }
}

class ComplexBean {

    public void write() {
        System.out.println("Print from complex bean!");
    }
}
