import React from 'react';

interface MapPopupProps {
  title: string;
  details: { label: string; value: string }[];
}

const MapPopup: React.FC<MapPopupProps> = ({ title, details }) => {
  return (
    <div className="map-popup">
      <h3>{title}</h3>
      {details.map((detail, index) => (
        <p key={index}>
          <strong>{detail.label}:</strong> {detail.value}
        </p>
      ))}
    </div>
  );
};

export default MapPopup;