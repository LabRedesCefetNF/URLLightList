package urllightlist.colecao;

import java.io.IOException;

import urllightlist.entidade.Dominio;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ResolucaoDeDominioException;
import urllightlist.utilitarios.ResolucaoDeDominio;

public interface ColecaoDominio extends Colecao<Dominio> {

	public boolean validarDominio(String nome, ResolucaoDeDominio resolucao)
			throws IOException, InterruptedException,
			ResolucaoDeDominioException, ColecaoException;

}
