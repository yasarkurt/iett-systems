import React, { useState } from 'react';
import { Garage } from '../../types/Garage';
import GarageCard from './GarageCard';
import GarageDetails from './GarageDetails';
import NotFoundMessage from '../common/NotFoundMessage';

interface GarageListProps {
  garages: Garage[];
}

const GarageList: React.FC<GarageListProps> = ({ garages }) => {
  const [selectedGarage, setSelectedGarage] = useState<Garage | null>(null);

  const handleGarageClick = (garage: Garage) => {
    setSelectedGarage(garage);
  };

  const handleClose = () => {
    setSelectedGarage(null);
  };

  if (garages.length === 0) {
    return <NotFoundMessage message="Belirtilen kriterlere uygun garaj bulunamadı." />;
  }

  return (
    <div className="garage-list">
      {garages.map((garage) => (
        <GarageCard key={garage.id} garage={garage} onClick={handleGarageClick} />
      ))}
      
      {selectedGarage && (
        <GarageDetails garage={selectedGarage} onClose={handleClose} />
      )}
    </div>
  );
};

export default GarageList;