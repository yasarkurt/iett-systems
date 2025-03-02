import React from 'react';
import { Garage } from '../../types/Garage';

interface GarageDetailsProps {
  garage: Garage;
  onClose: () => void;
}

const GarageDetails: React.FC<GarageDetailsProps> = ({ garage, onClose }) => {
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>×</button>
        
        <div className="modal-header">
          <h2>{garage.garageName} Detayları</h2>
        </div>
        
        <div className="modal-body">
          <table>
            <tbody>
              <tr>
                <th>Garaj Adı:</th>
                <td>{garage.garageName}</td>
              </tr>
              <tr>
                <th>Garaj Kodu:</th>
                <td>{garage.garageCode}</td>
              </tr>
              <tr>
                <th>Garaj ID:</th>
                <td>{garage.garageId}</td>
              </tr>
              <tr>
                <th>Konum:</th>
                <td>{garage.latitude.toFixed(6)}, {garage.longitude.toFixed(6)}</td>
              </tr>
              <tr>
                <th>Eklenme Zamanı:</th>
                <td>{new Date(garage.createdAt).toLocaleString('tr-TR')}</td>
              </tr>
              <tr>
                <th>Son Güncelleme:</th>
                <td>{new Date(garage.updatedAt).toLocaleString('tr-TR')}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default GarageDetails;