import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Calc {
	Document D = null;
	String op = null;
	int op1;
	int op2;
	
	
	public Calc(String calcDocName) {
		D = XMLDoc.parseFile(calcDocName);
	}
	
	public void leParametros(){
		NodeList operacao = D.getElementsByTagName("operacao");
		op = operacao.item(0).getTextContent();
		NodeList op1NodeList = D.getElementsByTagName("op1");
		op1 = Integer.parseInt( op1NodeList.item(0).getTextContent());
		NodeList op2NodeList = D.getElementsByTagName("op2");
		op2 = Integer.parseInt( op2NodeList.item(0).getTextContent());
	}


	public void printCalculo() {
		System.out.println("Operação: " + op);
		System.out.println("op1: " + op1);
		System.out.println("op2: " + op2);
	}


	public int calcula() throws Exception{
		if (op == null)
			throw new Exception("No operation available");
		
		switch (op) {
		case "soma":
			return op1 + op2;
		case "sub":
			return op1 - op2;
		case "mult":
			return op1 * op2;
		case "div":
			return op1 / op2;
		default:
			throw new Exception("Unknown operation!");
		}
		
	}

}
