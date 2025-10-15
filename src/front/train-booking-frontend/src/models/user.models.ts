
export interface User {
  id?: number;
  firstName: string;
  lastName: string;
  contactNumber: string;
  email: string;
  password?: string;
  address?: string;
  status?: string;
  role?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface ChangePasswordRequest {
  oldPassword: string;
  newPassword: string;
}


export interface Customer {
  id?: number;
  name: string;
}


export interface Train {
  id?: number;
  name: string;
  departure_time: string;
  arrival_time: string;
  price_ticket: number;
  day: number;
  month: number;
  nr_seats: number;
  customerId: number;
  customerName?: string;
}


export interface ApiResponse {
  message: string;
}