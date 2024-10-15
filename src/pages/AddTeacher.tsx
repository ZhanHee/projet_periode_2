import React, { useState } from "react";
import "./AddTeacher.css";
import { HouseLogo } from "../components/HouseLogo";


export const AddTeacher = () => {



  const [nom, setNom] = useState("");
  const [prenom, setPrenom] = useState("");
  const [nomMatiere, setnomMatiere] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Vous pouvez ajouter une logique pour envoyer ces données à votre backend ici

    // Exemple de validation simple
    if (!nom || !prenom || !nomMatiere) {
      setErrorMessage("Tous les champs doivent être remplis !");
      return;
    }

    setErrorMessage("");

    const requestBody = {
      nom,
      prenom,
      nomMatiere,
    };
    
    await fetch("http://localhost:8080/professeur", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    })

    

    setNom("");
    setPrenom("");
    setnomMatiere("");
  };

  return (
    <div className="wizzard-box">
      <div className="add-wizard-container">
        <h1>Ajouter un Professeur</h1>
        <form className="add-wizard-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="nom">Nom</label>
            <input
              type="text"
              id="nom"
              value={nom}
              onChange={(e) => setNom(e.target.value)}
              placeholder="Entrez le nom"
            />
          </div>

          <div className="form-group">
            <label htmlFor="prenom">Prénom</label>
            <input
              type="text"
              id="prenom"
              value={prenom}
              onChange={(e) => setPrenom(e.target.value)}
              placeholder="Entrez le prénom"
            />
          </div>

          <div className="form-group">
            <select onChange={(e) => setnomMatiere(e.target.value)}>

              <option value="">Sélectionnez une matière</option> {/* Option par défaut */}
              <option value="Botanique">Botanique</option>
              <option value="Défense contre les forces du Mal">Défense contre les forces du Mal</option>
              <option value="Métamorphose">Métamorphose</option>
              <option value="Potions">Potions</option>
              <option value="Sortilèges">Sortilèges</option>

            </select>
          </div>

          {errorMessage && <p className="error-message">{errorMessage}</p>}

          <button type="submit" className="add-wizard-btn">
            Ajouter Professeur
          </button>
        </form>
      </div>
    </div>
  );
};
