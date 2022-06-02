package urllightlist.utilitarios;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ManipulacaoDeArquivoException;
import urllightlist.excecao.ResolucaoDeDominioException;

public class ManipulacaoDeArquivo {

	private Long pos;
	protected Logger log;
	private String diretorioArq;
	protected String diretorioRaiz;
	private String nomeArquivo;
	private ResolucaoDeDominio resolucao;
	

	public static final String pastaDownload = "Download/";
	public static final String pastaExporta = "Exporta/";
	public static final String pastaExporta_Inativa = "Exporta/Inativa/";
	public static final String pastaRecuperacao = "Recuperacao/";

	public ManipulacaoDeArquivo(Logger log, ResolucaoDeDominio resolucao)
			throws ManipulacaoDeArquivoException {
		this.log = log;
		this.resolucao = resolucao;
		this.diretorioRaiz = this.defineDiretorio();
	}

	public ManipulacaoDeArquivo(Logger log)
			throws ManipulacaoDeArquivoException {
		this.log = log;
		this.diretorioRaiz = this.defineDiretorio();
	}

	public List<String> listarConteudoDeUmaPasta()
			throws ManipulacaoDeArquivoException {
		List<String> listaConteudo = null;

		File arquivo = new File(this.diretorioArq);
		if (arquivo.exists()) {
			String[] conteudo = arquivo.list();
			int tamanho = conteudo.length;
			listaConteudo = new ArrayList<String>();
			for (int contador = 0; contador < tamanho; contador = contador + 1) {
				listaConteudo.add(conteudo[contador]);
			}
		} else {
			log.fatal("Diretório inexistente: " + this.diretorioArq);
			throw new ManipulacaoDeArquivoException("Diretorio inexistente: "
					+ this.diretorioArq);
		}
		return listaConteudo;
	}

	public void lerArquivoDeDominio(Categoria categoria) throws IOException,
			ResolucaoDeDominioException, InterruptedException,
			ColecaoException, ManipulacaoDeArquivoException {

		String diretorioArq = this.diretorioArq;
		this.diretorioArq = diretorioArq + categoria.getNome();
		File arquivoDomains = new File(this.diretorioArq + "/domains");

		if (arquivoDomains.exists()) {
			RandomAccessFile randomAccess = new RandomAccessFile(
					arquivoDomains, "rw");
			if (this.pos != null) {
				randomAccess.seek(this.pos);
				log.warn("Recuperando posição " + this.pos
						+ " no arquivo de Dominio da Categoria "
						+ categoria.getNome());
			}
			String nomeDominio = randomAccess.readLine();
			while (nomeDominio != null && nomeDominio.length() > 0) {
				this.pos = randomAccess.getFilePointer();
				this.resolucao.resolverNovoDominio(nomeDominio, categoria);
				this.nomeArquivo = "recuperarPosArq";
				this.diretorioArq = this.getDiretorioRaiz()
						+ ManipulacaoDeArquivo.pastaRecuperacao;
				this.gravar(String.valueOf(this.pos));
				nomeDominio = randomAccess.readLine();
			}

			randomAccess.close();
			this.pos = null;
			this.setDiretorioArq(diretorioArq);

		} else {
			log.error("Arquivo de Dominio inexistente: " + this.diretorioArq);
		}
	}

	public String ler() throws IOException, ManipulacaoDeArquivoException {
		String str = null;
		File arqDeRecuperacao = new File(this.diretorioArq, this.nomeArquivo);

		if (arqDeRecuperacao.exists()) {
			FileReader fd = new FileReader(arqDeRecuperacao);
			BufferedReader br = new BufferedReader(fd);
			String linha = br.readLine();
			if (linha != null && linha.length() > 0) {
				str = linha;
			}

			br.close();
			fd.close();
		} else {
			log.warn("Arquivo não encontrado: " + this.nomeArquivo
					+ " Diretorio:" + this.diretorioRaiz);
		}
		return str;
	}

	public void gravar(String str) throws IOException,
			ManipulacaoDeArquivoException {

		File dir = new File(this.diretorioArq);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File arq = new File(this.diretorioArq, this.nomeArquivo);
		if (!arq.exists()) {
			arq.createNewFile();
		}

		FileWriter fw = new FileWriter(arq);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.close();
		fw.close();
	}

	public String defineDiretorio() throws ManipulacaoDeArquivoException {
		String SO;
		String diretorio;

		SO = this.identificaSO();
		if (SO.equals("W")) {
			diretorio = "C:/URLLightList/";
		} else {
			diretorio = "/opt/URLLightList/";
		}
		return diretorio;
	}

	public String identificaSO() throws ManipulacaoDeArquivoException {
		String sO_identificado;

		String SO = System.getProperty("os.name");
		if (SO.contains("Windows")) {
			sO_identificado = "W";
		} else {
			if (SO.contains("Linux")) {
				sO_identificado = "L";
			} else {
				log.fatal("Sistema Operacional incompatível: " + SO);
				throw new ManipulacaoDeArquivoException(
						"Sistema Operacional incompatível: " + SO);
			}
		}
		return sO_identificado;
	}

	public void criarArqURLLightList(List<Dominio> listaDominio)
			throws IOException, ManipulacaoDeArquivoException {

		File dir = new File(this.diretorioArq);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File arquivo = new File(this.diretorioArq, this.nomeArquivo);
		if (!arquivo.exists()) {
			arquivo.createNewFile();
		}

		FileWriter fw = new FileWriter(arquivo, true);
		BufferedWriter bw = new BufferedWriter(fw);
		Iterator<Dominio> iDominio = listaDominio.iterator();
		while (iDominio.hasNext()) {
			bw.write(iDominio.next().getNome());
			bw.newLine();
		}
		bw.close();
		fw.close();
	}

	public void downloadArquivo(String url) throws IOException{
		URL link = new URL(url);
		InputStream in = new BufferedInputStream(link.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = in.read(buf))) {
			out.write(buf, 0, n);
		}

		out.close();
		in.close();
		byte[] response = out.toByteArray();

		FileOutputStream fos = new FileOutputStream(this.diretorioArq
				+ this.nomeArquivo);
		fos.write(response);
		fos.close();
	}

	public Long getPos() {
		return pos;
	}

	public void setPos(Long pos) {
		this.pos = pos;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public ResolucaoDeDominio getResolucao() {
		return resolucao;
	}

	public void setResolucao(ResolucaoDeDominio resolucao) {
		this.resolucao = resolucao;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getDiretorioRaiz() {
		return diretorioRaiz;
	}

	public void setDiretorioRaiz(String diretorioRaiz) {
		this.diretorioRaiz = diretorioRaiz;
	}

	public static String getPastadownload() {
		return pastaDownload;
	}

	public static String getPastaexporta() {
		return pastaExporta;
	}

	public static String getPastaexportaInativa() {
		return pastaExporta_Inativa;
	}

	public static String getPastarecuperacao() {
		return pastaRecuperacao;
	}

	public String getDiretorioArq() {
		return diretorioArq;
	}

	public void setDiretorioArq(String diretorioArq) {
		this.diretorioArq = diretorioArq;
	}

}
