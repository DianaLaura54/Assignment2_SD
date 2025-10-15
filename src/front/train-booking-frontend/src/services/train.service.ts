import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

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

@Injectable({
  providedIn: 'root'
})
export class TrainService {
  private apiUrl = `${environment.apiUrl}/train`;

  constructor(private http: HttpClient) {}

getAllTrains(filterValue?: string): Observable<Train[]> {
  const url = filterValue 
    ? `${this.apiUrl}/get?filterValue=${filterValue}` 
    : `${this.apiUrl}/get`;
  return this.http.get<Train[]>(url);
}

  getTrainById(id: number): Observable<Train> {
    return this.http.get<Train>(`${this.apiUrl}/getById/${id}`);
  }

  getTrainsByCustomer(customerId: number): Observable<Train[]> {
    return this.http.get<Train[]>(`${this.apiUrl}/getByCustomer/${customerId}`);
  }

  addTrain(train: Train): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/add`, train);
  }

  updateTrain(train: Train): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.apiUrl}/update`, train);
  }

  deleteTrain(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.apiUrl}/delete/${id}`);
  }
}