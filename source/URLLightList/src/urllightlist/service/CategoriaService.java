package urllightlist.service;

import java.util.ArrayList;
import java.util.List;

import urllightlist.colecao.ColecaoCategoria;
import urllightlist.entidade.Categoria;
import urllightlist.excecao.ColecaoException;

public class CategoriaService {
	private ColecaoCategoria colecaoCategoria;

	public CategoriaService(ColecaoCategoria colecaoCategoria) {
		this.colecaoCategoria = colecaoCategoria;
	}

	public Categoria buscarCategoriaPorNome(String nome)
			throws ColecaoException {
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("nome");
		listaRestricao.add(nome);
		this.colecaoCategoria.setListaRestricao(listaRestricao);
		return (Categoria) this.colecaoCategoria.buscar();

	}
	

	public void atualizar(String[] listaCategoria) throws ColecaoException {
		int tamanho = listaCategoria.length;
		for (int contador = 0; contador < tamanho; contador = contador + 2) {
			List<Object> listaRestricao = new ArrayList<Object>();
			listaRestricao.add("id");
			listaRestricao.add(Long.valueOf(listaCategoria[contador]));
			this.colecaoCategoria.setListaRestricao(listaRestricao);
			Categoria categoria = (Categoria) this.colecaoCategoria.buscar();
			categoria.setGrauPerversidade(Integer
					.valueOf(listaCategoria[contador + 1]));
			this.colecaoCategoria.atualizar(categoria);
			
		}

	}
	
	
	public List<Categoria> listarCategoria() throws ColecaoException {
		List<Object>listaRestricao = new ArrayList<Object>();
		listaRestricao.add("checado");
		listaRestricao.add("notVerify");
		this.colecaoCategoria.setListaRestricao(listaRestricao);
		this.colecaoCategoria.setCampoOrdenacao("nome");
		return (List<Categoria>) this.colecaoCategoria.todosComExcecao();

	}

}
