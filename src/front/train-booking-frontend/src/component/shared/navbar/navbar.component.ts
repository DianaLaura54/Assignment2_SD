import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { TokenService } from '../../../services/token.service';
import { Subscription, filter } from 'rxjs';

@Component({
  selector: 'app-navbar',
  standalone: false, 
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  userEmail: string = '';
  isAdmin: boolean = false;
  private routerSubscription?: Subscription;

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.updateUserInfo();
    this.routerSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.updateUserInfo();
      });
  }

  ngOnDestroy(): void {
 
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

  private updateUserInfo(): void {
    this.userEmail = this.tokenService.getUserEmail() || 'User';
    this.isAdmin = this.tokenService.isAdmin();
  }

  logout(): void {
    if (confirm('Are you sure you want to logout?')) {
      this.authService.logout();
      this.userEmail = '';
      this.isAdmin = false;
    }
  }
}