import React from "react";
import logoPath from "../images/LogoHeartCare.png";
import "../css/style.css";
import "../css/style_menu.css";
import { Link, useNavigate } from "react-router-dom";
import { FaHospital } from "react-icons/fa";
import { CiLogout } from "react-icons/ci";
import { SlHome } from "react-icons/sl";
import { BsCalendarEvent } from "react-icons/bs";
import { AiOutlineQuestion, AiOutlineUser } from "react-icons/ai";
import {MdOutlineRule} from "react-icons/md";



function addClassActive(id, nomeClasseMainContent) {
    let attualmenteSelezionato = document.querySelectorAll(".voceMenuActive");
    [].forEach.call(attualmenteSelezionato, function (el) {
      el.classList.remove("voceMenuActive");
    });
    document.getElementById(id).classList.add("voceMenuActive");
    // nel caso in cui clicco su una voce del menu da telefono poi nascondo il menu
  }

function MenuPaziente() {
const nav = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    nav("/");
  }
  
  return (
    <div className="contenitoreMenu">
      <img src={logoPath} className="logoMenu" />

      <div className="contenitoreVociMenu" id="menu-paziente">
        <Link
          to={"/HomePaziente"}
          className="voceMenuText"
          onClick={() => addClassActive(1, "contenitoreMainContent")}
        >
          <div className="voceMenu voceMenuActive" id="1">
            <SlHome className="iconaMenu" />
            <span>Dashboard</span>
          </div>
        </Link>

        <Link
          to={"/Fascicolo"}
          className="voceMenuText"
          onClick={() => addClassActive(2, "contenitoreScheduleContent")}
        >
          <div className="voceMenu" id="2">
            <BsCalendarEvent className="iconaMenu" />
            <span>Fascicolo</span>
          </div>
        </Link>

        <Link
          to={"/Dispositivi"}
          className="voceMenuText"
          onClick={() => addClassActive(3, "contenitorePazientiContent")}
        >
          <div className="voceMenu" id="3">
            <FaHospital className="iconaMenu" />
            <span>Dispositivi</span>
          </div>
        </Link>

        <Link
          to={"/Profilo"}
          className="voceMenuText"
          onClick={() => addClassActive(4, "contenitoreMainContent")}
        >
          <div className="voceMenu" id="4">
            <AiOutlineUser className="iconaMenu" />
            <span>Profilo</span>
          </div>
        </Link>

        <hr className="lineaMenu" />

        <Link
          to={"/Norme"}
          className="voceMenuText"
          onClick={() => addClassActive(5, "contenitoreMainContent")}
        >
          <div className="voceMenu" id="5">
            <MdOutlineRule className="iconaMenu" />
            <span>Buone norme</span>
          </div>
        </Link>

        <div className="voceMenuText" onClick={() => logout()}>
          <div className="voceMenu" >
            <CiLogout className="iconaMenu" />
            <span>Logout</span>
          </div>
        </div>
      </div>

</div>
  )
}

export default MenuPaziente