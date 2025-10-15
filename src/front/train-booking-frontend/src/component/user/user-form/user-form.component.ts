import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../services/user.service';

export interface User {
  id?: number;
  firstName: string;
  lastName: string;
  contactNumber: string;
  email: string;
  address?: string;
  role?: string;

}

@Component({
  selector: 'app-user-form',
   standalone: false, 
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {
  userForm!: FormGroup;
  loading = false;
  isEditMode = false;
  userId?: number;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.userForm = this.fb.group({
      id: [''],
      firstName: ['', [Validators.required, Validators.pattern('^[^0-9]*$')]],
      lastName: ['', [Validators.required, Validators.pattern('^[^0-9]*$')]],
      contactNumber: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      address: [''],
      role: ['user', Validators.required],
   
    });

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.userId = +params['id'];
        this.loadUser(this.userId);
      }
    });
  }

  loadUser(id: number): void {
    this.userService.getUserById(id).subscribe({
      next: (user) => {
        this.userForm.patchValue(user);
      },
      error: (error) => {
        this.errorMessage = 'Failed to load user';
      }
    });
  }

  onSubmit(): void {
    if (this.userForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.userService.updateUser(this.userForm.value).subscribe({
      next: (response: any) => {
        alert(response.message);
        this.router.navigate(['/users']);
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Failed to save user';
        this.loading = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/users']);
  }
}