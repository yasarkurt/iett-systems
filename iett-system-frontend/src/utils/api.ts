import { API_URL } from './config';

/**
 * Temel API istekleri için yardımcı fonksiyonlar
 */

export const fetchData = async <T>(endpoint: string): Promise<T> => {
  const response = await fetch(`${API_URL}${endpoint}`);
  
  if (!response.ok) {
    throw new Error(`API isteği başarısız: ${response.status} ${response.statusText}`);
  }
  
  return response.json() as Promise<T>;
};

export const postData = async <T>(endpoint: string, data: any): Promise<T> => {
  const response = await fetch(`${API_URL}${endpoint}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });
  
  if (!response.ok) {
    throw new Error(`API isteği başarısız: ${response.status} ${response.statusText}`);
  }
  
  return response.json() as Promise<T>;
};

export const updateData = async <T>(endpoint: string, data: any): Promise<T> => {
  const response = await fetch(`${API_URL}${endpoint}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });
  
  if (!response.ok) {
    throw new Error(`API isteği başarısız: ${response.status} ${response.statusText}`);
  }
  
  return response.json() as Promise<T>;
};

export const deleteData = async (endpoint: string): Promise<void> => {
  const response = await fetch(`${API_URL}${endpoint}`, {
    method: 'DELETE',
  });
  
  if (!response.ok) {
    throw new Error(`API isteği başarısız: ${response.status} ${response.statusText}`);
  }
};