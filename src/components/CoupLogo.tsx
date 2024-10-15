import "./HouseLogo.css";

export const HouseLogo = ({house}:{house:string}) => {
    const path = house == "Gryffondor" ? "/balais.png" : house == "Serdaigle" ? "/chaudron.png" : house == "Poufsouffle" ? "/baguette.png" : house == "" ? "/all.png": "/serpentard.png";
    return (
        
        <img className="logo" src={`${path}`} alt="house" />
        
    );
}