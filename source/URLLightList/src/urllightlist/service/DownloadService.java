package urllightlist.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;

import urllightlist.excecao.ManipulacaoDeArquivoException;
import urllightlist.excecao.ServiceException;
import urllightlist.utilitarios.ManipulacaoDeArquivo;
import urllightlist.utilitarios.ManipulacaoDeArquivoXML;

public class DownloadService {

	private ManipulacaoDeArquivoXML mArqXML;

	public DownloadService(ManipulacaoDeArquivoXML mArqXML) {
		this.mArqXML = mArqXML;

	}

	public void downloadMD5(String url) throws IOException,
			ManipulacaoDeArquivoException {
		this.mArqXML.setDiretorioArq(this.mArqXML.getDiretorioRaiz());
		this.mArqXML.setNomeArquivo(ManipulacaoDeArquivoXML.arqMD5);
		this.mArqXML.downloadArquivo(url);
	}

	public void downloadURLBlackList(String url, String urlMD5)
			throws IOException, ManipulacaoDeArquivoException {
		this.mArqXML.setNomeArquivo("bigblacklist.tar.gz");
		this.mArqXML.setDiretorioArq(this.mArqXML.getDiretorioRaiz()
				+ ManipulacaoDeArquivo.pastaDownload);
		this.mArqXML.downloadArquivo(urlMD5);
		downloadMD5(urlMD5);
		this.atualizarArqConfigXML();
	}

	public String existeNovoArqURLBlackList() throws JDOMException,
			IOException, ManipulacaoDeArquivoException, ServiceException {
		String mensagem = null;
		
		String md5 = this.lerArqMD5();
		List<String> atributos = this.mArqXML.prepararAtributos(md5);
		File arqXML = new File(this.mArqXML.getDiretorioRaiz()
				+ ManipulacaoDeArquivoXML.nomeArq);
		if (arqXML.exists()) {
			String codigoAtual = this.mArqXML.lerCodigo();
			if (codigoAtual.equals(atributos.get(1))) {
				mensagem = "Nao existe uma nova versao da URLBlackList.";
			} else {
				mensagem = "Existe uma nova versao da URLBlackList.";
			}
		} else {
			if (md5 != null) {
				this.mArqXML.setAtributos(atributos);
				this.mArqXML.gerarArqXMl();
				mensagem = "Nao existe uma nova versao da URLBlackList.";
			} else {
				throw new ServiceException(
						"Nao foi possivel gerar um novo arquivo de configuração MD5.");
			}
		}
		return mensagem;

	}

	public String lerArqMD5() throws IOException, ManipulacaoDeArquivoException {
		this.mArqXML.setDiretorioArq(this.mArqXML.getDiretorioRaiz());
		this.mArqXML.setNomeArquivo(ManipulacaoDeArquivoXML.arqMD5);
		return this.mArqXML.ler();
	}

	public void atualizarArqConfigXML() throws IOException,
			ManipulacaoDeArquivoException {
		String md5 = this.lerArqMD5();
		List<String> atributos = this.mArqXML.prepararAtributos(md5);
		this.mArqXML.setAtributos(atributos);
		this.mArqXML.gerarArqXMl();
	}

	public ManipulacaoDeArquivoXML getmArqXML() {
		return mArqXML;
	}

	public void setmArqXML(ManipulacaoDeArquivoXML mArqXML) {
		this.mArqXML = mArqXML;
	}

}
