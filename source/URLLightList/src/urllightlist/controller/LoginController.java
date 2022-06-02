package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoAdministrador;
import urllightlist.colecao.ColecaoAdministradorEmBDR;
import urllightlist.entidade.Administrador;
import urllightlist.excecao.ControllerException;
import urllightlist.service.AdministradorService;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
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

		String mensagem = null;
		Session session = Hibernate.getSessionFactory().openSession();
		Logger log = Logger.getLogger("URLLightList");
		ColecaoAdministrador colecaoAdministrador = new ColecaoAdministradorEmBDR(
				session, log);

		try {
			if (senha != null && login != null && login.length() > 0
					&& senha.length() > 0) {

				Administrador adm = new Administrador();
				adm.setLogin(login);
				adm.setSenha(senha);
				AdministradorService admService = new AdministradorService(
						colecaoAdministrador);
				if (admService.logar(adm) != null) {
					
					request.getSession().setAttribute("login", adm.getLogin());
				} else {
					mensagem = "Login ou senha invalido.";
				}
				Resposta resposta = new Resposta(true, mensagem);
				String json = (new Gson()).toJson(resposta);
				response.getWriter().write(json);

			} else {
				throw new ControllerException("Preencha os campos corretamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Resposta resposta = new Resposta(false, e.getMessage(), e
					.getClass().getName());
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);

		} finally {
			session.close();

		}

	}
}
