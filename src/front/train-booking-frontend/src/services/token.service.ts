
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'auth_token';

  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) {
      return true;
    }

    try {
      const decoded: any = this.decodeToken(token);
      const expirationDate = decoded.exp * 1000;
      return Date.now() >= expirationDate;
    } catch (error) {
      return true;
    }
  }

  getUserRole(): string | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }

    try {
      const decoded: any = this.decodeToken(token);
      return decoded.role || null;
    } catch (error) {
      return null;
    }
  }

  getUserEmail(): string | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }

    try {
      const decoded: any = this.decodeToken(token);
      return decoded.sub || null;
    } catch (error) {
      return null;
    }
  }

  isAdmin(): boolean {
    return this.getUserRole()?.toLowerCase() === 'admin';
  }

  isUser(): boolean {
    return this.getUserRole()?.toLowerCase() === 'user';
  }

  private decodeToken(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (error) {
      return null;
    }
  }
}