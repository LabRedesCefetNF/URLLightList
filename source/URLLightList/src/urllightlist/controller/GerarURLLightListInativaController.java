package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoDominio;
import urllightlist.colecao.ColecaoDominioEmBDR;
import urllightlist.service.GeracaoURLLightListService;
import urllightlist.utilitarios.ManipulacaoDeArquivo;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class GerarListaDominiosInativos
 */
public class GerarURLLightListInativaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GerarURLLightListInativaController() {
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

		
		GeracaoURLLightListService gerarLightListService;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoDominio colecaoDominio = new ColecaoDominioEmBDR(session, log);

		try {

			ManipulacaoDeArquivo mArq = new ManipulacaoDeArquivo(log);
			mArq.setDiretorioArq(mArq.getDiretorioRaiz()
					+ ManipulacaoDeArquivo.pastaExporta_Inativa);
			gerarLightListService = new GeracaoURLLightListService(mArq,
					colecaoDominio);
			gerarLightListService.gerarListaInativa();
			
			Resposta resposta = new Resposta(true);
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);

		} catch (Exception e) {
			log.warn(e.getMessage());
			Resposta resposta = new Resposta(false, e.getMessage(), e
					.getClass().getName());
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);
		} finally {
			session.close();
		}
	}

}
