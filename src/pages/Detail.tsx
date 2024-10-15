import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./Detail.css";
import { HouseLogo } from "../components/HouseLogo";
import { BadgePlus } from "lucide-react";

interface User {
  id: number;
  nom: string;
  prenom: string;
  totalPoints?: number;
  nomMaison?: string;
  specialite?: string;
  nomMatiere?: string;
}

export const Detail = ({
  path,
  loggedUser,
  getBestHouse,
}: {
  path: string;
  loggedUser: any;
  getBestHouse: () => Promise<void>;
}) => {
  const { id } = useParams<{ id: string }>();
  const [user, setUser] = useState<User>();
  const [points, setPoints] = useState<boolean>(false);
  const [numberPoints, setNumberPoints] = useState<number>(0);
  const navigate = useNavigate();
  const [defis, setDefis] = useState<boolean>(false);
  const [mise, setMise] = useState<number>(0);
  const [message, setMessage] = useState<string>("");

  const fetchUserData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/${path}/${id}`);
      const data = await response.json();
      setUser(data);
      console.log(path, id)
    } catch (error) {
      console.error("Erreur lors de la récupération des données : ", error);
    }
  };

  useEffect(() => {
    fetchUserData();
    /*
    setUser({
      id: 4,
      totalPoints: 0,
      nom: "Longbottom",
      prenom: "Neville",
      nomMaison: "Gryffondor",
    });*/
    console.log(user, "ixi")
  }, [id]);
    
  const addPoints = async () => {
    await fetch("http://localhost:8080/evaluer", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        idEleve: id,
        idProfesseur: loggedUser?.id,
        nbPoints: numberPoints,
      }),
    });
    fetchUserData();
    getBestHouse();
  };

  const envoyerDefi = async () => {
    try {
        await fetch("http://localhost:8080/eleve/propositionPartie", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              idJoueurCible: user?.id,
              idJoueurSource: loggedUser.id,
              mise: mise,
            }),
          });
          setMessage("Défi envoyé");

    } catch (error) {
        setMessage("Erreur lors de l'envoi du défi");
    }
    

    fetchUserData();
  };

  return (
    <div className="profile-container">
      <div className="title">
        <h1 className="profile-name">
          {user?.prenom} {user?.nom}
        </h1>

        <div className="logoMaison">
          {user?.nomMaison && <HouseLogo house={user?.nomMaison} />}
        </div>
      </div>

      <h2>Détails du Profil</h2>
      <div className="profile-details">
        <div className="detail-item">
          <strong>Identifiant:</strong> <span>{id}</span>
        </div>
        
        {user?.nomMaison !== undefined ?
        <>
          <div className="detail-item">
            <strong>Maison:</strong> <span>{user?.nomMaison}</span>
          </div>
          <div className="detail-item">
            <strong>Total des Points:</strong> <span>{user?.totalPoints}</span>
          </div>
        </> : 
        <div className="detail-item">
        <strong>Nom matière:</strong> <span>{user?.nomMatiere}</span>
        </div>

          

        }
      </div>
      <div className="profile-footer">
        {user &&
        loggedUser?.fonction === "eleve" &&
        user?.nomMaison !== loggedUser?.nomMaison &&
        defis !== true && user?.nomMatiere == null &&(
          <button onClick={() => setDefis(true)}>Défier</button>
        ) }
        { defis && (
          <div>
            <div className="box">
              <span>{message}</span>
              <input
                type="number"
                value={mise}
                min={0}
                onChange={(e) => {
                  setMise(parseInt(e.target.value));
                }}
              />
              <button onClick={()=>envoyerDefi()}>Valider</button>
            </div>
          </div>
        )}



        {loggedUser?.fonction === "professeur" && (
    user?.nomMaison === "Gryffondor" || 
    user?.nomMaison === "Serpentard" || 
    user?.nomMaison === "Poufsouffle" || 
    user?.nomMaison === "Serdaigle"
  ) && (
          <div>
            {points ? (
              <div className="box">
                <input
                  type="number"
                  value={numberPoints}
                  onChange={(e) => setNumberPoints(parseInt(e.target.value))}
                />
                <div
                  onClick={() => {
                    addPoints();
                  }}
                >
                  <BadgePlus className="logo" color="gold" />
                </div>
              </div>
            ) : 


            (<button onClick={() => setPoints(true)}>
              Ajouter des points
            </button> )}

          </div>
        )}

        {loggedUser?.fonction === "Directeur" && (
    user?.nomMaison === "Gryffondor" || 
    user?.nomMaison === "Serpentard" || 
    user?.nomMaison === "Poufsouffle" || 
    user?.nomMaison === "Serdaigle"
  ) && (
          <div>
            {points ? (
              <div className="box">
                <input
                  min="0"
                  max="20"
                  type="number"
                  value={numberPoints}
                  onChange={(e) => setNumberPoints(parseInt(e.target.value))}
                />
                <div
                  onClick={() => {
                    addPoints();
                  }}
                >
                  <BadgePlus className="logo" color="gold" />
                </div>
              </div>
            ) : 


            (<button onClick={() => setPoints(true)}>
              Ajouter des points
            </button> )}

          </div>
        )}

        <button
          onClick={() => {
            navigate(`/${path}s`);
          }}
        >
          Retour
        </button>
      </div>
    </div>
  );
};
