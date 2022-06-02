package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoAdministrador;
import urllightlist.colecao.ColecaoAdministradorEmBDR;
import urllightlist.entidade.Administrador;
import urllightlist.excecao.ControllerException;
import urllightlist.service.AdministradorService;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class CadastrarUsuarioController
 */
public class CadastrarAdministradorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CadastrarAdministradorController() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String confirmacao = request.getParameter("confirmacao");

		String mensagem= null;
		Transaction transaction = null;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoAdministrador colecaoAdministrador = new ColecaoAdministradorEmBDR(
				session, log);

		try {
			if (login != null && login.length() > 0 && senha != null
					&& senha.length() > 0 && confirmacao != null
					&& confirmacao.length() > 0) {
				transaction = session.beginTransaction();
				AdministradorService admService = new AdministradorService(
						colecaoAdministrador);
				if (senha.equals(confirmacao)) {
				Administrador adm = new Administrador();
				adm.setLogin(login);
				adm.setSenha(senha);
				mensagem=admService.adicionar(adm);
				transaction.commit();
				}else{
					mensagem="Campo senha nao confere com o campo confirmacao.";
				}
			} else {
				throw new ControllerException(
						"Preencha os campos corretamente.");
			}

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
