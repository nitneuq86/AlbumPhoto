package modele;

public class Evenement {
	private String titre;
	private String URI;
		
	public Evenement(String titre, String URI) {
		this.titre = titre;
		this.URI = URI;
	}
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	
	
}
