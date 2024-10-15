import React from "react";
import "./Home.css"; 
import { useState } from "react";
import { Menu } from "../components/Menu";
import "./Login.css";

const Home = ({loggedUser}: any) => {
  const [isMapOpen, setIsMapOpen] = useState(false);
  const [cooldown, setCooldown] = useState(false);

  const toggleMap = () => {
    if (isMapOpen) {
      setCooldown(false);
    } else {
      setTimeout(() => {
        setCooldown(true);
      }, 2200);
    }
    setIsMapOpen(!isMapOpen);
    document.querySelector(".map-base")?.classList.toggle("active");
  };

  return (
    <div className="background-image">
      <div className="main-content ">
        {isMapOpen && cooldown && <Menu loggedUser={loggedUser}/>}
        <div className="map-base">
          <div className="footsteps footsteps-1">
            <div className="footstep left"></div>
            <div className="footstep right"></div>
            <div className="scroll-name">
              <p>Severus Snape</p>
            </div>
          </div>
          <div className="footsteps footsteps-2">
            <div className="footstep left"></div>
            <div className="footstep right"></div>
            <div className="scroll-name">
              <p>Harry Potter</p>
            </div>
          </div>
          <div className="map-flap flap--1">
            <div className="map-flap__front"></div>
            <div className="map-flap__back"></div>
          </div>
          <div className="map-flap flap--2">
            <div className="map-flap__front"></div>
            <div className="map-flap__back"></div>
          </div>
          <div className="map-side side-1">
            <div
              className="front"
              style={
                {
                  "--image":
                    "url('https://meowlivia.s3.us-east-2.amazonaws.com/codepen/map/8.png')",
                } as React.CSSProperties
              }
            ></div>
            <div className="back"></div>
          </div>
          <div className="map-side side-2">
            <div
              className="front"
              style={
                {
                  "--image":
                    "url('https://meowlivia.s3.us-east-2.amazonaws.com/codepen/map/18.png')",
                } as React.CSSProperties
              }
            ></div>
            <div className="back"></div>
          </div>
          <div className="map-side side-3">
            <div
              className="front"
              style={
                {
                  "--image":
                    "url('https://meowlivia.s3.us-east-2.amazonaws.com/codepen/map/7.png')",
                } as React.CSSProperties
              }
            ></div>
            <div className="back"></div>
          </div>
          <div className="map-side side-4">
            <div
              className="front"
              style={
                {
                  "--image":
                    "url('https://meowlivia.s3.us-east-2.amazonaws.com/codepen/map/10.png')",
                } as React.CSSProperties
              }
            ></div>
          </div>
          <div className="map-side side-5">
            <div
              className="front"
              style={
                {
                  "--image":
                    "url('https://meowlivia.s3.us-east-2.amazonaws.com/codepen/map/6.png')",
                } as React.CSSProperties
              }
            ></div>
            <div className="back"></div>
          </div>
          <div className="map-side side-6">
            <div
              className="front"
              style={
                {
                  "--image":
                    "url('https://meowlivia.s3.us-east-2.amazonaws.com/codepen/map/11.png')",
                } as React.CSSProperties
              }
            ></div>
            <div className="back"></div>
          </div>
        </div>

        <div className="instructions">
          {!isMapOpen && (<button className="toggle-map" onClick={toggleMap}>{isMapOpen ? "Fermer la carte" : "Ouvrir la carte"}</button>)}
          
            
          
        </div>
      </div>
    </div>
  );
};

export default Home;
