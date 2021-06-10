public class Servidor {

      public static void main(String args[]) {
        (new UDPListar()).start();
        (new TCPObter()).start();
    }

}