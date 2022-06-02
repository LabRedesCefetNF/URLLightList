package urllightlist.colecao;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.entidade.Dominio;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ResolucaoDeDominioException;
import urllightlist.utilitarios.ResolucaoDeDominio;

public class ColecaoDominioEmBDR extends ColecaoEmBDR<Dominio> implements
		ColecaoDominio {

	public ColecaoDominioEmBDR(Session session, Logger log) {
		super(session, Dominio.class, log);

	}

	public boolean validarDominio(String nome, ResolucaoDeDominio resolucao)
			throws IOException, InterruptedException,
			ResolucaoDeDominioException, ColecaoException {
				
		boolean dominioValido = true;
		resolucao.resolverDominio(nome);
		String resposta = resolucao.getResposta();
		if (resposta != null) {
			resolucao.interpretarResposta(resposta);
			if(resolucao.getStatusAtual()==0) {
				dominioValido = false;
			}
		} 
		return dominioValido;
	}
}