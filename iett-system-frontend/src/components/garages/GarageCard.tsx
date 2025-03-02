import React from 'react';
import { Garage } from '../../types/Garage';

interface GarageCardProps {
  garage: Garage;
  onClick: (garage: Garage) => void;
}

const GarageCard: React.FC<GarageCardProps> = ({ garage, onClick }) => {
  return (
    <div className="garage-card" onClick={() => onClick(garage)}>
      <div className="garage-card-header">
        <h3>{garage.garageName}</h3>
        <span className="garage-code">{garage.garageCode}</span>
      </div>
      <div className="garage-card-body">
        <p><strong>Garaj ID:</strong> {garage.garageId}</p>
        <p><strong>Konum:</strong> {garage.latitude.toFixed(6)}, {garage.longitude.toFixed(6)}</p>
      </div>
    </div>
  );
};

export default GarageCard;