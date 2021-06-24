//package application;
//
//import com.fasterxml.jackson.annotation.JsonRootName;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.json.JsonMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
//
//@JsonRootName(value = "request")
//public class Request {
//
//    private Authentication authentication;
//
//    @JacksonXmlProperty(isAttribute = true)
//    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
//    private Method method;
//    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
//    private Resource resource;
//
//    public Request() {}
//
//    public Request(Method method, Authentication authentication, Resource resource) {
//        this.method = method;
//        this.authentication = authentication;
//        this.resource = resource;
//    }
//
//    public Authentication getAuthentication() {
//        return authentication;
//    }
//
//    public Method getMethod() {
//        return method;
//    }
//
//    public Resource getResource() {
//        return resource;
//    }
//
//    static ObjectMapper getDefaultSerializer() {
//        return getSerializer("xml");
//    }
//
//    static ObjectMapper getSerializer(String format) {
//        if (format.equalsIgnoreCase("xml")) {
//            return new XmlMapper();
//        }
//
//        if (format.equalsIgnoreCase("json")) {
//            return new JsonMapper();
//        }
//
//        return getDefaultSerializer();
//    }
//
//    public Response execute(Server server) {
//        // todo: verify authentication
//        // todo: verify authorization
//
//        this.method.execute(server);
//
//        return null;
//    }
//}
