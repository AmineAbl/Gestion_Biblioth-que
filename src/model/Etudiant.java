package model;

public class Etudiant {

	private int id;
	private String nom;
	private String prenom;
	private String filiere;
	private String email;

	public Etudiant() {
	}

	public Etudiant(int id, String nom, String prenom, String filiere, String email) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.filiere = filiere;
		this.email = email;
	}

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return this.nom +" "+this.prenom;
	}
	
	
}
