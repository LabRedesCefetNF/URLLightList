package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoCategoriaEmBDR;
import urllightlist.colecao.ColecaoDominio;
import urllightlist.colecao.ColecaoDominioEmBDR;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.entidade.Reclassificacao;
import urllightlist.excecao.ControllerException;
import urllightlist.service.AnalisadoraDeArquivoService;
import urllightlist.utilitarios.ManipulacaoDeArquivo;
import urllightlist.utilitarios.Ouvinte;
import urllightlist.utilitarios.ResolucaoDeDominio;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class AnalisarArquivoController
 */
public class AnalisarArquivoController extends HttpServlet implements Ouvinte {
	private static final long serialVersionUID = 1L;
	private Session session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnalisarArquivoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String nomePasta = request.getParameter("conteudo");
		String continuar = request.getParameter("continuar");

		this.session = Hibernate.getSessionFactory().openSession();
		AnalisadoraDeArquivoService analisarArq;
		Transaction transaction = null;
		Logger log = Logger.getLogger("URLLightList");

		try {
			if (nomePasta != null) {
				ColecaoDominio colecaoDominio = new ColecaoDominioEmBDR(
						this.session, log);
				ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(
						this.session, log);

				transaction = this.session.beginTransaction();
				ResolucaoDeDominio resolucaoDeDominio = new ResolucaoDeDominio(
						colecaoDominio, this, log);
				ManipulacaoDeArquivo mArq = new ManipulacaoDeArquivo(log,
						resolucaoDeDominio);
				mArq.setDiretorioArq(mArq.getDiretorioRaiz()
						+ ManipulacaoDeArquivo.pastaDownload + nomePasta + "/");
				analisarArq = new AnalisadoraDeArquivoService(log, mArq,
						colecaoCategoria, this);
				if (continuar == null) {
					analisarArq.setReiniciar(false);
				}
				analisarArq.analisar();

				if (transaction.isActive()) {
					transaction.commit();
				}
			} else {
				throw new ControllerException("Escolha uma pasta para a analise.");
			}

			Resposta resposta = new Resposta(true);
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);

		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.fatal(e.getMessage());
			Resposta resposta = new Resposta(false, e.getMessage(), e
					.getClass().getName());
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);

		} finally {
			this.session.close();

		}

	}

	@Override
	public void categoriaSalva(Categoria categoria) {
		if (session.getTransaction().isActive()) {
			session.getTransaction().commit();
			session.beginTransaction();
		}
	}

	@Override
	public void dominioSalvo(Dominio dominio) {
		if (session.getTransaction().isActive()) {
			session.getTransaction().commit();
			session.beginTransaction();
		}

	}

	@Override
	public void reclassificacaoSalva(Reclassificacao reclassificacao) {
		if (session.getTransaction().isActive()) {
			session.getTransaction().commit();
			session.beginTransaction();
		}

	}
}
