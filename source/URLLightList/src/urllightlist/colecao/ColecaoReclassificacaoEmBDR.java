package urllightlist.colecao;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.entidade.Reclassificacao;

public class ColecaoReclassificacaoEmBDR extends ColecaoEmBDR<Reclassificacao>
		implements ColecaoReclassificacao {

	public ColecaoReclassificacaoEmBDR(Session session, Logger log) {
		super(session, Reclassificacao.class, log);
	}

}
