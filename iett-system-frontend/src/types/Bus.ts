export interface Bus {
    id: number;
    plateNumber: string;
    operator: string;
    garage: string;
    doorNumber: string;
    time: string;
    longitude: number;
    latitude: number;
    speed: number;
    createdAt: string;
    updatedAt: string;
  }
  
  export interface BusRequest {
    plateNumber: string;
    operator: string;
    garage: string;
    doorNumber: string;
    time: string;
    longitude: number;
    latitude: number;
    speed: number;
  }