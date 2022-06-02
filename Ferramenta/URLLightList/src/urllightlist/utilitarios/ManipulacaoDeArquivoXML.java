package urllightlist.utilitarios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import urllightlist.excecao.ManipulacaoDeArquivoException;

public class ManipulacaoDeArquivoXML extends ManipulacaoDeArquivo {

	private String data;
	private String codigo;
	private String tamanho;

	public static final String arqMD5 = "arqMD5";
	public static final String nomeArq = "configMD5.xml";

	public ManipulacaoDeArquivoXML(Logger log)
			throws ManipulacaoDeArquivoException {
		super(log);
	}


	public void gerarArqXMl() throws IOException, ManipulacaoDeArquivoException {
		String diretorio = this.defineDiretorio();

		Document documento = new Document();
		Element principal = new Element("urlBlackList");
		Element md5 = new Element("md5");
		Attribute data = new Attribute("data", this.data);
		Attribute codigo = new Attribute("codigo", this.codigo);
		Attribute tamanho = new Attribute("tamanho", this.tamanho);
		md5.setAttribute(data);
		md5.setAttribute(codigo);
		md5.setAttribute(tamanho);
		principal.addContent(md5);

		documento.setRootElement(principal);
		XMLOutputter xOut = new XMLOutputter();
		OutputStream out = new FileOutputStream(new File(diretorio + nomeArq));
		xOut.output(documento, out);
		this.log.warn("Um novo arquivo de configuração MD5 foi gerado.");

	}

	public String lerCodigo() throws JDOMException, IOException,
			ManipulacaoDeArquivoException {
		File file = new File(this.defineDiretorio() + nomeArq);
		SAXBuilder construtor = new SAXBuilder();
		Document documento = construtor.build(file);
		Element principal = (Element) documento.getRootElement();
		return principal.getChild("md5").getAttributeValue("codigo");

	}

	public String lerTamanho() throws JDOMException, IOException,
			ManipulacaoDeArquivoException {
		File file = new File(this.defineDiretorio() + nomeArq);
		SAXBuilder construtor = new SAXBuilder();
		Document documento = construtor.build(file);
		Element principal = (Element) documento.getRootElement();
		return principal.getChild("md5").getAttributeValue("tamanho");

	}

	public String lerData() throws JDOMException, IOException,
			ManipulacaoDeArquivoException {
		File file = new File(this.defineDiretorio() + nomeArq);
		SAXBuilder construtor = new SAXBuilder();
		Document documento = construtor.build(file);
		Element principal = (Element) documento.getRootElement();
		return principal.getChild("md5").getAttributeValue("data");

	}

	public List<String> prepararAtributos(String str) {
		String[] arrayStr = str.split(",");
		List<String> atributos = new ArrayList<String>();
		for (int contador = 0; contador < arrayStr.length; contador = contador + 1) {
			String[] novoArray = arrayStr[contador].split("\"");
			atributos.add(novoArray[1]);
		}
		return atributos;
	}
	
	
	public void setAtributos(List<String> atributos) {
		this.data = atributos.get(0);
		this.codigo = atributos.get(1);
		this.tamanho = atributos.get(2);

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

}
