export interface Garage {
    id: number;
    garageId: string;
    garageName: string;
    garageCode: string;
    latitude: number;
    longitude: number;
    createdAt: string;
    updatedAt: string;
  }
  
  export interface GarageRequest {
    garageId: string;
    garageName: string;
    garageCode: string;
    latitude: number;
    longitude: number;
  }