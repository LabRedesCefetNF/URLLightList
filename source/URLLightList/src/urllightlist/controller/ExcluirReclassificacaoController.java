package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoReclassificacao;
import urllightlist.colecao.ColecaoReclassificacaoEmBDR;
import urllightlist.service.ReclassificacaoService;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class ExcluirReclassificacaoController
 */
public class ExcluirReclassificacaoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExcluirReclassificacaoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Long id = Long.valueOf(request.getParameter("id"));

		String mensagem=null;
		Transaction transaction = null;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoReclassificacao colecaoReclassificacao = new ColecaoReclassificacaoEmBDR(
				session, log);
		try {

			if (id != null) {
				transaction = session.beginTransaction();
				ReclassificacaoService rService = new ReclassificacaoService(
						colecaoReclassificacao);
				rService.excluir(id);
				transaction.commit();
				
				Resposta resposta = new Resposta(true, mensagem);
				String json = (new Gson()).toJson(resposta);
				response.getWriter().write(json);

			}} catch (Exception e) {

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