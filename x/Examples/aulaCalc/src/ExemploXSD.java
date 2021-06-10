import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Classe de exemplo para validar documentos XML através de XSD
 */

public class ExemploXSD {
	public final static String contexto = "xml\\";

	public static void main(String[] args) {
		// exemplo de validação comsucesso
		System.out.println("A validar calc.xml com calc.xsd");
		if (ExemploXSD.validaXSD("calc.xml", "calc.xsd"))
			System.out.println("Válido!");
		else
			System.out.println("Não Válido!");
		
		
		Calc c = new Calc(contexto + "calc.xml");
		c.leParametros();
		c.printCalculo();
		
		int result;
		try {
			result = c.calcula();
			System.out.println("Resultado: " + result);
		} catch (Exception e) {
			System.out.println("Erro a calcular: ");
			e.printStackTrace();
		}
		
	}

	private static boolean validaXSD(String xml, String gramaticaXsd) {
		Document D = XMLDoc.parseFile(contexto + xml);
		boolean result = false;
		try {
			if (XMLDoc.validDoc(D, contexto + gramaticaXsd, XMLConstants.W3C_XML_SCHEMA_NS_URI))
				// Validação com XSD realizada com sucesso!
				result = true;
			else
				// Falhou a validação com XSD
				result = false;
		} catch (SAXException e) {
			e.printStackTrace();
			System.out.println("Falhou a leitura do XSD (" + gramaticaXsd + ")!");
		}
		return result;
	}

}
