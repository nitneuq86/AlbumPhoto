package extra;

import java.util.ArrayList;
import java.util.Date;

import modele.Album;
import modele.Personne;
import modele.Utilisateur;

public class Data {
	private static ArrayList<Utilisateur> bddUtilisateurs = new ArrayList<Utilisateur>();
	
	public static Album getAlbumTest() {
		Album album = new Album("Vacances en Floride", "Quentin", new Date());
		album.ajouterPhoto("photo1.jpg");
		album.ajouterPhoto("photo7.jpg");
		album.ajouterPhoto("photo23.jpg");
		return album;
	}
	
	public static Utilisateur verificationUtilisateur(String login, String pass){
		bddUtilisateurs.add(new Utilisateur(new Personne("Myriam", "Abdel-Fattah"), "Myrga"));
		bddUtilisateurs.add(new Utilisateur(new Personne("Quentin", "Durand"), "Nitneuq"));
		
		boolean UtilisateurTrouve = false;
		int i=0;
		while(!UtilisateurTrouve && i<bddUtilisateurs.size()){
			if(bddUtilisateurs.get(i).getLogin().equals(login)){
				UtilisateurTrouve = true;
				if(pass.equals("prout")){
					return bddUtilisateurs.get(i);
				}
			}
			i++;
		}
		return null;
	}
}
