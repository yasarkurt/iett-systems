import { Garage, GarageRequest } from '../types/Garage';
import { API_URL } from '../utils/config';

export const fetchAllGarages = async (search?: string, limit: number = 20): Promise<Garage[]> => {
  let url = `${API_URL}/api/garages?limit=${limit}`;
  if (search) {
    url += `&search=${encodeURIComponent(search)}`;
  }
  
  const response = await fetch(url);
  if (!response.ok) {
    throw new Error('Failed to fetch garages');
  }
  
  return response.json();
};

export const fetchGarageById = async (id: number): Promise<Garage> => {
  const response = await fetch(`${API_URL}/api/garages/${id}`);
  if (!response.ok) {
    throw new Error('Failed to fetch garage details');
  }
  
  return response.json();
};

export const createGarage = async (garage: GarageRequest): Promise<Garage> => {
  const response = await fetch(`${API_URL}/api/garages`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(garage),
  });
  
  if (!response.ok) {
    throw new Error('Failed to create garage');
  }
  
  return response.json();
};

export const updateGarage = async (id: number, garage: GarageRequest): Promise<Garage> => {
  const response = await fetch(`${API_URL}/api/garages/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(garage),
  });
  
  if (!response.ok) {
    throw new Error('Failed to update garage');
  }
  
  return response.json();
};

export const deleteGarage = async (id: number): Promise<void> => {
  const response = await fetch(`${API_URL}/api/garages/${id}`, {
    method: 'DELETE',
  });
  
  if (!response.ok) {
    throw new Error('Failed to delete garage');
  }
};