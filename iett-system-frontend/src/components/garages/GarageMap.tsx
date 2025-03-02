import React from 'react';
import { Marker, Popup } from 'react-leaflet';
import { Garage } from '../../types/Garage';
import MapContainer from '../map/MapContainer';
import L from 'leaflet';
import markerPNG from '../../img/garage-marker.png'; 

interface GarageMapProps {
  garages: Garage[];
  onGarageSelect?: (garage: Garage) => void;
}

// Garaj marker ikonu
const garageIcon = new L.Icon({
  iconUrl: markerPNG,
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -32],
});

const GarageMap: React.FC<GarageMapProps> = ({ garages, onGarageSelect }) => {
  return (
    <MapContainer>
      {garages.map((garage) => (
        <Marker
          key={garage.id}
          position={[garage.latitude, garage.longitude]}
          icon={garageIcon}
          eventHandlers={{
            click: () => {
              if (onGarageSelect) {
                onGarageSelect(garage);
              }
            },
          }}
        >
          <Popup>
            <div className="map-popup">
              <h3>{garage.garageName}</h3>
              <p><strong>Garaj Kodu:</strong> {garage.garageCode}</p>
              <p><strong>Garaj ID:</strong> {garage.garageId}</p>
            </div>
          </Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};

export default GarageMap;