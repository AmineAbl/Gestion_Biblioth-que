package model;

public class Livre {

	private int id;
	private String titre;
	private String auteur;
	private String isbn;
	private Categorie categorie;
	private int quantite;

	public Livre() {
	}

	public Livre(int id, String titre, String auteur, String isbn, Categorie categorie, int quantite) {
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
		this.isbn = isbn;
		this.categorie = categorie;
		this.quantite = quantite;
	}

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return this.titre;
	}
	
	
}
