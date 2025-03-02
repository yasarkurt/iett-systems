/**
 * İki koordinat arası mesafeyi hesaplar (km cinsinden)
 * @param lat1 Birinci noktanın enlemi
 * @param lon1 Birinci noktanın boylamı
 * @param lat2 İkinci noktanın enlemi
 * @param lon2 İkinci noktanın boylamı
 * @returns Mesafe (km)
 */
export const calculateDistance = (
    lat1: number,
    lon1: number,
    lat2: number,
    lon2: number
  ): number => {
    const R = 6371; // Dünya yarıçapı (km)
    const dLat = toRad(lat2 - lat1);
    const dLon = toRad(lon2 - lon1);
    
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(toRad(lat1)) *
        Math.cos(toRad(lat2)) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  };
  
  /**
   * Dereceyi radyana çevirir
   * @param deg Derece
   * @returns Radyan
   */
  const toRad = (deg: number): number => {
    return (deg * Math.PI) / 180;
  };
  
  /**
   * Tarihi yerel formata çevirir
   * @param dateString ISO tarih string
   * @returns Formatlı tarih string
   */
  export const formatDate = (dateString: string): string => {
    return new Date(dateString).toLocaleString('tr-TR');
  };
  
  /**
   * Metni kısaltır
   * @param text Kısaltılacak metin
   * @param maxLength Maksimum uzunluk
   * @returns Kısaltılmış metin
   */
  export const truncateText = (text: string, maxLength: number): string => {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
  };