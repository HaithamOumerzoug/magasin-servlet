package mesCommandes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name="mo-servlet",urlPatterns = {"/servlet"})
@SuppressWarnings("serial")
public class InscriptionClient extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String nomRecu=null, motPasseRecu=null;
		String nomCookie=null, motPasseCookie=null;
		
		nomRecu=req.getParameter("nom");
		motPasseRecu=req.getParameter("motdepasse");
		if(req.getCookies()!=null) {
			Cookie[] cookies = req.getCookies();
			for(Cookie cookie : cookies ) {
				if(cookie.getName().equals("nom")) { 
					nomCookie=cookie.getValue().replace("+", " ");
				}
				else if(cookie.getName().equals("motdepasse")) { 
					motPasseCookie=cookie.getValue().replace("+", " ");
				}
			}
		}
		// initialisation cookies et paramètres
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		if (nomCookie==null && nomRecu==null){
			// Cas 1 : cas où il n'y a ni de cookies ni de parametres
			out.println("<html>");
			out.println("<body>");
			out.println("<head>");
			out.println("<title> inscription d'un client </title>");
			out.println("</head>");
			out.println("<body bgcolor='white' >");
			out.println( nomRecu +" | "+ motPasseRecu +" | "+ nomCookie +" | "+ motPasseCookie );
			out.println("<h3>" + "Bonjour, vous devez vous inscrire " + "</h3>");
			out.println("<h3>" + "Attention mettre nom et le mot de passe avec plus de 3 caracteres" + "</h3>");
			out.print(" <form action='sinscrire' method='GET' > ");
			out.println("nom");
			out.println("<input type='text' size='20' name='nom' >");
			out.println("<br>");
			out.println("mot de passe");
			out.println("<input type='password' size='20' name='motdepasse'> <br>");
			out.println("<input type='submit' value='inscription'>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		}
		 else if (nomCookie==null && nomRecu!=null)
		 {
			// Cas 2 : cas où il n'y a pas de cookies mais les paramètres nom et mot de passes sont présents :
			 Cookie cookie = new Cookie("nom",URLEncoder.encode( nomRecu, "UTF-8" ));
			 Cookie cookie2 = new Cookie("motdepasse",URLEncoder.encode( motPasseRecu, "UTF-8" ));
			 cookie.setMaxAge(60*60*24);//1 jours
			 cookie2.setMaxAge(60*60*24);//1 jours
			 res.addCookie(cookie);
			 res.addCookie(cookie2);
			 res.sendRedirect("/TpServlet/servlet/sinscrire");
		}
		else if (identique(nomRecu,nomCookie) && identique(motPasseRecu,motPasseCookie))
		{
			// Cas 4 : cas où le nom et le mot passe sont correctes, appel à la servlet achat
			res.sendRedirect("/TpServlet/servlet/achat");
			 
		}
		 else 
		 {
		 	// Cas 3 : les cookies sont présents demande de s'identifier
			 out.println("<html>");
				out.println("<body>");
				out.println("<head>");
				out.println("<title> S'identifier </title>");
				out.println("</head>");
				out.println("<body bgcolor='white' >");
				out.println( nomRecu +" | "+ motPasseRecu +" | "+ nomCookie +" | "+ motPasseCookie );
				out.println("<h3>" + "Bonjour, vous devez vous s'identifier " + "</h3>");
				out.print(" <form action='sinscrire' method='GET' > ");
				out.println("nom");
				out.println("<input type='text' size='20' name='nom' >");
				out.println("<br>");
				out.println("mot de passe");
				out.println("<input type='password' size='20' name='motdepasse'> <br>");
				out.println("<input type='submit' value='Identifier'>");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
		 }
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
	boolean identique (String recu, String cookie) {
		return ((recu != null) && (recu.length() >3) && (cookie != null) && (recu.equals(cookie) ));
		}
}