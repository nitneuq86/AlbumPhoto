package modele;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;

@Entity
public class Album {
	@Id
	@GeneratedValue 
	private int id;
	private String titre;
	@ManyToOne
	private Personne createur;
	private Date dateCreation;
	private ArrayList<String> photos;
	
	public Album() {}
	
	public Album(String titre, Date dateCreation, Personne createur) {
		this.titre = titre;
		this.createur = createur;
		this.dateCreation = dateCreation;
		createur.getAlbums().add(this);
		this.photos = new ArrayList<String>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Personne getCreateur() {
		return createur;
	}

	public void setCreateur(Personne createur) {
		this.createur = createur;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public Date getDateCreation() {
		return dateCreation;
	}
	
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	public ArrayList<String> getPhotos(){
		return this.photos;
	}

	public void ajouterPhoto(String photo) {
		if(photo != null)
			this.photos.add(photo);
	}
}