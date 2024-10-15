import React, { useEffect, useState } from "react";
import "./getHouses.css";
import { HouseLogo } from "../components/CoupLogo";
import { useNavigate } from "react-router-dom";

export const ChifoumiResultat = () => {
    // const [ChifoumiData, setChifoumiData] = useState<any[]>([]);
    const navigate = useNavigate();
    const [monScore, setmonCoupImage] = useState("0");
    const [otherScore, setOtherCoupImage] = useState("0");
    const [monMaison,serMonMaison] = useState("Serpentard")
    const [otherMaison,serOtherMaison] = useState("Serpentard")

    const fetchData = async () => {
        try {
            const response1 = await fetch(`ici c'est le chemin de ChifoumiPage avec idEleve`);
            const Score1 = await response1.json();
            setmonCoupImage(Score1);

            const response2 = await fetch(`ici c'est le chemin de ChifoumiPage avec idEleve de l'autre jouer`);
            const score2 = await response2.json();
            setOtherCoupImage(score2);
        } catch (error) {
            console.error("Error fetching images:", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);



    return (
        <div className="back">
            <br/><br/>
            <h1>final Score</h1>
            <div className="center">
                <h2 className="no-underline">monScore : otherScore</h2>
                <h1>{monScore} : {otherScore}</h1>
            </div>
            <div className="houses-container">
                <div className={`house-card left ${monMaison.toLowerCase()}`}>
                    <h2 className="house-title">Mon Maison</h2>
                        <div className="logoHous">
                            {monMaison && (
                                <img src={`../../public/${monMaison}.png`}
                                     alt="mon coup"
                                     className="logoHous"
                                     onClick={() => {
                                         navigate(`/maison/${monMaison}`);
                                     }}
                                />
                            )}
                        </div>


                </div>
                <div className={`house-card right ${monMaison.toLowerCase()}`}>
                    <h2 className="house-title">Other Maison</h2>
                    <div className="logoHous">
                        {otherMaison && (
                            <img src={`../../public/${otherMaison}.png`}
                                 alt="autre coup"
                                 className="logoHous"
                                 onClick={() => {
                                     navigate(`/maison/${otherMaison}`);
                                 }}
                            />

                        )}

                    </div>
                </div>
            </div>
        </div>
    );
};
