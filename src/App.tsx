import { useEffect, useState } from "react";
import Home from "./pages/Home";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Navbar } from "./components/Navbar";
import { GetAll } from "./pages/GetAll";
import { Detail } from "./pages/Detail";
import { Login } from "./pages/Login";

import { AddWizard } from "./pages/AddWizard";
import { AddTeacher } from "./pages/AddTeacher";
import { GetHouses } from "./pages/getHouses";
import { HouseDetail } from "./pages/HouseDetail";
import { MonProfil } from "./pages/MonProfil";
import {ChifoumiPage} from "./pages/ChifoumiPage";
import {ChifoumiLoss} from "./pages/ChifoumiLoss";
import {ChifoumiWin} from "./pages/ChifoumiWin";
import {ChifoumiEqual} from "./pages/ChifoumiEqual";
import {ChifoumiResultat} from "./pages/ChifoumiResultat";

function App() {
  const [loggedUser, setLoggedUser] = useState<any>(null);
  const [bestHouse, setBestHouse] = useState<any>(null);

  const getBestHouse = async () => {
    const response = await fetch("http://localhost:8080/maison/nomMaisonGagnante",{
    });
    const data = await response.json();
    setBestHouse(data); 
  }

  useEffect(() => {
    getBestHouse();
  }, []);

  return (
    <Router>
      <Navbar loggedUser={loggedUser} setLoggedUser={setLoggedUser} bestHouse={bestHouse}/>
      <Routes>
        <Route path="/" element={<Home loggedUser={loggedUser}/>} />
        <Route
          path="/eleve/:id"
          element={<Detail path="eleve" loggedUser={loggedUser} getBestHouse={getBestHouse}/>}
        />
        <Route
          path="/professeur/:id"
          element={<Detail path="professeur" loggedUser={loggedUser} getBestHouse={getBestHouse} />}
        />
        <Route path="/eleves" element={<GetAll about={1} />} />
        <Route path="/professeurs" element={<GetAll about={2} />} />
        <Route
          path="/login"
          element={<Login setLoggedUser={setLoggedUser} />}
        />
        
        <Route path="/ajouterEleves" element={<AddWizard />} />
        <Route path="/ajouterProfesseur" element={<AddTeacher />} />
        <Route path="/maisons" element={<GetHouses />} />
        <Route path="/maison/:nom" element={<HouseDetail />} />
        <Route path="/jeu" element={<HouseDetail />} />
        <Route path="/Chifoumi" element={<ChifoumiPage />} />
        <Route path="/ChifoumiLoss" element={<ChifoumiLoss />} />
        <Route path="/ChifoumiWin" element={<ChifoumiWin />} />
        <Route path="/ChifoumiResultat" element={<ChifoumiResultat />} />
        <Route path="/ChifoumiEqual" element={<ChifoumiEqual />} />

        <Route
          path="/MonProfil"
          element={<MonProfil loggedUser={loggedUser} />} 
        />

        
        


      </Routes>
      
    </Router>
  );
}

export default App;
