import React, { useState, useEffect } from 'react';
import Layout from '../components/common/Layout';
import SearchBar from '../components/common/SearchBar';
import BusList from '../components/buses/BusList';
import BusMap from '../components/buses/BusMap';
import LoadingSpinner from '../components/common/LoadingSpinner';
import { Bus } from '../types/Bus';
import { Garage } from '../types/Garage';
import { fetchAllBuses } from '../api/busApi';
import { fetchAllGarages } from '../api/garageApi';

const BusesPage: React.FC = () => {
  const [buses, setBuses] = useState<Bus[]>([]);
  const [garages, setGarages] = useState<Garage[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState<string>('');

  useEffect(() => {
    loadData();
  }, [searchTerm]);

  const loadData = async () => {
    try {
      setLoading(true);
      
      // Otobüsleri ve garajları paralel olarak yükle
      const [busesData, garagesData] = await Promise.all([
        fetchAllBuses(searchTerm),
        fetchAllGarages()
      ]);
      
      setBuses(busesData);
      setGarages(garagesData);
      setError(null);
    } catch (err) {
      console.error('Error loading data:', err);
      setError('Veriler yüklenirken bir hata oluştu.');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (term: string) => {
    setSearchTerm(term);
  };

  return (
    <Layout>
      <div className="page-container">
        <h1 className="page-title">Otobüsler</h1>
        
        <SearchBar
          onSearch={handleSearch}
          placeholder="Plaka, Operatör, Garaj veya Kapı No ile arayın..."
        />
        
        {loading ? (
          <LoadingSpinner />
        ) : error ? (
          <div className="error-message">{error}</div>
        ) : (
          <div className="content-container">
            <div className="list-container">
              <BusList buses={buses} garages={garages} />
            </div>
            <div className="map-wrapper">
              <BusMap buses={buses} />
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
};

export default BusesPage;