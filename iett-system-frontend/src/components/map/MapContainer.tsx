import React from 'react';
import { MapContainer as LeafletMap, TileLayer } from 'react-leaflet';
import { DEFAULT_ZOOM, MAP_CENTER } from '../../utils/config';

interface MapContainerProps {
  children: React.ReactNode;
  center?: { lat: number; lng: number };
  zoom?: number;
}

const MapContainer: React.FC<MapContainerProps> = ({
  children,
  center = MAP_CENTER,
  zoom = DEFAULT_ZOOM,
}) => {
  return (
    <div className="map-container">
      <LeafletMap center={[center.lat, center.lng]} zoom={zoom} scrollWheelZoom={false}>
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {children}
      </LeafletMap>
    </div>
  );
};

export default MapContainer;