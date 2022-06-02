package urllightlist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import urllightlist.utilitarios.ManipulacaoDeArquivo;

/**
 * Servlet implementation class ListarConteudoPastaController
 */
public class ListarConteudoPastaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarConteudoPastaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger log = Logger.getLogger("URLLightList");
		
		try {

			List<String> conteudoPasta;
			ManipulacaoDeArquivo mArq = new ManipulacaoDeArquivo(log);
			mArq.setDiretorioArq(mArq.getDiretorioRaiz()+ManipulacaoDeArquivo.pastaDownload);			
			conteudoPasta = mArq.listarConteudoDeUmaPasta();

			request.setAttribute("conteudoPasta", conteudoPasta);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("analisarArquivo.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			throw new ServletException(e);
		
		}
	}


	}


