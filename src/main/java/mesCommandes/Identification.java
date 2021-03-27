package mesCommandes;

import javax.servlet.http.Cookie;

public class Identification {
	static String chercheNom (Cookie [] cookies) {
		String nom=null;
		// cherche dans les cookies la valeur de celui qui se nomme "nom"
		if(cookies ==null)return null;
		for(Cookie cookie : cookies ) {
			if(cookie.getName().equals("nom")) { 
				nom=cookie.getValue().replace("+", " ");
			}
		}
		// retourne la valeur de ce nom au lieu de inconnu
		return nom;
		}
}	
