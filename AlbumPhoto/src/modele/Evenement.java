package modele;

public class Evenement {
	private String name;
	private String URI;
		
	public Evenement(String name, String uRI) {
		super();
		this.name = name;
		URI = uRI;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	
	
}
