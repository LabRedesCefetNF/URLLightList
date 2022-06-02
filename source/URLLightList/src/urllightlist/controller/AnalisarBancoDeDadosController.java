package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoDominio;
import urllightlist.colecao.ColecaoDominioEmBDR;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.entidade.Reclassificacao;
import urllightlist.excecao.ControllerException;
import urllightlist.service.AnalisadoraBancoDeDadosService;
import urllightlist.utilitarios.ManipulacaoDeArquivo;
import urllightlist.utilitarios.Ouvinte;
import urllightlist.utilitarios.ResolucaoDeDominio;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class AnalisarBanco
 */
public class AnalisarBancoDeDadosController extends HttpServlet implements
		Ouvinte {
	private static final long serialVersionUID = 1L;
	private Session session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnalisarBancoDeDadosController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		String continuar = request.getParameter("continuar");

		this.session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		Logger log = Logger.getLogger("URLLightList");
		ColecaoDominio colecaoDominio = new ColecaoDominioEmBDR(this.session,
				log);

		try {
			if (status != null) {
				transaction = this.session.beginTransaction();
				ResolucaoDeDominio resolucaoDeDominio = new ResolucaoDeDominio(
						colecaoDominio, this, log);
				ManipulacaoDeArquivo mArq = new ManipulacaoDeArquivo(log);
				mArq.setDiretorioArq(mArq.getDiretorioRaiz()
						+ ManipulacaoDeArquivo.pastaRecuperacao);
				mArq.setNomeArquivo("recuperarId");
				AnalisadoraBancoDeDadosService analisarBD = new AnalisadoraBancoDeDadosService(
						resolucaoDeDominio, mArq, colecaoDominio, log, this);
				if (continuar == null) {
					analisarBD.setReiniciar(false);
				}
				analisarBD.setStatus(Integer.valueOf(status));
				analisarBD.analisar();

				if (transaction.isActive()) {
					transaction.commit();
				}

				Resposta resposta = new Resposta(true);
				String json = (new Gson()).toJson(resposta);
				response.getWriter().write(json);

			} else {
				throw new ControllerException("Escolha um status para analise.");
			}
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
	public void reclassificacaoSalva(Reclassificacao arg0) {
		if (session.getTransaction().isActive()) {
			session.getTransaction().commit();
			session.beginTransaction();
		}

	}

}