import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user.service';

export interface User {
  id?: number;
  firstName: string;
  lastName: string;
  contactNumber: string;
  email: string;
  status?: string;
  role?: string;
  address?: string;
}

@Component({
  selector: 'app-user-list',
   standalone: false, 
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load users';
        this.loading = false;
      }
    });
  }

  editUser(id: number): void {
    this.router.navigate(['/users/edit', id]);
  }

  deleteUser(id: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe({
        next: (response: any) => {
          alert(response.message);
          this.loadUsers();
        },
        error: (error) => {
          alert('Failed to delete user');
        }
      });
    }
  }
}