package urllightlist.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import urllightlist.colecao.ColecaoDominio;
import urllightlist.entidade.Dominio;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ManipulacaoDeArquivoException;
import urllightlist.excecao.ResolucaoDeDominioException;
import urllightlist.utilitarios.ManipulacaoDeArquivo;
import urllightlist.utilitarios.Ouvinte;
import urllightlist.utilitarios.ResolucaoDeDominio;

public class AnalisadoraBancoDeDadosService {

	private Logger log;
	private Integer status;
	private Dominio dominio;
	private Ouvinte ouvinte;
	private boolean reiniciar;
	private ManipulacaoDeArquivo mArq;
	private List<Dominio> listaDominio;
	private ResolucaoDeDominio resolucao;
	private ColecaoDominio colecaoDominio;

	final static int maxResultado = 200;
	Long contagem;

	public AnalisadoraBancoDeDadosService(ResolucaoDeDominio resolucao,
			ManipulacaoDeArquivo mArq, ColecaoDominio colecaoDominio,
			Logger log, Ouvinte ouvinte) {

		this.log = log;
		this.mArq = mArq;
		this.reiniciar = true;
		this.ouvinte = ouvinte;
		this.resolucao = resolucao;
		this.colecaoDominio = colecaoDominio;

	}

	public void analisar() throws ColecaoException, IOException,
			ManipulacaoDeArquivoException, InterruptedException,
			ResolucaoDeDominioException {

		Long id;
		setRestricaoStatus();
		this.colecaoDominio.setMaxResultado(maxResultado);
		if (this.reiniciar == true) {
			if (this.mArq.ler() != null) {
				id = Long.valueOf(this.mArq.ler());
				contagem = this.colecaoDominio.contarComCampoMaiorQue("id", id);
				this.listaDominio = this.colecaoDominio.todosComCampoMaiorQue(
						"id", id);
				this.listarDominio();
			} else {
				log.warn("Não há uma análise de banco de dados para reiniciar.");
				this.reiniciar = false;
				this.analisar();
			}
		} else {
			contagem = this.colecaoDominio.contar();
			this.listaDominio = this.colecaoDominio.todos(0);
			this.listarDominio();
		}

		this.mArq.gravar("");
	}

	public void listarDominio() throws IOException, InterruptedException,
			ResolucaoDeDominioException, ColecaoException,
			ManipulacaoDeArquivoException {

		int nRodada = (int) (contagem / maxResultado);
		for (int contador = 0; contador <= nRodada; contador = contador + 1) {
			this.analisarListaDeDominio();
			if (this.mArq.ler() != null) {
				Long id = Long.valueOf(this.mArq.ler());
				this.listaDominio = this.colecaoDominio.todosComCampoMaiorQue(
						"id", id);
			}
		}
	}

	
	public void analisarListaDeDominio() throws IOException,
			InterruptedException, ResolucaoDeDominioException,
			ColecaoException, ManipulacaoDeArquivoException {

		Iterator<Dominio> iListaDeDominio = this.listaDominio.iterator();
		int contador = 0;
		while (iListaDeDominio.hasNext()) {
			this.dominio = iListaDeDominio.next();
			this.resolucao.setDominio(this.dominio);
			this.resolucao.resolverDominioJaExistente();
			this.mArq.gravar(String.valueOf(this.dominio.getId()));
			contador = contador + 1;
		}

	}

	public void setRestricaoStatus() {
		if (this.status != 2) {
			List<Object> listaRestricao = new ArrayList<Object>();
			listaRestricao.add("status");
			listaRestricao.add(this.status);
			this.colecaoDominio.setListaRestricao(listaRestricao);
			
		}
	}

	public List<Dominio> getListaDeDominio() {
		return listaDominio;
	}

	public void setListaDeDominio(List<Dominio> listaDeDominio) {
		this.listaDominio = listaDeDominio;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public List<Dominio> getListaDominio() {
		return listaDominio;
	}

	public void setListaDominio(List<Dominio> listaDominio) {
		this.listaDominio = listaDominio;
	}

	public ColecaoDominio getColecaoDominio() {
		return colecaoDominio;
	}

	public void setColecaoDominio(ColecaoDominio colecaoDominio) {
		this.colecaoDominio = colecaoDominio;
	}

	public ResolucaoDeDominio getResolucao() {
		return resolucao;
	}

	public void setResolucao(ResolucaoDeDominio resolucao) {
		this.resolucao = resolucao;
	}

	public ManipulacaoDeArquivo getmArq() {
		return mArq;
	}

	public void setmArq(ManipulacaoDeArquivo mArq) {
		this.mArq = mArq;
	}

	public Ouvinte getOuvinte() {
		return ouvinte;
	}

	public void setOuvinte(Ouvinte ouvinte) {
		this.ouvinte = ouvinte;
	}

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public boolean isReiniciar() {
		return reiniciar;
	}

	public void setReiniciar(boolean reiniciar) {
		this.reiniciar = reiniciar;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
