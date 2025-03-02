import { Bus, BusRequest } from '../types/Bus';
import { API_URL } from '../utils/config';

export const fetchAllBuses = async (search?: string, limit: number = 20): Promise<Bus[]> => {
  let url = `${API_URL}/api/buses?limit=${limit}`;
  if (search) {
    url += `&search=${encodeURIComponent(search)}`;
  }
  
  const response = await fetch(url);
  if (!response.ok) {
    throw new Error('Failed to fetch buses');
  }
  
  return response.json();
};

export const fetchBusById = async (id: number): Promise<Bus> => {
  const response = await fetch(`${API_URL}/api/buses/${id}`);
  if (!response.ok) {
    throw new Error('Failed to fetch bus details');
  }
  
  return response.json();
};

export const createBus = async (bus: BusRequest): Promise<Bus> => {
  const response = await fetch(`${API_URL}/api/buses`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(bus),
  });
  
  if (!response.ok) {
    throw new Error('Failed to create bus');
  }
  
  return response.json();
};

export const updateBus = async (id: number, bus: BusRequest): Promise<Bus> => {
  const response = await fetch(`${API_URL}/api/buses/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(bus),
  });
  
  if (!response.ok) {
    throw new Error('Failed to update bus');
  }
  
  return response.json();
};

export const deleteBus = async (id: number): Promise<void> => {
  const response = await fetch(`${API_URL}/api/buses/${id}`, {
    method: 'DELETE',
  });
  
  if (!response.ok) {
    throw new Error('Failed to delete bus');
  }
};