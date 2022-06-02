package urllightlist.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoDominio;
import urllightlist.colecao.ColecaoReclassificacao;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.entidade.Reclassificacao;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ManipulacaoDeArquivoException;
import urllightlist.excecao.ServiceException;
import urllightlist.utilitarios.ManipulacaoDeArquivo;

public class GeracaoURLLightListService {

	private ManipulacaoDeArquivo mArq;
	private ColecaoDominio colecaoDominio;
	private ColecaoCategoria colecaoCategoria;
	private ColecaoReclassificacao colecaoReclassificacao;

	final static int maxResultado = 200;

	public GeracaoURLLightListService(ManipulacaoDeArquivo mArq,
			ColecaoDominio colecaoDominio) {
		this.mArq = mArq;
		this.colecaoDominio = colecaoDominio;

	}

	public GeracaoURLLightListService(ManipulacaoDeArquivo mArq,
			ColecaoCategoria colecaoCategoria, ColecaoDominio colecaoDominio) {
		this.mArq = mArq;
		this.colecaoCategoria = colecaoCategoria;
		this.colecaoDominio = colecaoDominio;

	}

	public GeracaoURLLightListService(ManipulacaoDeArquivo mArq,
			ColecaoDominio colecaoDominio, ColecaoCategoria colecaoCategoria,
			ColecaoReclassificacao colecaoReclassificacao, String nomePasta) {

		this.mArq = mArq;
		this.colecaoCategoria = colecaoCategoria;
		this.colecaoDominio = colecaoDominio;
		this.colecaoReclassificacao = colecaoReclassificacao;

	}

	public void gerarListaInativa() throws IOException,
			ManipulacaoDeArquivoException, ServiceException, ColecaoException {

		this.setRestricaoDominio();
		Long contagem = this.colecaoDominio.contar();

		int primeiro = 0;
		int nRodada = (int) (contagem / maxResultado);
		this.colecaoDominio.setMaxResultado(maxResultado);
		this.mArq.setNomeArquivo("domains");
		for (int contador = 0; contador <= nRodada; contador = contador + 1) {
			List<Dominio> listaDominio = this.colecaoDominio.todos(primeiro);
			if (listaDominio != null) {
				this.mArq.criarArqURLLightList(listaDominio);
			}
			primeiro = primeiro + maxResultado;
		}

	}

	public void gerarURLBlackList() throws ColecaoException, IOException,
			ManipulacaoDeArquivoException {
		int primeiro = 0;
		this.setRestricaoCategoria();	
		Long contagem = this.colecaoCategoria.contarComExcecao();
		int nRodada = (int) (contagem / maxResultado);
		this.colecaoCategoria.setMaxResultado(maxResultado);
		for (int contador = 0; contador <= nRodada; contador = contador + 1) {
			List<Categoria> listaCategoria = this.colecaoCategoria
					.todosComExcecao(primeiro);
			this.gerarURLLightList(listaCategoria, "formatoBlackList");
			primeiro = primeiro + maxResultado;
		}
	}

	public void gerarListaReclassificada(Long idAdm) throws ColecaoException,
			IOException, ManipulacaoDeArquivoException {
		String diretorio = this.mArq.getDiretorioArq();

		this.setRestricaoReclassificacao(idAdm);
		Long contagem = this.colecaoReclassificacao.contar();

		int primeiro = 0;
		int nRodada = (int) (contagem / maxResultado);
		this.colecaoReclassificacao.setMaxResultado(maxResultado);
		for (int contador = 0; contador <= nRodada; contador = contador + 1) {
			List<Reclassificacao> listaReclassificacao = this.colecaoReclassificacao
					.todos(primeiro);
			Iterator<Reclassificacao> iReclassificacao = listaReclassificacao
					.iterator();
			while (iReclassificacao.hasNext()) {
				Reclassificacao reclassificacao = iReclassificacao.next();
				List<Categoria> listaCategoria = reclassificacao
						.getListaCategoria();
				this.mArq.setDiretorioArq(diretorio
						+ reclassificacao.getCategoria().getNome());
				this.gerarURLLightList(listaCategoria, "reclassificada");
			}
			primeiro = primeiro + maxResultado;
			this.mArq.setDiretorioArq(null);
		}

	}

	public void gerarURLLightList(List<Categoria> listaCategoria, String tipo)
			throws ColecaoException, IOException, ManipulacaoDeArquivoException {
		
		String diretorio = this.mArq.getDiretorioArq();
		Iterator<Categoria> iCategoria = listaCategoria.iterator();
		while (iCategoria.hasNext()) {
			Categoria categoria = iCategoria.next();
			this.setRestricaoDominio(categoria.getId());
			Long contagem = this.colecaoDominio.contar();

			int primeiro = 0;
			int nRodada = (int) (contagem / maxResultado);
			this.mArq.setNomeArquivo("domains");
			this.colecaoDominio.setMaxResultado(maxResultado);
			for (int contador = 0; contador <= nRodada; contador = contador + 1) {
				List<Dominio> listaDominio = this.colecaoDominio
						.todos(primeiro);
				if (tipo.equals("formatoBlackList")) {
					String dirCategoria = diretorio + categoria.getNome() + "/";
					this.mArq.setDiretorioArq(dirCategoria);
				}
				this.mArq.criarArqURLLightList(listaDominio);     			
				primeiro = primeiro + maxResultado;
				listaDominio = null;
			}
			this.mArq.setDiretorioArq(diretorio);
			primeiro = 0;
		}
	}

	public void setRestricaoCategoria() {
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("checado");
		listaRestricao.add("notVerify");
		this.colecaoCategoria.setListaRestricao(listaRestricao);
	}
	
	public void setRestricaoDominio(){
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("status");
		listaRestricao.add(0);
		this.colecaoDominio.setListaRestricao(listaRestricao);
	}
	
	public void setRestricaoDominio(Long id){
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("status");
		listaRestricao.add(1);
		listaRestricao.add("categoria.id");
		listaRestricao.add(id);
		this.colecaoDominio.setListaRestricao(listaRestricao);

	}
	
	public void setRestricaoReclassificacao(Long id){
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("administrador.id");
		listaRestricao.add(id);
		this.colecaoReclassificacao.setListaRestricao(listaRestricao);
	}	

	public ManipulacaoDeArquivo getmArq() {
		return mArq;
	}

	public void setmArq(ManipulacaoDeArquivo mArq) {
		this.mArq = mArq;
	}

	public ColecaoDominio getColecaoDominio() {
		return colecaoDominio;
	}

	public void setColecaoDominio(ColecaoDominio colecaoDominio) {
		this.colecaoDominio = colecaoDominio;
	}

	public ColecaoCategoria getColecaoCategoria() {
		return colecaoCategoria;
	}

	public void setColecaoCategoria(ColecaoCategoria colecaoCategoria) {
		this.colecaoCategoria = colecaoCategoria;
	}

	public ColecaoReclassificacao getColecaoReclassificacao() {
		return colecaoReclassificacao;
	}

	public void setColecaoReclassificacao(
			ColecaoReclassificacao colecaoReclassificacao) {
		this.colecaoReclassificacao = colecaoReclassificacao;
	}

}
