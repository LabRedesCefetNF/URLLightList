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
import urllightlist.service.DominioService;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class ExcluirDominioController
 */
public class ExcluirDominioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExcluirDominioController() {
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
		
		Long id = Long.valueOf(request.getParameter("id"));
		Transaction transaction = null;
		String mensagem=null;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoDominio colecaoDominio = new ColecaoDominioEmBDR(session, log);

		try {

			transaction = session.beginTransaction();
			DominioService dService = new DominioService(colecaoDominio);
			dService.excluir(id);
			transaction.commit();

			Resposta resposta = new Resposta(true, mensagem);
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
			session.close();

		}

	}
}