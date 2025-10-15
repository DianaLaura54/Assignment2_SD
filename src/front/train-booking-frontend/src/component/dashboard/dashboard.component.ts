import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  userEmail: string = '';
  userRole: string = '';
  isAdmin: boolean = false;

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userEmail = this.tokenService.getUserEmail() || 'User';
    this.userRole = this.tokenService.getUserRole() || 'user';
    this.isAdmin = this.tokenService.isAdmin();
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }


  logout(): void {
    if (confirm('Are you sure you want to logout?')) {
      this.authService.logout();
    }
  }
}