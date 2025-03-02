import React from 'react';
import { Bus } from '../../types/Bus';

interface BusCardProps {
  bus: Bus;
  nearestGarage?: string;
  onClick: (bus: Bus) => void;
}

const BusCard: React.FC<BusCardProps> = ({ bus, nearestGarage, onClick }) => {
  // Zamanı formatla
  const formattedTime = new Date(bus.time).toLocaleString('tr-TR');

  return (
    <div className="bus-card" onClick={() => onClick(bus)}>
      <div className="bus-card-header">
        <h3>{bus.plateNumber}</h3>
        <span className="bus-operator">{bus.operator}</span>
      </div>
      <div className="bus-card-body">
        <p><strong>Garaj:</strong> {bus.garage}</p>
        <p><strong>Kapı No:</strong> {bus.doorNumber}</p>
        <p><strong>Hız:</strong> {bus.speed.toFixed(1)} km/s</p>
        <p><strong>Zaman:</strong> {formattedTime}</p>
        {nearestGarage && (
          <p className="nearest-garage">
            <strong>En Yakın Garaj:</strong> {nearestGarage}
          </p>
        )}
      </div>
    </div>
  );
};

export default BusCard;