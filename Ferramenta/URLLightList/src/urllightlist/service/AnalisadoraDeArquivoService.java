package urllightlist.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import urllightlist.colecao.ColecaoCategoria;
import urllightlist.entidade.Categoria;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ManipulacaoDeArquivoException;
import urllightlist.excecao.ResolucaoDeDominioException;
import urllightlist.utilitarios.ManipulacaoDeArquivo;
import urllightlist.utilitarios.Ouvinte;

public class AnalisadoraDeArquivoService {

	private Logger log;
	private Categoria categoria;
	private Ouvinte ouvinte;
	private boolean reiniciar;
	private ManipulacaoDeArquivo mArq;
	private ColecaoCategoria colecaoCategoria;

	final static int maxResultado = 200;

	public AnalisadoraDeArquivoService(Logger log, ManipulacaoDeArquivo mArq,
			ColecaoCategoria colecaoCategoria, Ouvinte ouvinte) {

		this.log = log;
		this.mArq = mArq;
		this.ouvinte = ouvinte;
		this.reiniciar = true;
		this.colecaoCategoria = colecaoCategoria;

	}

	public void analisar() throws ColecaoException, IOException,
			ResolucaoDeDominioException, InterruptedException,
			ManipulacaoDeArquivoException {

		this.reiniciarAnalise();

		List<String> listaNomeCategoria = this.mArq.listarConteudoDeUmaPasta();

		Iterator<String> iNomeCategoria = listaNomeCategoria.iterator();
		while (iNomeCategoria.hasNext()) {
			String nome = iNomeCategoria.next();
			if (!(nome.equals("CATEGORIES"))) {
				List<Object> listaRestricao = new ArrayList<Object>();
				listaRestricao.add("nome");
				listaRestricao.add(nome);
				this.colecaoCategoria.setListaRestricao(listaRestricao);
				this.categoria = (Categoria) this.colecaoCategoria.buscar();
				if (categoria == null) {
					this.categoria = new Categoria();
					this.categoria.setNome(nome);
					this.categoria.setGrauPerversidade(1);
					this.colecaoCategoria.adicionar(this.categoria);
					this.ouvinte.categoriaSalva(categoria);
					this.categoria = (Categoria) this.colecaoCategoria.buscar();
					this.analisarCategoria();
				} else {
					if (this.categoria.getChecado() == null) {
						this.analisarCategoria();
					}
				}
			}
		}
		finalizarAnalise();
	}

	public void reiniciarAnalise() throws ColecaoException, IOException,
			ResolucaoDeDominioException, InterruptedException,
			ManipulacaoDeArquivoException {

		String diretorio = this.mArq.getDiretorioArq();

		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("checado");
		listaRestricao.add("inicio");
		this.colecaoCategoria.setListaRestricao(listaRestricao);
		this.categoria = (Categoria) this.colecaoCategoria.buscar();

		if ((this.categoria != null)) {
			if (this.reiniciar == true) {
				log.warn("Reiniciando: " + this.categoria.getNome());
				this.mArq.setNomeArquivo("recuperarPosArq");
				this.mArq.setDiretorioArq(this.mArq.defineDiretorio()
						+ ManipulacaoDeArquivo.pastaRecuperacao);
				String pos = this.mArq.ler();
				if (pos != null) {
					this.mArq.setPos(Long.parseLong(pos));
				}
				this.mArq.setDiretorioArq(diretorio);
				this.mArq.lerArquivoDeDominio(this.categoria);
				this.atualizarChecadoCategoria("final");
				log.warn("Analise finalizada: " + categoria.getNome());
			} else {
				this.restaurarConfig();
			}
		}else{
			log.warn("Não existe uma análise para reiniciar.");
		}
	}

	public void restaurarConfig() throws ColecaoException {

		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("checado");
		listaRestricao.add("notVerify");
		this.colecaoCategoria.setListaRestricao(listaRestricao);
		List<Categoria> listaCategoria = this.colecaoCategoria
				.todosComExcecao();
		if (listaCategoria != null) {
			Iterator<Categoria> iCategoria = listaCategoria.iterator();
			while (iCategoria.hasNext()) {
				this.categoria = iCategoria.next();
				this.atualizarChecadoCategoria(null);
			}
			log.warn("As configurações iniciais foram restauradas.");
		}
	}

	public void analisarCategoria() throws ColecaoException, IOException,
			ResolucaoDeDominioException, InterruptedException,
			ManipulacaoDeArquivoException {

		this.atualizarChecadoCategoria("inicio");
		log.warn("Analise iniciada da categoria: " + this.categoria.getNome());
		this.mArq.lerArquivoDeDominio(this.categoria);
		this.atualizarChecadoCategoria("final");

		log.warn("Analise finalizada da categoria: " + this.categoria.getNome());
	}

	public void finalizarAnalise() throws ColecaoException {

		this.colecaoCategoria.setListaRestricao(null);
		Long qdtTotal = this.colecaoCategoria.contar();

		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("checado");
		listaRestricao.add("notVerify");
		this.colecaoCategoria.setListaRestricao(listaRestricao);
		Long qtdNotVerify = this.colecaoCategoria.contar();

		listaRestricao.remove(1);
		listaRestricao.add("final");
		this.colecaoCategoria.setListaRestricao(listaRestricao);
		Long qtdFinalizada = this.colecaoCategoria.contar();

		if ((qdtTotal - qtdNotVerify) == qtdFinalizada) {
			List<Categoria> listaCategoria = this.colecaoCategoria.todos();
			Iterator<Categoria> iCategoria = listaCategoria.iterator();
			while (iCategoria.hasNext()) {
				this.categoria = iCategoria.next();
				this.atualizarChecadoCategoria(null);
			}
			log.warn("Analise da URLBlackList finalizada.");
		}
	}

	public void atualizarChecadoCategoria(String checado)
			throws ColecaoException {
		this.categoria.setChecado(checado);
		this.colecaoCategoria.atualizar(this.categoria);
		this.ouvinte.categoriaSalva(categoria);
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public ManipulacaoDeArquivo getmArq() {
		return mArq;
	}

	public void setmArq(ManipulacaoDeArquivo mArq) {
		this.mArq = mArq;
	}

	public ColecaoCategoria getColecaoCategoria() {
		return colecaoCategoria;
	}

	public void setColecaoCategoria(ColecaoCategoria colecaoCategoria) {
		this.colecaoCategoria = colecaoCategoria;
	}

	public Ouvinte getOuvinte() {
		return ouvinte;
	}

	public void setOuvinte(Ouvinte ouvinte) {
		this.ouvinte = ouvinte;
	}

	public boolean isReiniciar() {
		return reiniciar;
	}

	public void setReiniciar(boolean reiniciar) {
		this.reiniciar = reiniciar;
	}

	public static int getMaxresultado() {
		return maxResultado;
	}

}
