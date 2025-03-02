import React from 'react';
import { Bus } from '../../types/Bus';

interface BusDetailsProps {
  bus: Bus;
  onClose: () => void;
}

const BusDetails: React.FC<BusDetailsProps> = ({ bus, onClose }) => {
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>×</button>
        
        <div className="modal-header">
          <h2>{bus.plateNumber} Detayları</h2>
        </div>
        
        <div className="modal-body">
          <table>
            <tbody>
              <tr>
                <th>Plaka:</th>
                <td>{bus.plateNumber}</td>
              </tr>
              <tr>
                <th>Operatör:</th>
                <td>{bus.operator}</td>
              </tr>
              <tr>
                <th>Garaj:</th>
                <td>{bus.garage}</td>
              </tr>
              <tr>
                <th>Kapı No:</th>
                <td>{bus.doorNumber}</td>
              </tr>
              <tr>
                <th>Hız:</th>
                <td>{bus.speed.toFixed(1)} km/s</td>
              </tr>
              <tr>
                <th>Konum:</th>
                <td>{bus.latitude.toFixed(6)}, {bus.longitude.toFixed(6)}</td>
              </tr>
              <tr>
                <th>Zaman:</th>
                <td>{new Date(bus.time).toLocaleString('tr-TR')}</td>
              </tr>
              <tr>
                <th>Eklenme Zamanı:</th>
                <td>{new Date(bus.createdAt).toLocaleString('tr-TR')}</td>
              </tr>
              <tr>
                <th>Son Güncelleme:</th>
                <td>{new Date(bus.updatedAt).toLocaleString('tr-TR')}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default BusDetails;