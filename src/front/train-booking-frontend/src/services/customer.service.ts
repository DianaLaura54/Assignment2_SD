
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

export interface Customer {
  id?: number;
  name: string;
}

export interface ApiResponse {
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private apiUrl = `${environment.apiUrl}/customer`;

  constructor(private http: HttpClient) {}

getAllCustomers(filterValue?: string): Observable<Customer[]> {
  const url = filterValue 
    ? `${this.apiUrl}/get?filterValue=${filterValue}` 
    : `${this.apiUrl}/get`;
  return this.http.get<Customer[]>(url);
}

  getCustomerById(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${this.apiUrl}/getById/${id}`);
  }

  addCustomer(customer: Customer): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/add`, customer);
  }

  updateCustomer(customer: Customer): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.apiUrl}/update`, customer);
  }

  deleteCustomer(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.apiUrl}/delete/${id}`);
  }
}