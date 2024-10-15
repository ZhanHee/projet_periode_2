import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./HouseDetail.css";

import { HouseLogo } from "../components/HouseLogo";
import { BadgePlus } from "lucide-react";

export const HouseDetail = () => {
  const { nom } = useParams<{ nom: string }>();

  const [maison, setMaison] = useState<any>();
  const navigate = useNavigate();

  const fetchUserData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/maison/${nom}`);
      const data = await response.json();
      setMaison(data);
    } catch (error) {
      console.error("Erreur lors de la récupération des données : ", error);
    }
  };

  useEffect(() => {
     fetchUserData();
     /*
    setMaison({
      nomMaison: "Serdaigle",
      eleves: [
        {
          id: 21,
          nom: "Lovegood",
          prenom: "Luna",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 22,
          nom: "Chang",
          prenom: "Cho",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 22,
          nom: "Chang",
          prenom: "Cho",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 22,
          nom: "Chang",
          prenom: "Cho",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 22,
          nom: "Chang",
          prenom: "Cho",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 22,
          nom: "Chang",
          prenom: "Cho",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 22,
          nom: "Chang",
          prenom: "Cho",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 22,
          nom: "Chang",
          prenom: "Cho",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 22,
          nom: "Chang",
          prenom: "Cho",
          jeton: "eleve",
          totalPoints: 0,
        },
      ],
      nbPointTotal: 0,
    });*/
  }, [nom]);

  return (
    <div className="house-detail-container" id="house">
      <div className="house-header">
        <h1 className="house-name">{maison?.nomMaison}</h1>

        <div className="house-logo">
          {maison?.nomMaison && <HouseLogo house={maison?.nomMaison} />}
        </div>
      </div>

      <div className="house-points">
        <strong>Total des Points:</strong> <span>{maison?.nbPointTotal}</span>
      </div>

      <div className="student-list">
        {maison?.eleves?.map((item: any, index: number) => (
          <div key={index} className="student-item">
            <p
              onClick={() => {
                navigate(`/eleve/${item.id}`);
              }}
              className="student-name"
            >
              {item.nom}
            </p>
            <p
              onClick={() => {
                navigate(`/eleve/${item.id}`);
              }}
              className="student-firstname"
            >
              {item.prenom}
            </p>
            <p className="student-points">{item.totalPoints}</p>
            
          </div>
        ))}
      </div>
    </div>
  );
};
