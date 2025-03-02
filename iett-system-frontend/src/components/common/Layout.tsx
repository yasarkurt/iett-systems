import React from 'react';
import Header from './Header';
import Navbar from './Navbar';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <div className="app-container">
      <Header />
      <Navbar />
      <main className="content">{children}</main>
      <footer className="app-footer">
        <p>&copy; {new Date().getFullYear()} IETT Yönetim Sistemi</p>
      </footer>
    </div>
  );
};

export default Layout;