package mesCommandes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;

import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class EnregistrerCommande extends HttpServlet {
	
	Connection connexion=null;
	Statement stmt=null;
	PreparedStatement pstmt=null;
	@SuppressWarnings("rawtypes")
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{ 
		String nom = null;
		//Cookie[] cookies = request.getCookies();
		//boolean connu = false;
		//nom = Identification. chercheNom (cookies);
		nom=(String)request.getAttribute("nom");
		OuvreBase();
		AjouteNomBase(nom);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title> votre commande </title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3>" + "Bonjour " + nom + " voici ta nouvelle commande" + "</h3>");
		
		HttpSession session = request.getSession();
		Enumeration names = session.getAttributeNames();
		if(!names.hasMoreElements()) {
			out.println("Aucune commande à enregitrer");
		}
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = session.getAttribute(name).toString();
			out.println(name + " = " + value + "<br>");
		}
		AjouteCommandeBase(nom,session);
		out.println("<h3>" + "et voici " + nom + " ta commande complete" + "</h3>");
		MontreCommandeBase(nom, out);
		out.println("<A HREF=vider> Vous pouvez commandez un autre disque </A><br> ");
		out.println("</body>");
		out.println("</html>");
		fermeBase();
	}
	@SuppressWarnings("deprecation")
	protected void OuvreBase() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoncmd_magasin?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","invoncmd_root","wOM^BWs$.d~o");
			connexion.setAutoCommit(true);
			stmt = connexion.createStatement();
		}
		catch (Exception E) {
			log(" -------- probeme " + E.getClass().getName() );
			E.printStackTrace();
		}
	}
	protected void fermeBase() {
		try {
			stmt.close();
			connexion.close();
		}
		catch (Exception E) {
			log(" -------- probeme " + E.getClass().getName() );
			E.printStackTrace();
		}
	}
	protected void AjouteNomBase(String nom) {
		try {
			ResultSet rset = null;
			pstmt= connexion.prepareStatement("select numero from personnel where nom=?");
			pstmt.setString(1,nom);
			rset=pstmt.executeQuery();
			if (!rset.next())
				stmt.executeUpdate("INSERT INTO personnel (nom) VALUES ('" + nom + "' )" );
		}
		catch (Exception E) {
			log(" - probleme " + E.getClass().getName() );
			E.printStackTrace();
		}
	}
	@SuppressWarnings("rawtypes")
	protected void AjouteCommandeBase(String nom, HttpSession session ) {
		// ajoute le contenu du panier dans la base
		Enumeration commades = session.getAttributeNames();
		int numero=0;
		try {
			ResultSet rset = null;
			pstmt= connexion.prepareStatement("select numero from personnel where nom=?");
			pstmt.setString(1,nom);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				numero=rset.getInt("numero");
			}if(numero!=0) {
				while (commades.hasMoreElements()) {
					String commade = (String) commades.nextElement();
					String value = session.getAttribute(commade).toString();
					pstmt=connexion.prepareStatement("INSERT INTO COMMANDE (ARTICLE,QUI) VALUES (?,?)");
					pstmt.setString(1, value);
					pstmt.setInt(2, numero);
					pstmt.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	protected void MontreCommandeBase(String nom, PrintWriter out) {
		// affiche les produits présents dans la base
		int numero=0;
		try {
			ResultSet rset = null;
			pstmt= connexion.prepareStatement("select numero from personnel where nom=?");
			pstmt.setString(1,nom);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				numero=rset.getInt("numero");
			}
			//recuperer les commendes par ordre decroissant
			pstmt=connexion.prepareStatement("SELECT * FROM COMMANDE WHERE QUI=? ORDER BY NUM DESC");
			pstmt.setInt(1, numero);
			ResultSet rs=pstmt.executeQuery();
		    
			out.println("<table border=1>");
			out.println("<thead>");
			out.println( "<tr><th>Num</th>");
			out.println( "<th>Article</th>");
			out.println( "<th>Nom</th></tr>");
			out.println("</thead>");
			out.println("<tbody>");
			while(rs.next()) {
				out.println( "<tr><td>"+rs.getInt("num")+"</td>");
				out.println( "<td>"+rs.getString("article")+"</td>");
				out.println( "<td>"+nom+"</td></tr>");
			}
			out.println("</tbody>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		doGet(request, response);
	}
}