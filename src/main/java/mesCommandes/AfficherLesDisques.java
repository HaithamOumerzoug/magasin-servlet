package mesCommandes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AfficherLesDisques extends HttpServlet{
	
	@SuppressWarnings("static-access")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Stock uneVente = new Stock();
		String nom = null;
		//Cookie[] cookies = req.getCookies();
		//nom = Identification.chercheNom(cookies);
		//On utilisant le filter en requipere le nom du client sans avoir à chercher elle même dans le cookie
		nom =(String)req.getAttribute("nom");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title> Commande de disques </title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3>" + "Bonjour " + nom + " vous pouvez commander un disque" + "</h3>");
		uneVente.vente(out);
		out.println("</body>");
		out.println("</html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
