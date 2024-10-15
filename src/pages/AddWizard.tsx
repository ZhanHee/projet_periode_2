import React, { useState } from "react";
import "./AddWizard.css";
import { HouseLogo } from "../components/HouseLogo";

export const AddWizard = () => {
  const [nom, setNom] = useState("");
  const [prenom, setPrenom] = useState("");
  const [nomMaison, setNomMaison] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Vous pouvez ajouter une logique pour envoyer ces données à votre backend ici

    // Exemple de validation simple
    if (!nom || !prenom || !nomMaison) {
      setErrorMessage("Tous les champs doivent être remplis !");
      return;
    }

    setErrorMessage("");

    const requestBody = {
      nom,
      prenom,
      nomMaison,
    };
    
    await fetch("http://localhost:8080/eleve", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    })

    

    setNom("");
    setPrenom("");
    setNomMaison("");
  };

  return (
    <div className="wizzard-box">
      <div className="add-wizard-container">
        <h1>Ajouter un Sorcier</h1>
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
            <label htmlFor="nomMaison">Maison : {nomMaison}</label>
            <div className="maisonSelector">
              <div
                onClick={() => {
                  setNomMaison("Gryffondor");
                }}
              >
                <HouseLogo house="Gryffondor"></HouseLogo>
              </div>
              <div
                onClick={() => {
                  setNomMaison("Serdaigle");
                }}
              >
                <HouseLogo house="Serdaigle"></HouseLogo>
              </div>
              <div
                onClick={() => {
                  setNomMaison("Poufsouffle");
                }}
              >
                <HouseLogo house="Poufsouffle"></HouseLogo>
              </div>
              <div
                onClick={() => {
                  setNomMaison("Serpentard");
                }}
              >
                <HouseLogo house="Serpentard"></HouseLogo>
              </div>
            </div>
          </div>

          {errorMessage && <p className="error-message">{errorMessage}</p>}

          <button type="submit" className="add-wizard-btn">
            Ajouter Sorcier
          </button>
        </form>
      </div>
    </div>
  );
};
