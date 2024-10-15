import { useEffect, useState } from "react";
import "./Navbar.css";
import { useNavigate } from "react-router-dom";
import { HouseLogo } from "./HouseLogo";

export const Navbar = ({loggedUser, setLoggedUser, bestHouse}:any) => {

    

    const navigate = useNavigate();
    useEffect(() => {
        console.log(loggedUser);
    }
    , [loggedUser]);
    console.log(loggedUser);

    

    return (
        <nav className="navbar">
            <h1>MIAGIE</h1>
            <div className="classement">
                {/* <div className="titre">Miason en tête</div> */}
                <div className="logocont"><HouseLogo house={bestHouse?.nomMaison}/></div>
                <span>{bestHouse?.nbPointTotal}</span>

            </div>
            <ul>
                <li onClick={()=>navigate("/")}>Menu principal</li>
                {
                    !loggedUser ? <li onClick={()=>{navigate("/login")}}>LogIn</li> : <li onClick={()=>{
                        setLoggedUser(null);
                        navigate("/")}}>LogOut {loggedUser?.prenom}</li>
                }
                
            </ul>
        </nav>
    )
};