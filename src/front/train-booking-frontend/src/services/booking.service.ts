import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

export interface Booking {
  id: number;
  user: any;
  train: any;
  seatsBooked: number;
  bookingDate: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = `${environment.apiUrl}/booking`;

  constructor(private http: HttpClient) {}

  bookTrain(trainId: number, seatsToBook: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/book`, {
      trainId: trainId.toString(),
      seatsToBook: seatsToBook.toString()
    });
  }

  getMyBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.apiUrl}/my-bookings`);
  }

  getAllBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.apiUrl}/all`);
  }

  getBookingById(id: number): Observable<Booking> {
    return this.http.get<Booking>(`${this.apiUrl}/${id}`);
  }

  cancelBooking(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/cancel/${id}`, {});
  }

  getBookingsByTrainId(trainId: number): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.apiUrl}/train/${trainId}`);
  }
}