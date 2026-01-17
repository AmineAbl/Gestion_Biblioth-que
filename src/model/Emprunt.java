package model;

import java.util.Date;

public class Emprunt {

	private int id;
	private Livre livre;
	private Etudiant etudiant;
	private Date dateEmprunt;
	private Date dateRetourPrevue;
	private Date dateRetourEffective;
	private double penalite;

	public Emprunt() {
	}

	public Emprunt(int id, Livre livre, Etudiant etudiant, Date dateEmprunt, Date dateRetourPrevue,
			Date dateRetourEffective, double penalite) {
		this.id = id;
		this.livre = livre;
		this.etudiant = etudiant;
		this.dateEmprunt = dateEmprunt;
		this.dateRetourPrevue = dateRetourPrevue;
		this.dateRetourEffective = dateRetourEffective;
		this.penalite = penalite;
	}

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Date getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public Date getDateRetourPrevue() {
		return dateRetourPrevue;
	}

	public void setDateRetourPrevue(Date dateRetourPrevue) {
		this.dateRetourPrevue = dateRetourPrevue;
	}

	public Date getDateRetourEffective() {
		return dateRetourEffective;
	}

	public void setDateRetourEffective(Date dateRetourEffective) {
		this.dateRetourEffective = dateRetourEffective;
	}

	public double getPenalite() {
		return penalite;
	}

	public void setPenalite(double penalite) {
		this.penalite = penalite;
	}

	@Override
	public String toString() {
		return "Emprunt [id=" + id + ", livre=" + livre + ", etudiant=" + etudiant + ", dateEmprunt=" + dateEmprunt
				+ ", dateRetourPrevue=" + dateRetourPrevue + ", dateRetourEffective=" + dateRetourEffective
				+ ", penalite=" + penalite + "]";
	}
	
	
}
