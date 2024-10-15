import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { HouseLogo } from "../components/HouseLogo";
import "./GetAll.css";

export const GetAll = ({ about }: { about: number }) => {
  const [name, setName] = useState<string>("");
  const [list, setList] = useState<any[]>([]);


  const navigate = useNavigate();
  const path = about == 1 ? "eleve" : "professeur";

  const fetchData = async (arg: string) => {
    if (arg === "") {
      const response = await fetch(`http://localhost:8080/${path}`);
      const data = await response.json();
      setList(data);
    } else {
      console.log(`http://localhost:8080/${path}?filter=${arg}`)
      const response = await fetch(
        `http://localhost:8080/${path}?filter=${arg}`
      );
      const data = await response.json();
      setList(data);
    }
  };

  useEffect(() => {
    console.log(name)
    fetchData(name);
  }, [name]);

  useEffect(() => {
    fetchData("");
    /*
    setList([
      {
        id: 1,
        nom: "Dupont",
        prenom: "Jean",
        totalPoints: 120,
        nomMaison: "Gryffondor",
      },
      {
        id: 2,
        nom: "Durand",
        prenom: "Marie",
        totalPoints: 150,
        nomMaison: "Serdaigle",
      },
      {
        id: 3,
        nom: "Martin",
        prenom: "Pierre",
        totalPoints: 110,
        nomMaison: "Poufsouffle",
      },
      {
        id: 4,
        nom: "Lefevre",
        prenom: "Sophie",
        totalPoints: 130,
        nomMaison: "Serpentard",
      },
      {
        id: 5,
        nom: "Petit",
        prenom: "Lucas",
        totalPoints: 145,
        nomMaison: "Gryffondor",
      },
      {
        id: 6,
        nom: "Moreau",
        prenom: "Elodie",
        totalPoints: 135,
        nomMaison: "Serdaigle",
      },
      {
        id: 7,
        nom: "Garcia",
        prenom: "Julien",
        totalPoints: 125,
        nomMaison: "Poufsouffle",
      },
      {
        id: 8,
        nom: "Rousseau",
        prenom: "Emma",
        totalPoints: 160,
        nomMaison: "Serpentard",
      },
      {
        id: 9,
        nom: "Blanc",
        prenom: "Lucie",
        totalPoints: 105,
        nomMaison: "Gryffondor",
      },
      {
        id: 10,
        nom: "Faure",
        prenom: "Hugo",
        totalPoints: 140,
        nomMaison: "Serdaigle",
      },
      {
        id: 11,
        nom: "Bernard",
        prenom: "Laura",
        totalPoints: 115,
        nomMaison: "Poufsouffle",
      },
      {
        id: 12,
        nom: "Girard",
        prenom: "Paul",
        totalPoints: 155,
        nomMaison: "Serpentard",
      },
    ]);*/
  }, []);


  return (
    <div className="container">
      <h2>Liste des {about == 1 ? "élèves" : "Professeurs"}</h2>
      <div className="searchBar">
        <input
          type="text"
          placeholder="Rechercher"
          onChange={(e) => setName(e.target.value)}
          value={name}
        />
      </div>
      

      <div className="list">
        {list.map((item, index) => (
          <div
            key={index}
            className="item"
            
          >
            <p onClick={() => {
              navigate(`/${path}/${item.id}`);
            }}>{item.nom}</p>
            <p onClick={() => {
              navigate(`/${path}/${item.id}`);
            }}>{item.prenom}</p>
            <p>{item.totalPoints}</p>
            {about == 1 &&
             <p>
              <div className="houselogo" onClick={()=>{navigate(`/maison/${item.nomMaison}`)}}>
                <HouseLogo house={item.nomMaison} />
              </div>
            </p>
            }
        
          </div>
        ))}
      </div>
    </div>
  );
};
