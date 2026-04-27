/**
 * Direct worker bookings (date + time window). No quotation on this path.
 */
import apiClient from './apiClient';

const unwrap = (response) => response.data?.data ?? response.data;

/**
 * @param {object} body - { workerProfileId, bookingDate, startTime, endTime, note? }
 */
export const createBooking = async (body) => {
  const response = await apiClient.post('/bookings', body);
  return unwrap(response);
};

export const getSeekerBookings = async () => {
  const response = await apiClient.get('/bookings/seeker');
  return unwrap(response);
};

export const getWorkerBookings = async () => {
  const response = await apiClient.get('/bookings/worker');
  return unwrap(response);
};

export const acceptBooking = async (id) => {
  const response = await apiClient.patch(`/bookings/${id}/accept`);
  return unwrap(response);
};

export const rejectBooking = async (id) => {
  const response = await apiClient.patch(`/bookings/${id}/reject`);
  return unwrap(response);
};

export const cancelBooking = async (id) => {
  const response = await apiClient.delete(`/bookings/${id}`);
  return unwrap(response);
};
