package urllightlist.controller;

public class Resposta {
	
	public Resposta(boolean sucesso,String mensagem, Object dado){
		this.sucesso=sucesso;
		this.mensagem=mensagem;
		this.dado=dado;
	}
	public Resposta(boolean sucesso,String mensagem){
		this.sucesso=sucesso;
		this.mensagem=mensagem;
		this.dado=null;
	}
	
	public Resposta(boolean sucesso){
		this.sucesso=true;
		this.mensagem=null;
		this.dado=null;
	}

	private final String mensagem;
	private final Object dado;
	private final boolean sucesso;

	public boolean isSucesso() {
		return sucesso;
	}

	public String getMensagem() {
		return mensagem;
	}

	public Object getDado() {
		return dado;
	}

	

}
