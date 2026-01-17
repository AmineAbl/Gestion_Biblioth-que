# 📚 Application de Gestion de Bibliothèque Universitaire

## 📝 Description
Ce projet est une application desktop développée en **Java Swing** permettant de gérer une bibliothèque universitaire.  
L’application facilite la gestion des **livres**, **catégories**, **étudiants**, **emprunts**, **retours**, ainsi que la génération de **statistiques** et de **PDF**.

Le projet suit une architecture claire (**MVC + DAO**) afin de séparer l’interface graphique, la logique métier et l’accès à la base de données.

---

## 🎯 Objectifs du projet
- Automatiser la gestion d’une bibliothèque universitaire
- Simplifier la gestion des livres et des emprunts
- Générer des statistiques et des rapports PDF
- Mettre en pratique Java Swing, JDBC et les bonnes pratiques de programmation

---

## 🛠️ Technologies utilisées
- **Langage** : Java  
- **Interface graphique** : Java Swing  
- **Base de données** : MySQL  
- **Accès aux données** : JDBC  
- **Graphiques** : JFreeChart  
- **Génération PDF** : iText  
- **IDE** : NetBeans / IntelliJ  
- **JDK** : JDK 8  

---

## 🧱 Architecture du projet

Le projet est organisé selon le modèle **MVC (Model - View - Controller)** :

### 📦 Model
- `Livre`
- `Categorie`
- `Etudiant`
- `Emprunt`

### 📦 DAO
- Classes responsables des opérations CRUD
- Communication avec la base de données MySQL via JDBC

### 📦 Controller
- Gestion de la logique métier
- Appel des DAO depuis l’interface graphique

### 📦 Presentation
- Interfaces graphiques Java Swing (`JFrame`, `JPanel`, `JTable`)
- Gestion des événements avec Lambda expressions

---

## ✨ Fonctionnalités principales

### 📖 Gestion des livres
- Ajouter / Modifier / Supprimer un livre
- Recherche par titre, auteur ou ISBN
- Association d’un livre à une catégorie

### 🗂️ Gestion des catégories
- Ajout, modification et suppression des catégories

### 👨‍🎓 Gestion des étudiants
- Inscription des étudiants
- Consultation de l’historique des emprunts

### 🔄 Gestion des emprunts
- Emprunt et retour des livres
- Gestion des retards et pénalités

### 📊 Statistiques
- Livres les plus empruntés
- Fréquentation de la bibliothèque
- Visualisation avec graphiques (JFreeChart)

### 📄 Génération PDF
- Génération automatique de rapports PDF (sans créer de fichier préalable)
- Export des listes et historiques

---

## ⚙️ Base de données

- **SGBD** : MySQL  
- La base de données contient les tables :
  - `livre`
  - `categorie`
  - `etudiant`
  - `emprunt`

La connexion à la base est gérée via JDBC dans une classe dédiée.

---

## 🔐 Gestion des erreurs
- Utilisation de blocs `try-catch` pour :
  - Les erreurs de saisie utilisateur
  - Les exceptions SQL
  - Les erreurs lors de la génération de PDF

---

## 🎨 Interface graphique
- Utilisation de `JFrame` pour les fenêtres
- `JPanel` pour structurer les interfaces
- `JTable` pour afficher les données
- Personnalisation de l’arrière-plan (couleurs / images)

---

## ▶️ Lancement du projet
1. Importer le projet dans NetBeans ou IntelliJ
2. Configurer la base de données MySQL
3. Modifier les paramètres de connexion JDBC si nécessaire
4. Exécuter la classe principale (`Main`)

---

## 📌 Auteur
- **Nom** : Amine ABOU-LAICHE  
- **Projet académique** – Gestion de Bibliothèque Universitaire

---
