package extra;

import java.util.ArrayList;
import java.util.Date;

import controleur.Connexion;
import modele.Album;
import modele.Personne;
import modele.Utilisateur;

public class Data {
	private static ArrayList<Utilisateur> bddUtilisateurs = new ArrayList<Utilisateur>();

	public static Album getAlbumTest() {
		Album album = new Album("Mes photos perso", new Date(), new Personne("Myriam", "Abdel-Fattah"));
		album.ajouterPhoto("http://img0.mxstatic.com/wallpapers/44e535006cffbc1b6e41f72d5e9df1e4_large.jpeg");
		album.ajouterPhoto("http://www.unesourisetmoi.info/wall32/images/paysage-fonds-ecran_04.jpg");
		album.ajouterPhoto("http://lemag.promovacances.com/wp-content/uploads/2013/08/Paysage-portugal.jpg");
		album.ajouterPhoto("http://en.lesalondelaphoto.com/var/comexposium/storage/images/media/salon-de-la-photo-medias/images/suivre-le-salon-de-la-photo-sur-les-reseaux-sociaux/1482353-1-fre-FR/Suivre-le-Salon-de-la-photo-sur-les-reseaux-sociaux_Tuile_980x430.jpg");
		album.ajouterPhoto("http://www.lechemin.ch/wp-content/uploads/2015/05/connaitre.jpg");
		album.ajouterPhoto("http://www.laboiteverte.fr/wp-content/uploads/2015/01/meilleure-image-flickr-29.jpg");
		album.ajouterPhoto("http://cssslider.com/sliders/demo-26/data1/images/summerfield336672_1280.jpg");
		album.ajouterPhoto("http://img00.deviantart.net/ae17/i/2013/118/4/6/rainbow_flower_by_i_is_kitty-d5l8o1g.jpg");
		album.ajouterPhoto("http://cdn.theatlantic.com/assets/media/img/photo/2015/10/photos-of-the-week-926-1002/w04_RTS2PNN/main_900.jpg?1443979188");
		return album;
	}

	public static Utilisateur verificationUtilisateur(String login, String pass) {
		bddUtilisateurs.add(new Utilisateur(new Personne("Myriam", "Abdel-Fattah"), "Myrga"));
		bddUtilisateurs.add(new Utilisateur(new Personne("Quentin", "Durand"), "Nitneuq"));

		boolean UtilisateurTrouve = false;
		int i = 0;
		while (!UtilisateurTrouve && i < bddUtilisateurs.size()) {
			if (bddUtilisateurs.get(i).getLogin().equals(login)) {
				UtilisateurTrouve = true;
				if (pass.equals("prout")) {
					return bddUtilisateurs.get(i);
				}
			}
			i++;
		}
		return null;
	}
}
