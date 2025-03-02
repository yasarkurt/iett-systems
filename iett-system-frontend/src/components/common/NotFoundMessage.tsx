import React from 'react';

interface NotFoundMessageProps {
  message: string;
}

const NotFoundMessage: React.FC<NotFoundMessageProps> = ({ message }) => {
  return (
    <div className="not-found-message">
      <div className="icon">🔍</div>
      <h2>Sonuç Bulunamadı</h2>
      <p>{message}</p>
    </div>
  );
};

export default NotFoundMessage;