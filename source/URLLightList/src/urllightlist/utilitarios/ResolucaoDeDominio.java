package urllightlist.utilitarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import urllightlist.colecao.ColecaoDominio;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ResolucaoDeDominioException;

public class ResolucaoDeDominio {

	private Dominio dominio;
	private String resposta;
	private int statusAtual;
	private Logger log;
	private Ouvinte ouvinte;

	boolean saidaInvalida = false;
	boolean respostaEncontrada = false;

	public ResolucaoDeDominio(Logger log) {
		this.log = log;
	}

	public ResolucaoDeDominio(ColecaoDominio colecaoDominio,
			Ouvinte ouvinteAnalise, Logger log) {

		this.colecaoDominio = colecaoDominio;
		this.ouvinte = ouvinteAnalise;
		this.log = log;
	}

	public void resolverNovoDominio(String nomeDominio, Categoria categoria)
			throws ColecaoException, ResolucaoDeDominioException, IOException,
			InterruptedException {

		if (this.eIP(nomeDominio) == false) {
			List<Object> listaRestricao = new ArrayList<Object>();
			listaRestricao.add("nome");
			listaRestricao.add(nomeDominio);
			this.colecaoDominio.setListaRestricao(listaRestricao);
			this.colecaoDominio.buscar();
			this.dominio = (Dominio) this.colecaoDominio.buscar();
			if (this.dominio == null) {
				this.dominio = new Dominio();
				this.dominio.setCategoria(categoria);
				this.dominio.setNome(nomeDominio);
				this.resolverDominio(this.dominio.getNome());
				if (this.resposta != null) {
					interpretarResposta(this.resposta);
					this.dominio.setStatus(this.statusAtual);
					this.colecaoDominio.adicionar(this.dominio);
					this.ouvinte.dominioSalvo(dominio);

				}
				this.resposta = null;
			} else {
				this.analisarRedundancia(categoria);
			}
		}
	}

	public void resolverDominioJaExistente() throws IOException,
			InterruptedException, ResolucaoDeDominioException, ColecaoException {
		this.resolverDominio(this.dominio.getNome());
		if (this.resposta != null) {
			this.interpretarResposta(this.resposta);
			this.compararStatus(this.getStatusAtual());
		}
		this.resposta = null;
	}

	public void resolverDominio(String nomeDominio) throws IOException,
			InterruptedException, ResolucaoDeDominioException {

		Process processo = null;

		String comando = "dig " + nomeDominio;

		if (this.dominioComecaComHifen(nomeDominio)) {
			comando = "dig " + "'\'" + nomeDominio;

		}
		processo = Runtime.getRuntime().exec(comando);
		log.warn("Resolucao: " + nomeDominio);
		processo.waitFor();
		if (processo.exitValue() == 0) {
			lerSaida(processo.getInputStream(), "sucesso");
		} else {
			lerSaida(processo.getErrorStream(), "erro");

		}
		processo.destroy();
	}

	public void lerSaida(InputStream inputStream, String tipoSaida)
			throws IOException, ResolucaoDeDominioException {
		BufferedReader br = null;
		String saida = null;

		br = new BufferedReader(new InputStreamReader(inputStream));
		saida = br.readLine();
		if (saida != null) {
			while (saida != null) {
				if (tipoSaida.equals("erro")) {
					lerSaidaComErro(saida);
					if (saidaInvalida == true) {
						saidaInvalida = false;
						break;
					}

				} else {
					this.lerSaidaDeSucesso(saida);
					if (respostaEncontrada == true) {
						respostaEncontrada = false;
						break;
					}
					saida = br.readLine();
				}
			}
		}
		br.close();
	}

	public void lerSaidaDeSucesso(String saida) {
		int pos = saida.indexOf("ANSWER:");
		if (pos != -1) {
			this.resposta = saida.substring(pos + 8, pos + 10);
			respostaEncontrada = true;
			
		}
	}

	public void lerSaidaComErro(String saida)
			throws ResolucaoDeDominioException {
		if (saida.contains("failed") || saida.contains("time out")) {
			log.fatal("[FAILED/TIME OUT]: Não há conectividade");
			throw new ResolucaoDeDominioException("Não há conectividade.");
		} else {
			this.log.error("[ERRO]: " + "[" + this.dominio.getNome() + "]:"
					+ saida);
			saidaInvalida = true;
		}

	}

	public void compararStatus(int statusAtual) throws ColecaoException {
		if (this.dominio.getStatus() != statusAtual) {
			log.warn("Status atualizado - " + dominio.getNome() + "para: "
					+ statusAtual);
			this.dominio.setStatus(statusAtual);
			this.colecaoDominio.atualizar(this.dominio);
			this.ouvinte.dominioSalvo(this.dominio);

		}
	}

	public boolean dominioComecaComHifen(String dominio) {
		boolean comeca = true;
		String subStr = dominio.substring(0, 1);
		if (!subStr.equals("-")) {
			comeca = false;
		}
		return comeca;

	}

	public void interpretarResposta(String resposta) {
		if (resposta.equals("0,")) {
			this.setStatusAtual(0);
		} else {
			this.setStatusAtual(1);
		}
	}

	public boolean eIP(String nome) {
		boolean eIP = true;
		String[] arrayDominio = nome.split("[.]");
		for (int j = 0; j < arrayDominio.length; j = j + 1) {
			char[] arrayCaracter = arrayDominio[j].toCharArray();
			if ((soNumero(arrayCaracter) == false)) {
				eIP = false;
				break;
			}
		}
		return eIP;
	}

	public boolean soNumero(char[] arrayCaracter) {
		boolean contemSoNumeros = true;
		for (int i = 0; i < arrayCaracter.length; i = i + 1) {
			char caracter = arrayCaracter[i];
			if (Character.isDigit(caracter) == false) {
				contemSoNumeros = false;
				break;
			}
		}
		return contemSoNumeros;
	}

	public void analisarRedundancia(Categoria categoria)
			throws ColecaoException {
		int grauCategoriaNoBD;
		int grauCategoriaEmAnalise;

		if (categoria.getId() != this.dominio.getCategoria().getId()) {
			grauCategoriaEmAnalise = categoria.getGrauPerversidade();
			grauCategoriaNoBD = this.dominio.getCategoria()
					.getGrauPerversidade();
			if (grauCategoriaNoBD < grauCategoriaEmAnalise) {
				this.dominio.setCategoria(categoria);
				this.colecaoDominio.atualizar(dominio);
				this.ouvinte.dominioSalvo(dominio);
				log.warn("Redundância existe: " + grauCategoriaNoBD + "<"
						+ grauCategoriaEmAnalise);
			} 
		}

	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getResposta() {
		return resposta;
	}

	public int getStatusAtual() {
		return statusAtual;
	}

	public void setStatusAtual(int statusAtual) {
		this.statusAtual = statusAtual;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public ColecaoDominio getColecaoDominio() {
		return colecaoDominio;
	}

	public void setColecaoDominio(ColecaoDominio colecaoDominio) {
		this.colecaoDominio = colecaoDominio;
	}

	private ColecaoDominio colecaoDominio;

}
