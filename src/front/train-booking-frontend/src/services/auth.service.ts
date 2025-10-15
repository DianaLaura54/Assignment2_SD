
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, BehaviorSubject } from 'rxjs';
import { environment } from '../environments/environment';

import { LoginRequest, LoginResponse, User } from '../models/user.models';
import { TokenService } from './token.service';
import { Router } from '@angular/router';

export interface ApiResponse {
  message: string;
}

export interface UserState {
  email: string;
  role: string;
  isAuthenticated: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/user`;
  
  
  private userStateSubject = new BehaviorSubject<UserState>({
    email: '',
    role: '',
    isAuthenticated: false
  });
  
  public userState$ = this.userStateSubject.asObservable();

  constructor(
    private http: HttpClient,
    private tokenService: TokenService,
    private router: Router
  ) {
    this.updateUserState();
  }

  signup(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/signup`, user);
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (response.token) {
          this.tokenService.setToken(response.token);
          this.updateUserState();
        }
      })
    );
  }

  logout(): void {
    this.tokenService.removeToken();
    this.userStateSubject.next({
      email: '',
      role: '',
      isAuthenticated: false
    });
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!this.tokenService.getToken() && !this.tokenService.isTokenExpired();
  }

  isAdmin(): boolean {
    return this.isAuthenticated() && this.tokenService.isAdmin();
  }

  isUser(): boolean {
    return this.isAuthenticated() && this.tokenService.isUser();
  }


  private updateUserState(): void {
    const email = this.tokenService.getUserEmail() || '';
    const role = this.tokenService.getUserRole() || '';
    const isAuthenticated = this.isAuthenticated();

    this.userStateSubject.next({
      email,
      role,
      isAuthenticated
    });
  }


  getCurrentUserState(): UserState {
    return this.userStateSubject.value;
  }
}