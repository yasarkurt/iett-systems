import React from 'react';
import { NavLink } from 'react-router-dom';
import './Navbar.css'; // CSS dosyasını import et

const Navbar: React.FC = () => {
  return (
    <nav className="main-nav">
      <ul>
        <li>
          <NavLink to="/" end className="nav-link">
            Ana Sayfa
          </NavLink>
        </li>
        <li>
          <NavLink to="/garages" className="nav-link">Garajlar</NavLink>
        </li>
        <li>
          <NavLink to="/buses" className="nav-link">Otobüsler</NavLink>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;