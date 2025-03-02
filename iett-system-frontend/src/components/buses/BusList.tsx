import React, { useState } from 'react';
import { Bus } from '../../types/Bus';
import { Garage } from '../../types/Garage';
import BusCard from './BusCard';
import BusDetails from './BusDetails';
import NotFoundMessage from '../common/NotFoundMessage';

interface BusListProps {
  buses: Bus[];
  garages?: Garage[];
}

// İki nokta arasındaki mesafeyi hesapla (Haversine formülü)
const calculateDistance = (lat1: number, lon1: number, lat2: number, lon2: number): number => {
  const R = 6371; // Dünya yarıçapı (km)
  const dLat = ((lat2 - lat1) * Math.PI) / 180;
  const dLon = ((lon2 - lon1) * Math.PI) / 180;
  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos((lat1 * Math.PI) / 180) *
      Math.cos((lat2 * Math.PI) / 180) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return R * c;
};

// Bir otobüse en yakın garajı bul
const findNearestGarage = (bus: Bus, garages: Garage[]): string => {
  if (garages.length === 0) return '';

  let nearestGarage = garages[0];
  let minDistance = calculateDistance(
    bus.latitude,
    bus.longitude,
    garages[0].latitude,
    garages[0].longitude
  );

  for (let i = 1; i < garages.length; i++) {
    const distance = calculateDistance(
      bus.latitude,
      bus.longitude,
      garages[i].latitude,
      garages[i].longitude
    );
    if (distance < minDistance) {
      minDistance = distance;
      nearestGarage = garages[i];
    }
  }

  return nearestGarage.garageName;
};

const BusList: React.FC<BusListProps> = ({ buses, garages = [] }) => {
  const [selectedBus, setSelectedBus] = useState<Bus | null>(null);

  const handleBusClick = (bus: Bus) => {
    setSelectedBus(bus);
  };

  const handleClose = () => {
    setSelectedBus(null);
  };

  if (buses.length === 0) {
    return <NotFoundMessage message="Belirtilen kriterlere uygun otobüs bulunamadı." />;
  }

  return (
    <div className="bus-list">
      {buses.map((bus) => (
        <BusCard
          key={bus.id}
          bus={bus}
          nearestGarage={garages.length > 0 ? findNearestGarage(bus, garages) : undefined}
          onClick={handleBusClick}
        />
      ))}
      
      {selectedBus && (
        <BusDetails bus={selectedBus} onClose={handleClose} />
      )}
    </div>
  );
};

export default BusList;