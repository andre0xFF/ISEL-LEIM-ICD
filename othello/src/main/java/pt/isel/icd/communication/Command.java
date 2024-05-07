//package pt.isel.icd.communication;
//
//import pt.isel.icd.patterns.command.Receiver;
//
//public interface Command<T extends Receiver> extends pt.isel.icd.patterns.command.Command<T> {
//
//    void setOriginSimpleSocket(SimpleSocket simpleSocket);
//
//    static Command<Receiver> fromXml(String xml) {
//        throw new UnsupportedOperationException("fromXml not implemented");
//    }
//
//    default String toXml() {
//        throw new UnsupportedOperationException("toXml not implemented");
//    }
//}
