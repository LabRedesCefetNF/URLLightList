package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoAdministrador;
import urllightlist.colecao.ColecaoAdministradorEmBDR;
import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoCategoriaEmBDR;
import urllightlist.colecao.ColecaoDominio;
import urllightlist.colecao.ColecaoDominioEmBDR;
import urllightlist.colecao.ColecaoReclassificacao;
import urllightlist.colecao.ColecaoReclassificacaoEmBDR;
import urllightlist.entidade.Administrador;
import urllightlist.excecao.ControllerException;
import urllightlist.service.AdministradorService;
import urllightlist.service.GeracaoURLLightListService;
import urllightlist.utilitarios.ManipulacaoDeArquivo;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class GerarListaReclassificadaController
 */
public class GerarURLLightListAtivaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GerarURLLightListAtivaController() {
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
		String tipoLista = request.getParameter("tipo");
		String nomePasta = request.getParameter("nomePasta");

		GeracaoURLLightListService gerarService;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();

		HttpSession sessao = request.getSession(false);
		String login = (String) sessao.getAttribute("login");
		try {

			if (tipoLista != null && nomePasta != null) {
				ColecaoDominio colecaoDominio = new ColecaoDominioEmBDR(
						session, log);
				ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(
						session, log);
				ManipulacaoDeArquivo mArq = new ManipulacaoDeArquivo(log);
				mArq.setDiretorioArq(mArq.getDiretorioRaiz()
						+ ManipulacaoDeArquivo.pastaExporta + nomePasta + "/");

				if (tipoLista.equals("formatoBlackList")) {
					gerarService = new GeracaoURLLightListService(mArq,
							colecaoCategoria, colecaoDominio);
					gerarService.gerarURLBlackList();

				} else {
					if (tipoLista.equals("reclassificada")) {
						ColecaoAdministrador colecaoAdministrador = new ColecaoAdministradorEmBDR(
								session, log);
						AdministradorService admService = new AdministradorService(
								colecaoAdministrador);
						Administrador adm = admService.buscarAdmPorLogin(login);
						ColecaoReclassificacao colecaoReclassificacao = new ColecaoReclassificacaoEmBDR(
								session, log);
						gerarService = new GeracaoURLLightListService(mArq,
								colecaoDominio, colecaoCategoria,
								colecaoReclassificacao, nomePasta);
						gerarService.gerarListaReclassificada(adm.getId());

					}
				}
			} else {
				throw new ControllerException(
						"Preencha os campos corretamente.");
			}

			Resposta resposta = new Resposta(true);
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);

		} catch (Exception e) {
			Resposta resposta = new Resposta(false, e.getMessage(), e
					.getClass().getName());
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);
		} finally {
			session.close();
		}
	}

}
