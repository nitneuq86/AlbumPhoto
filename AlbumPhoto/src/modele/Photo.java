package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Photo {
	
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private Album album;
	private Personne createur;
	private String uri;
	
	public Photo() {
	}

	public Photo(Album album, Personne createur, String uri) {
		this.album = album;
		this.createur = createur;
		this.uri = uri;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Personne getCreateur() {
		return createur;
	}

	public void setCreateur(Personne createur) {
		this.createur = createur;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
