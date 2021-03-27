package mesCommandes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class CommanderUnDisque extends HttpServlet{
	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String nom = null;
		int nbreProduit = 0;
		//Cookie[] cookies = req.getCookies();
		//On utilisant le filter en requipere le nom du client sans avoir à chercher elle même dans le cookie
		nom=(String)req.getAttribute("nom");
		//nom = Identification.chercheNom(cookies);
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title> votre commande </title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3>" + "Bonjour "+ nom + " voici votre commande" + "</h3>");
		
		// affichage de tous les disques présents dans le panier (éléments de la session)
		HttpSession session = req.getSession();
		Enumeration disques = session.getAttributeNames();
		while (disques.hasMoreElements()){
		   String disque = (String)disques.nextElement();
		   out.println(disque + ": " + session.getValue(disque) + "<br>");
		   nbreProduit++;
		}
		
		// si parametre ordre == ajouter affichage du disque à ajouter au panier
		String code = req.getParameter("code");
		out.println("<p>Commande code :"+code+"</p>");
		
		// ajoute du nouveau disque dans le panier
		String ordre = req.getParameter("ordre");
		if(ordre.equals("ajouter")) {
			session.setAttribute("panier_"+nbreProduit, code);
		}
		
		out.println("<A HREF=achat> Vous pouvez commandez un autre disque </A><br> ");
		out.println("<A HREF=enregistre> Vous pouvez enregistrer votre commande </A><br> ");
		out.println("</body>");
		out.println("</html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}	
