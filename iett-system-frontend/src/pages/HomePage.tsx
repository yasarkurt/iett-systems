import React from 'react';
import { Link } from 'react-router-dom';
import Layout from '../components/common/Layout';

const HomePage: React.FC = () => {
  return (
    <Layout>
      <div className="home-container">
    
        
        <section className="home-features">
          <div className="feature-card">
            <div className="feature-icon">🚌</div>
            <h2>Otobüsler</h2>
            <p>Tüm otobüslerin konumlarını, durumlarını ve diğer bilgilerini görüntüleyin.</p>
            <Link to="/buses" className="feature-link">Otobüsleri Görüntüle</Link>
          </div>
          
          <div className="feature-card">
            <div className="feature-icon">🏢</div>
            <h2>Garajlar</h2>
            <p>İstanbul genelindeki tüm IETT garajlarını harita üzerinde inceleyin.</p>
            <Link to="/garages" className="feature-link">Garajları Görüntüle</Link>
          </div>
        </section>
      </div>
    </Layout>
  );
};

export default HomePage;