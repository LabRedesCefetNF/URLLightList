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
 * Servlet implementation class AtualizarUsuarioController
 */
public class AtualizarAdministradorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AtualizarAdministradorController() {
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
		
		Long id = Long.valueOf(request.getParameter("id"));
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String confirmacao = request.getParameter("confirmacao");
		
		
		Transaction transaction = null;
		String mensagem=null;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoAdministrador colecaoAdministrador = new ColecaoAdministradorEmBDR(
				session, log);

		try {
			if (login != null && senha != null && confirmacao != null) {
				
				transaction = session.beginTransaction();
				AdministradorService admService= new AdministradorService(colecaoAdministrador);
				if(senha.equals(confirmacao)){;
				Administrador adm= new Administrador();
				adm.setId(id);
				adm.setLogin(login);
				adm.setSenha(senha);
				mensagem=admService.atualizar(adm);
				transaction.commit();
				}else{
					mensagem="Campo senha nao confere com o campo de confirmacao.";
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