import React, { useState, useEffect } from 'react';
import Layout from '../components/common/Layout';
import SearchBar from '../components/common/SearchBar';
import GarageList from '../components/garages/GarageList';
import GarageMap from '../components/garages/GarageMap';
import LoadingSpinner from '../components/common/LoadingSpinner';
import { Garage } from '../types/Garage';
import { fetchAllGarages } from '../api/garageApi';

const GaragesPage: React.FC = () => {
  const [garages, setGarages] = useState<Garage[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState<string>('');

  useEffect(() => {
    loadGarages();
  }, [searchTerm]);

  const loadGarages = async () => {
    try {
      setLoading(true);
      const data = await fetchAllGarages(searchTerm);
      setGarages(data);
      setError(null);
    } catch (err) {
      console.error('Error loading garages:', err);
      setError('Garajlar yüklenirken bir hata oluştu.');
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
        <h1 className="page-title">Garajlar</h1>
        
        <SearchBar
          onSearch={handleSearch}
          placeholder="Garaj Adı, Kodu veya ID ile arayın..."
        />
        
        {loading ? (
          <LoadingSpinner />
        ) : error ? (
          <div className="error-message">{error}</div>
        ) : (
          <div className="content-container">
            <div className="list-container">
              <GarageList garages={garages} />
            </div>
            <div className="map-wrapper">
              <GarageMap garages={garages} />
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
};

export default GaragesPage;