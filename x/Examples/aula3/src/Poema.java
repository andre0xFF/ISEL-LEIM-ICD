import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
 * Classe para manipula��o de poemas
 */

/**
 * @author Porf�rio Filipe
 *
 */

public class Poema {
	public final static String contexto = "WebContent" + File.separator + "xml" + File.separator ;
	public final static String poemas = "WebContent" + File.separator + "poemas" + File.separator;
	Document D = null; 		// representa a arvore DOM com o poema
	String poemaFileName=null;
	
	public Poema(Element pm) {
			DocumentBuilder builder;
			try {
				builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				D = builder.newDocument();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			Element clone = (Element) D.importNode(pm, true);
			D.appendChild(clone);
	}
	
	public Poema(String XMLdoc) {
		poemaFileName=XMLdoc;
		XMLdoc = poemas + XMLdoc;
		DocumentBuilder docBuilder;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			File sourceFile = new File(XMLdoc);
			D = docBuilder.parse(sourceFile);
		} catch (ParserConfigurationException e) {
			System.out.println("Wrong parser configuration: " + e.getMessage());
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Could not read source file: " + e.getMessage());
		}
	}

	public void menu() {
		char op;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println();
			System.out.println();
			System.out.println("*** Menu Poema ***");
			System.out
					.println("1 - Apresenta o poema na sua forma escrita clássica.");
			System.out
					.println("2 - Classifica (DOM) as estrofes quanto ao número de versos.");
			System.out
					.println("3 - Acrescenta um verso a determinada estrofe.");
			System.out.println("4 - Remove uma determinada estrofe.");
			System.out
					.println("5 - Indica os versos que cont�m determinada palavra.");
			System.out.println("6 - Gravar o poema.");
			System.out.println("7 - Validar/Reconhecer poema.");
			System.out.println("8 - Validar/Reconhecer soneto.");
			System.out.println("9 - Classifica (XPATH) as estrofes quanto ao número de versos.");
			System.out.println("A - Apresenta o titulo do poema.");
			System.out.println("B - Testa se o poema contém lista de palavras.");
			System.out.println("C - Consultar a lista de (titulos) de poemas partilhada.");
			System.out.println("O - Obter um poema que inclua um dado conjunto de palavras.");
			System.out.println("0 - Terminar!");
			String str = sc.nextLine();
			if (str != null && str.length() > 0)
				op = str.charAt(0);
			else
				op = ' ';
			switch (op) {
			case '1':
				apresenta();
				break;
			case '2':
				classificaDOM();
				break;
			case '3':
				System.out.println("Indique o número da estrofe:");
				short i = sc.nextShort();
				sc.nextLine();
				System.out.println("Escreva o verso:");
				String verso = sc.nextLine();
				if (acrescenta(i, verso))
					apresenta();
				else
					System.out.println("Não acrescentou!");
				break;
			case '4':
				System.out.println("Indique o número da estrofe a remover:");
				short r = sc.nextShort();
				sc.nextLine();
				if (remove(r))
					apresenta();
				else
					System.out.println("Não removeu!");
				break;
			case '5':
				System.out.println("Escreva a palavra:");
				String palavra = sc.nextLine();
				indica(palavra);
				break;
			case '6':
				System.out.println("Indique o nome do ficheiro (em " + Poema.contexto
						+ ") para guardar o poema (ex: novopoema.xml):");
				String poemaFileName = sc.nextLine();
				grava(poemaFileName);
				break;
			case '7':
				System.out.println("Indique o nome do ficheiro (em " + Poema.contexto
						+ ") que representa o esquema XML (ex: poema.xsd):");
				String xsdFileName = sc.nextLine();
				if (xsdFileName.length() == 0) {
					xsdFileName = "poema.xsd";
					System.out.println("Foi assumido o esquema XML representado em: " + xsdFileName);
				}
				
				if (isValid(xsdFileName))
					System.out.println("Validação com XSD realizada com sucesso!");
				else
					System.out.println("Falhou a validação com XSD ("+xsdFileName+")!");
				
				break;
			case '8':
				System.out.println("Aplica ao poema corrente a transformação (poema_xml_to_xml.xsl) \ne aplica ao resultdo (soneto.xml) o esquema XML (soneto.xsd).");
				if (isSoneto())
					System.out.println("Validação do soneto realizada com sucesso!");
				else
					System.out.println("Falhou a valida��o do soneto (soneto.xsd)!");
				break;
			case '9':
				classificaXPATH();
				break;
			case 'A':
			case 'a':
				System.out.println("O titulo do poema é: "+getTitulo());
				break;
			case 'B':
			case 'b':
				System.out.println("Indique as palavras: ");
				ArrayList<String> palavras = new ArrayList<String>();
				String pal="";
				do {
					pal = sc.nextLine();
					if(pal.compareTo("")==0) break;
					palavras.add(pal);
					System.out.println("Indique outra palavra ou <enter> para terminar:");
				} while(true);
				
				String[] array = palavras.toArray(new String[palavras.size()]);
				
				if (contem(array))
					System.out.println("O poema contém as palavras indicadas!");
				else
				  System.out.println("O poema não contém as palavras indicadas!");
				break;
			case 'C':
			case 'c':
				System.out.println("Consultar a lista de (titulos) de poemas partilhada.");
				System.out.println(XMLReadWrite.documentToString(Consultar()));
				break; 
			case 'O':
			case 'o':
				System.out.println("Obter um poema que inclua um dado conjunto de palavras");
				System.out.println("Indique as palavras: ");
				ArrayList<String> palList = new ArrayList<String>();
				String pv="";
				do {
					pv = sc.nextLine();
					if(pv.compareTo("")==0) break;
					palList.add(pv);
					System.out.println("Indique outra palavra ou <enter> para terminar:");
				} while(true);
				String[] palArray = palList.toArray(new String[palList.size()]);
				Document pm = Obter(palArray);
				if(pm!=null) {
					Element P = (Element) pm.getElementsByTagName("poema").item(0);
					Poema p = new Poema(P);
					p.apresenta();
				}
				else
					System.out.println("N�o existe nenhum poema com todas as palavras!");
				break;
			case '0': break;
			default:
				System.out.println("Opção inválida, esolha uma opção do menu.");
			}
		} while (op != '0');
		sc.close();
		System.out.println("Terminou a execução.");
		System.exit(0);
	}

	private String escreveExtenso(short numero) {
		switch (numero) {
		case 1:
			return "Monastico";
		case 2:
			return "Dístico ou parelha";
		case 3:
			return "Terceto";
		case 4:
			return "Quadra";
		case 5:
			return "Quintilha";
		case 6:
			return "Sextilha";
		case 7:
			return "Sétima";
		case 8:
			return "Oitava";
		case 9:
			return "Nona";
		case 10:
			return "Décima";
		default:
			return "Irregular ("+numero+")";
		}
	}
	
	private boolean estaPresente(String palavra, String verso) {
		StringTokenizer st = new StringTokenizer(verso);
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.compareToIgnoreCase(palavra) == 0)
				return true;
			if (token.compareToIgnoreCase(palavra + ",") == 0)
				return true;
			if (token.compareToIgnoreCase(palavra + ".") == 0)
				return true;
			if (token.compareToIgnoreCase(palavra + ":") == 0)
				return true;
			if (token.compareToIgnoreCase(palavra + "...") == 0)
				return true;
			if (token.compareToIgnoreCase(palavra + "!") == 0)
				return true;
			if (token.compareToIgnoreCase(palavra + "?") == 0)
				return true;
			if (token.compareToIgnoreCase("-" + palavra) == 0)
				return true;
			if (token.compareToIgnoreCase("(" + palavra + ")") == 0
					|| token.compareToIgnoreCase("(" + palavra) == 0
					|| token.compareToIgnoreCase(palavra + ")") == 0)
				return true;
			if (token.compareToIgnoreCase("'" + palavra + "'") == 0
					|| token.compareToIgnoreCase("'" + palavra) == 0
					|| token.compareToIgnoreCase(palavra + "'") == 0)
				return true;
			if (token.compareToIgnoreCase("\"" + palavra + "\"") == 0
					|| token.compareToIgnoreCase("\"" + palavra) == 0
					|| token.compareToIgnoreCase(palavra + "\"") == 0)
				return true;
		}
		return false;
	}

	public String getTitulo(){
		Element root = D.getDocumentElement();
		Element titulo = (Element) root.getElementsByTagName("titulo").item(0);
		return titulo.getTextContent();
	}
	
	// permite testar se o poema tem a palavra assinalada em parametro
	public boolean contem(String palavra) {
		Element root = D.getDocumentElement();
		NodeList versos = root.getElementsByTagName("verso");
		for (int i = 0; i < versos.getLength(); i++) {
			if (estaPresente(palavra, versos.item(i).getTextContent()))
				return true;
		}
		return false;
	}
	
	// permite testar se o poema tem as palavras assinaladas em parametro
	public boolean contem(String[] palavras) {
		for (int i = 0; i < palavras.length; i++)
			if (!contem(palavras[i]))
				return false;
		return true;
	}
	
	// devolve array de strings com lista de ficheiros na pasta dos poemas
	public static String[] getPoemas() {
		int j=0;
		File folder = new File(poemas);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++)
			if (listOfFiles[i].isFile())
				j=j+1;
		String[] files= new String[j];
		j=0;
		for (int i = 0; i < listOfFiles.length; i++)
			if (listOfFiles[i].isFile())
				files[j++]=listOfFiles[i].getName();
		return files;
		}
	
	// Consultar a lista de (titulos) de poemas partilhada.
	public static Document Consultar() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	    Document titulos = builder.newDocument();
	    // create the root element node
	    Element root = titulos.createElement("titulos");
	    titulos.appendChild(root);
	    // create a comment node given the specified string
	    Comment comment = titulos.createComment("Lista de titulos dos poemas");
	    titulos.insertBefore(comment, root);
	    String[] poemas=getPoemas();
		for(int i=0; i<poemas.length; i++) {
			// add titulo
			//System.out.println(i+"-"+poemas[i]);
			Poema p=new Poema(poemas[i]);
	        Element tituloElement = titulos.createElement("titulo");
	        tituloElement.appendChild(titulos.createTextNode(p.getTitulo()));
	        root.appendChild(tituloElement);
		}
		return titulos;
	}
	
	// Obter um poema que inclua um dado conjunto de palavras.
	public static Document Obter(String[] palavras) {
		String[] poemas=getPoemas();
		for(int i=0; i<poemas.length; i++) {
			Poema p=new Poema(poemas[i]);
	        if(p.contem(palavras))
	        	return p.DOMDoc();
		}
		return null;
	}
	
	public Document DOMDoc() {
		return 	D;
	}
	
	public void DocDOM(Document Doc) {
		D=Doc;
	}
	
	public boolean isValid(String xsdFileName){
		try {
			return XMLDoc.validDoc(D, contexto+xsdFileName, XMLConstants.W3C_XML_SCHEMA_NS_URI);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	public boolean isSoneto(){
		XMLDoc.transfDoc(poemas+poemaFileName, contexto+"poema_xml_to_xml.xsl", contexto+"soneto.xml");
		return XMLDoc.validDocXSD(contexto+"soneto.xml", contexto+"soneto.xsd"); 
	}
	public void apresenta() {
		Element root = D.getDocumentElement();
		System.out.println("Título: " + getTitulo());
		System.out.println();
		NodeList estrofes = root.getElementsByTagName("estrofe");
		for (int e = 0; e < estrofes.getLength(); e++) {
			Element estrofe = (Element) estrofes.item(e);
			NodeList versos = estrofe.getElementsByTagName("verso");
			for (int i = 0; i < versos.getLength(); i++)
				System.out.println(versos.item(i).getTextContent());
			System.out.println();
		}
		Element autor = (Element) root.getElementsByTagName("autor").item(0);
		System.out.println("Autor: " + autor.getTextContent());
	}

	public void classificaDOM() {
		System.out
				.println("Classificação das estrofes quanto à quantidade de versos:");
		Element root = D.getDocumentElement();
		NodeList estrofes = root.getElementsByTagName("estrofe");
		for (int e = 0; e < estrofes.getLength(); e++) {
			Element estrofe = (Element) estrofes.item(e);
			NodeList versos = estrofe.getElementsByTagName("verso");
			System.out.println(e + 1 + " estrofe: "+escreveExtenso((short)versos.getLength()));
		}
	}

	public void classificaXPATH() {
		System.out.println("Classificação das estrofes quanto à quantidade de versos:");
		// contar as estrofes
		NodeList estrofes = XMLDoc.getXPath("/poema/estrofe", D);
		for (int e = 0; e < estrofes.getLength(); e++) {
			// contar os versos
			NodeList versos = XMLDoc.getXPath("/poema/estrofe[position()="+(e+1)+"]/verso", D);
			System.out.println(e + 1 + " estrofe: "+escreveExtenso((short)versos.getLength()));
		}
	}
	public boolean acrescenta(short numEstrofe, String verso) {
		System.out.println("Acrescenta o verso \"" + verso + "\" à estrofe "
				+ numEstrofe + ".");
		Element root = D.getDocumentElement();
		NodeList estrofes = root.getElementsByTagName("estrofe");
		if (numEstrofe <= estrofes.getLength()) {
			Element estrofe = (Element) estrofes.item(numEstrofe - 1);
			Element vers = D.createElement("verso");
			vers.setTextContent(verso);
			estrofe.appendChild(vers);
			return true;
		}
		return false;
	}

	public boolean remove(short numEstrofe) {
		Element root = D.getDocumentElement();
		NodeList estrofes = root.getElementsByTagName("estrofe");
		if (numEstrofe <= estrofes.getLength()) {
			Element estrofe = (Element) estrofes.item(numEstrofe - 1);
			estrofe.getParentNode().removeChild(estrofe);
			System.out.println("Removeu a " + numEstrofe + " estrofe.");
			return true;
		}
		return false;
	}

	public void indica(String palavra) {
		System.out.println("Indica os versos com a  palavra \"" + palavra
				+ "\"");
		Element root = D.getDocumentElement();
		NodeList versos = root.getElementsByTagName("verso");
		for (int i = 0; i < versos.getLength(); i++)
			if (estaPresente(palavra, versos.item(i).getTextContent()))
				System.out.println(versos.item(i).getTextContent());
	}

	/**
	 * Escreve arvore DOM num ficheiro
	 *
	 * @param output ficheiro usado para escrita
	 */
	public boolean grava(final String output) {
		try {
			writeDocument(D, new FileOutputStream(contexto+output));
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * implementação da escrita da arvore num ficheiro recorrendo ao XSLT
	 * @param input arvore DOM
	 * @param output stream usado para escrita
	 */
	public static final void writeDocument(final Document input, final OutputStream output) {
		try {
			DOMSource domSource = new DOMSource(input);
			StreamResult resultStream = new StreamResult(output);
			TransformerFactory transformFactory = TransformerFactory
					.newInstance();

			// transforma��o vazia

			Transformer transformer = transformFactory.newTransformer();

			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			if (input.getXmlEncoding() != null)
				transformer.setOutputProperty(OutputKeys.ENCODING,
						input.getXmlEncoding());
			else
				transformer
						.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			try {
				transformer.transform(domSource, resultStream);
			} catch (javax.xml.transform.TransformerException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		String[] poemas=getPoemas();
		System.out.println("Poemas em " + Poema.poemas+":");
		for(int i=0; i<poemas.length; i++)
			System.out.println((i+1)+" - " + poemas[i]);
		Scanner sc = new Scanner(System.in);
		System.out.println("Indique o nome do ficheiro (em " + Poema.poemas
				+ ") com o poema (ex: poema1.xml):");
		String poemaFileName = sc.nextLine();
		if (poemaFileName.length() == 0) {
			poemaFileName = "poema1.xml";
			System.out.println("Foi assumido o poema representado em: " + poemaFileName);
		} 
		Poema pm = new Poema(poemaFileName);
		pm.menu();
		sc.close();
	}
}
