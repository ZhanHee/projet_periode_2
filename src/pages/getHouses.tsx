import React, { useEffect, useState } from "react";
import "./getHouses.css";
import { HouseLogo } from "../components/HouseLogo";
import { useNavigate } from "react-router-dom";

export const GetHouses = () => {
  const [housesData, setHousesData] = useState<any[]>([]);
  const navigate = useNavigate();

  const houseData = [
    {
      nomMaison: "Gryffondor",
      eleves: [
        {
          id: 1,
          nom: "Potter",
          prenom: "Harry",
          jeton: "eleve",
          totalPoints: 10,
        },
        {
          id: 2,
          nom: "Weasley",
          prenom: "Ron",
          jeton: "eleve",
          totalPoints: 0,
        },
        {
          id: 3,
          nom: "Granger",
          prenom: "Hermione",
          jeton: "eleve",
          totalPoints: 0,
        },
      ],
      nbPointTotal: 10,
    },
    {
      nomMaison: "Serdaigle",
      eleves: [
        {
          id: 4,
          nom: "Lovegood",
          prenom: "Luna",
          jeton: "eleve",
          totalPoints: 15,
        },
      ],
      nbPointTotal: 15,
    },
    {
      nomMaison: "Poufsouffle",
      eleves: [
        {
          id: 5,
          nom: "Diggory",
          prenom: "Cedric",
          jeton: "eleve",
          totalPoints: 20,
        },
      ],
      nbPointTotal: 20,
    },
    {
      nomMaison: "Serpentard",
      eleves: [
        {
          id: 6,
          nom: "Malfoy",
          prenom: "Draco",
          jeton: "eleve",
          totalPoints: 5,
        },
      ],
      nbPointTotal: 5,
    },
  ];
  const fetchData = async () => {
     const response = await fetch(`http://localhost:8080/maison`);
    const data = await response.json();
    setHousesData(data);
    //setHousesData(houseData);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="back">
      <div className="houses-container">
        {housesData?.map((house, index) => (
          <div
            className={`house-card ${house?.nomMaison.toLowerCase()}`}
            key={index}
            onClick={() => {
              navigate(`/maison/${house?.nomMaison}`);
            }}
          >
            <div className="logoHous">
              <HouseLogo house={house?.nomMaison} />
            </div>
            <h2 className="house-title">Maison {house?.nomMaison}</h2>
            <p className="house-total-points">
              Points Totaux : {house?.nbPointTotal}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};
