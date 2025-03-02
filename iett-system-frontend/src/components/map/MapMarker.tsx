import React from 'react';
import { Marker, Popup } from 'react-leaflet';
import L from 'leaflet';

interface MapMarkerProps {
  position: [number, number];
  title: string;
  popupContent: React.ReactNode;
  iconUrl: string;
  onClick?: () => void;
}

const MapMarker: React.FC<MapMarkerProps> = ({
  position,
  title,
  popupContent,
  iconUrl,
  onClick,
}) => {
  const icon = new L.Icon({
    iconUrl,
    iconSize: [32, 32],
    iconAnchor: [16, 32],
    popupAnchor: [0, -32],
  });

  return (
    <Marker
      position={position}
      icon={icon}
      title={title}
      eventHandlers={{
        click: () => {
          if (onClick) onClick();
        },
      }}
    >
      <Popup>{popupContent}</Popup>
    </Marker>
  );
};

export default MapMarker;