import React, { useEffect, useState } from "react";
import "./getHouses.css";
import { HouseLogo } from "../components/CoupLogo";
import { useNavigate } from "react-router-dom";

export const ChifoumiPage = () => {
  // const [ChifoumiData, setChifoumiData] = useState<any[]>([]);
  const navigate = useNavigate();
  const [choisi, setChoisi] = useState(false);

  const ChifoumiData = [
    {
      nomMaison: "Gryffondor",
      nomCoup: "Balais"
    },
    {
      nomMaison: "Serdaigle",
      nomCoup: "Chaudron"

    },
    {
      nomMaison: "Poufsouffle",
      nomCoup: "Baguette"


    },
  ];
  const fetchData = async () => {
    const response = await fetch(`http://localhost:8080/maison`);
    const data = await response.json();
    setChifoumiData(data);
    setChoisi(data.choisi);
    //setHousesData(houseData);
  };

  // useEffect(() => {
  //   fetchData();
  // }, []);

  return (
      <div className="back">
        <br/><br/>
        <h1>choisissez votre coup !</h1>
        <div className="houses-container">
          {ChifoumiData?.map((house, index) => (
              <div
                  className={`house-card ${house?.nomMaison.toLowerCase()}`}
                  key={index}
                  onClick={() => {
                    // navigate(`/maison/${house?.nomMaison}`);
                    if (choisi == true) {
                      setChoisi(false);
                    }else {
                      setChoisi(true);
                    }
                  }}
              >
                <div className="logoHous">
                  <HouseLogo house={house?.nomMaison}/>
                </div>
                <h2 className="house-title">{house?.nomCoup}</h2>
              </div>
          ))}
        </div>
        <h2 className="no-underline">{!choisi ? "Rôle : Balai > Chaudron > Baguette" : "waiting for opponent"}</h2>
      </div>
  );
};
