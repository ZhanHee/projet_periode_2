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
}

interface Note {
  nomMatiere: string;           // Nom de la matière
  note: number;                 // Note obtenue
  dateEval: string;             // Date de l'évaluation au format ISO (chaîne de caractères)
}

export const MonProfil = ({
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
  const [notes, setNotes] = useState<Note[]>([]);
  const [points, setPoints] = useState<boolean>(false);
  const [numberPoints, setNumberPoints] = useState<number>(0);
  const navigate = useNavigate();
  const [defis, setDefis] = useState<boolean>(false);
  const [mise, setMise] = useState<number>(0);

  const fetchUserData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/eleve/${loggedUser?.id}`);
      const data = await response.json();
      setUser(data);
      console.log(path, id)
    } catch (error) {
      console.error("Erreur lors de la récupération des données : ", error);
    }
  };

  const fetchUserNotes = async () => {
    try {
      const response = await fetch(`http://localhost:8080/evaluer/${loggedUser?.id}`);
      const data = await response.json();
      setNotes(data);  // Mise à jour de l'état avec les notes récupérées
      console.log("Notes récupérées : ", data);
    } catch (error) {
      console.error("Erreur lors de la récupération des notes : ", error);
    }
  };


  useEffect(() => {
    fetchUserData();
    fetchUserNotes();
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
        </div>
        
        {user?.nomMaison !== undefined ?
        <>
          <div className="detail-item">
            <strong>Maison:</strong> <span>{user?.nomMaison}</span>
          </div>
          <div className="detail-item">
            <strong>Total des Points:</strong> <span>{user?.totalPoints}</span>
          </div>



    <div className="detail-item">
      <h2>Mes notes</h2>
      <span>
        {notes.length > 0 ? (
          <ul>
            {notes.map((note, index) => (
              <li key={index}>
                <p> {note.nomMatiere} - {note.note}</p>
              </li>
            ))}
          </ul>
        ) : (
          <p>Aucune évaluation trouvée.</p>
        )}
      </span>
    </div>



        </> : 
        <div className="detail-item">
        </div>

          

        }
      </div>
      <div className="profile-footer">
        { defis && (
          <div>
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
