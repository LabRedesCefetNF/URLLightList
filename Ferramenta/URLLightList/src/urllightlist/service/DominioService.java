package urllightlist.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import urllightlist.colecao.ColecaoDominio;
import urllightlist.entidade.Dominio;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ResolucaoDeDominioException;
import urllightlist.excecao.ServiceException;
import urllightlist.utilitarios.ResolucaoDeDominio;

public class DominioService {

	private ColecaoDominio colecaoDominio;

	public DominioService(ColecaoDominio colecaoDominio) {
		this.colecaoDominio = colecaoDominio;
	}

	public String adicionar(Dominio dominio, ResolucaoDeDominio resolucao)
			throws ColecaoException, IOException, InterruptedException,
			ResolucaoDeDominioException, ServiceException {
		String resposta = null;
		Dominio dominio_encontrado = this.buscarDominioPorNome(dominio
				.getNome());
		if (dominio_encontrado == null) {
			if (this.colecaoDominio
					.validarDominio(dominio.getNome(), resolucao)) {
				dominio.setStatus(1);
				colecaoDominio.adicionar(dominio);
			} else {
				resposta = "Dominio nao resolve, logo nao pode ser salvo.";
			}
		} else {
			resposta = "Esse dominio ja existe no banco de dados.";
		}
		return resposta;
	}

	public void atualizar(Dominio dominio) throws ResolucaoDeDominioException,
			ColecaoException, IOException, InterruptedException,
			ServiceException {
		this.colecaoDominio.atualizar(dominio);

	}

	public void excluir(Long id) throws ColecaoException {
		this.colecaoDominio.excluir(id);
	}

	public Dominio buscarDominioPorNome(String nome) throws ColecaoException {
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("nome");
		listaRestricao.add(nome);
		this.colecaoDominio.setListaRestricao(listaRestricao);
		return (Dominio) this.colecaoDominio.buscar();
	}

	public Dominio buscarDominioPorId(Long id) throws ColecaoException {
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("id");
		listaRestricao.add(id);
		this.colecaoDominio.setListaRestricao(listaRestricao);
		return (Dominio) this.colecaoDominio.buscar();
	}

}
