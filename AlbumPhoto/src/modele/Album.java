package modele;

import java.util.ArrayList;
import java.util.Date;

public class Album {
	private String titre;
	private ArrayList<String> photos;
	private String createur;
	private Date dateCreation;
	
	public Album(String titre, String createur, Date dateCreation) {
		this.titre = titre;
		this.createur = createur;
		this.dateCreation = dateCreation;
		this.photos = new ArrayList<>();
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getCreateur() {
		return createur;
	}

	public Date getDateCreation() {
		return dateCreation;
	}
	
	public ArrayList<String> getPhotos() {
		return photos;
	}

	public void ajouterPhoto(String photo) {
		this.photos.add(photo);
	}
}
