import React from 'react';
import { Marker, Popup } from 'react-leaflet';
import { Bus } from '../../types/Bus';
import MapContainer from '../map/MapContainer';
import L from 'leaflet';
import markerPNG from '../../img/bus-marker.png'; 

interface BusMapProps {
  buses: Bus[];
  onBusSelect?: (bus: Bus) => void;
}

// Otobüs marker ikonu
const busIcon = new L.Icon({
  iconUrl: markerPNG,
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -32],
});

const BusMap: React.FC<BusMapProps> = ({ buses, onBusSelect }) => {
  return (
    <MapContainer>
      {buses.map((bus) => (
        <Marker
          key={bus.id}
          position={[bus.latitude, bus.longitude]}
          icon={busIcon}
          eventHandlers={{
            click: () => {
              if (onBusSelect) {
                onBusSelect(bus);
              }
            },
          }}
        >
          <Popup>
            <div className="map-popup">
              <h3>{bus.plateNumber}</h3>
              <p><strong>Operator:</strong> {bus.operator}</p>
              <p><strong>Garaj:</strong> {bus.garage}</p>
              <p><strong>Kapı No:</strong> {bus.doorNumber}</p>
              <p><strong>Hız:</strong> {bus.speed.toFixed(1)} km/s</p>
            </div>
          </Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};

export default BusMap;