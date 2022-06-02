package urllightlist.utilitarios;

import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.entidade.Reclassificacao;

public interface Ouvinte {

	public void categoriaSalva(Categoria categoria);
	public void dominioSalvo(Dominio dominio);
	public void reclassificacaoSalva(Reclassificacao reclassificacao);
	
	
}
