import "./HouseLogo.css";

export const HouseLogo = ({house}:{house:string}) => {
    const path = house == "Gryffondor" ? "/gryffondor.png" : house == "Serdaigle" ? "/serdaigle.png" : house == "Poufsouffle" ? "/poufsouffle.png" : house == "" ? "/all.png": "/serpentard.png"; 
    return (
        
        <img className="logo" src={`${path}`} alt="house" />
        
    );
}