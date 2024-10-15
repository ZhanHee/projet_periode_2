import React, { useEffect, useState } from "react";
import "./getHouses.css";
import { HouseLogo } from "../components/CoupLogo";
import { useNavigate } from "react-router-dom";

export const ChifoumiEqual = () => {
    // const [ChifoumiData, setChifoumiData] = useState<any[]>([]);
    const navigate = useNavigate();
    const [monCoupImage, setmonCoupImage] = useState("baguette");
    const [otherCoupImage, setOtherCoupImage] = useState("baguette");
    const [autreround, setAutreround] = useState(false);

    const fetchData = async () => {
        try {
            const response1 = await fetch(`ici c'est le chemin de ChifoumiPage avec idEleve`);
            const data1 = await response1.json();
            setmonCoupImage(data1.imageUrl);

            const response2 = await fetch(`ici c'est le chemin de ChifoumiPage avec idEleve de l'autre jouer`);
            const data2 = await response2.json();
            setOtherCoupImage(data2.imageUrl);
            setAutreround(data2.autreround);//pour decider si il y a une autre tourne;
        } catch (error) {
            console.error("Error fetching images:", error);
        }
    };

    useEffect(() => {
      fetchData();
    }, []);

    useEffect(() => {
        if (autreround) {
            const timer = setTimeout(() => {
                navigate("/nextPage"); // ici c'est le chemin vers le deuxieme tourne
            }, 3000);

            return () => clearTimeout(timer);
        }
    }, [autreround, navigate]);

    return (
        <div className="back">
            <br/><br/>
            <h1>C'est un equal !</h1>
            <div className="houses-container">
                <div className="house-card left">
                    <h2 className="house-title">votre coupe
                        <div className="logoHous">
                            {monCoupImage && (
                                <img src={`../../public/${monCoupImage}.png`}
                                     alt="mon coup"
                                     className="logoHous"/>
                            )}
                        </div>
                    </h2>

                </div>
                <div className="house-card right">
                    <h2 className="house-title">other coupe</h2>
                    <div className="logoHous">
                        {otherCoupImage && (
                            <img src={`../../public/${otherCoupImage}.png`}
                                 alt="autre coup"
                                 className="logoHous"/>
                        )}
                    </div>
                </div>
            </div>
            <h1>{autreround ? "le match continue.." : "le match est terminee"}</h1>
        </div>
    );
};
