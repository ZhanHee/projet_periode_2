import "./Menu.css";
import { useNavigate } from "react-router-dom";

export const Menu = ({loggedUser}: any) => {
  const navigate = useNavigate();

  return (
    <div className="menu">
      <ul>
        {loggedUser && loggedUser.fonction === "eleve" && (<li onClick={()=>navigate("/MonProfil")}>Mon profil</li>)}
        <li onClick={()=>navigate("/eleves")}>Trouver un élève</li>
        <li onClick={()=>navigate("/professeurs")}>Trouver un professeur</li>
        <li onClick={()=>navigate("/maisons")}>Classement des maisons</li>

        {loggedUser && loggedUser.fonction === "Directeur" && (<li onClick={()=>navigate("/ajouterEleves")}>Ajouter un sorcier</li>)}
        {loggedUser && loggedUser.fonction === "Directeur" && (<li onClick={()=>navigate("/ajouterProfesseur")}>Ajouter un professeur</li>)}
        {loggedUser && loggedUser.fonction === "eleve" && (<li onClick={()=>navigate("/jeu")}>Coupe de feu</li>)}
      </ul>
    </div>
  );
};
