package urllightlist.service;

import java.util.ArrayList;
import java.util.List;

import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoReclassificacao;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Reclassificacao;
import urllightlist.excecao.ColecaoException;
import urllightlist.utilitarios.Ouvinte;

public class ReclassificacaoService {
	private ColecaoReclassificacao colecaoReclassificacao;
	private ColecaoCategoria colecaoCategoria;
	private Ouvinte ouvinte;

	public ReclassificacaoService(ColecaoReclassificacao colecaoReclassificacao) {
		this.colecaoReclassificacao = colecaoReclassificacao;
	}

	public ReclassificacaoService(
			ColecaoReclassificacao colecaoReclassificacao,
			ColecaoCategoria colecaoCategoria, Ouvinte ouvinte) {
		this.colecaoReclassificacao = colecaoReclassificacao;
		this.colecaoCategoria = colecaoCategoria;
		this.ouvinte = ouvinte;
	}

	public List<Reclassificacao> listarReclassificaoPorLogin(Long id)
			throws ColecaoException {
		List<Object> listaRestricao= new ArrayList<Object>();
		listaRestricao= new ArrayList<Object>();
		listaRestricao.add("administrador.id");
		listaRestricao.add(id);
		this.colecaoReclassificacao.setListaRestricao(listaRestricao);
		return this.colecaoReclassificacao.todos();
	}

	public void excluir(Long id) throws ColecaoException {
		this.colecaoReclassificacao.excluir(id);

	}

	public String adicionar(Reclassificacao reclassificacao, String[] categorias)
			throws ColecaoException {

		String mensagem = null;
		if (this.colecaoCategoria.validarNome(reclassificacao.getCategoria()
				.getNome())) {
			this.colecaoCategoria.adicionar(reclassificacao.getCategoria());
			this.ouvinte.categoriaSalva(reclassificacao.getCategoria());
			Categoria categoria_ = this.buscarCategoriaPorNome(reclassificacao
					.getCategoria().getNome());

			List<Categoria> listaCategoria = new ArrayList<Categoria>();
			int tamanho = categorias.length;
			for (int contador = 0; contador < tamanho; contador++) {
				Categoria categoria = this
						.buscarCategoriaPorNome(categorias[contador]);
				listaCategoria.add(categoria);

			}
			reclassificacao.setCategoria(categoria_);
			reclassificacao.setListaCategoria(listaCategoria);
			this.colecaoReclassificacao.adicionar(reclassificacao);
			this.ouvinte.reclassificacaoSalva(reclassificacao);
		} else {
			mensagem = "O nome da categoria deve ter no minimo 3 caracteres e no maximo 12 caracteres.";
		}
		return mensagem;
	}

	public Categoria buscarCategoriaPorNome(String nome)
			throws ColecaoException {
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("nome");
		listaRestricao.add(nome);
		this.colecaoCategoria.setListaRestricao(listaRestricao);
		return (Categoria) colecaoCategoria.buscar();
	}

	public ColecaoCategoria getColecaoCategoria() {
		return colecaoCategoria;
	}

	public void setColecaoCategoria(ColecaoCategoria colecaoCategoria) {
		this.colecaoCategoria = colecaoCategoria;
	}
}