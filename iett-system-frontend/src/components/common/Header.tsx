import React from 'react';
import { Link } from 'react-router-dom';
import iettLogo from '../../img/iett.png';
import './Header.css'; // CSS dosyasını import et

const Header: React.FC = () => {
  return (
    <header className="app-header">
      <div className="logo-container">
        <Link to="/" className="logo-link">
          <img src={iettLogo} alt="IETT Logo" className="logo-image" />
          <h1 className="logo-title"> Yönetim Sistemi</h1>
        </Link>
      </div>
    </header>
  );
};

export default Header;