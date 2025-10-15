import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerService } from '../../../services/customer.service';

export interface Customer {
  id?: number;
  name: string;
}

@Component({
  selector: 'app-customer-list',
   standalone: false, 
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {
  customers: Customer[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private customerService: CustomerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.loading = true;
    this.customerService.getAllCustomers().subscribe({
      next: (customers) => {
        this.customers = customers;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load customers';
        this.loading = false;
      }
    });
  }

  addCustomer(): void {
    this.router.navigate(['/customers/add']);
  }

  editCustomer(id: number): void {
    this.router.navigate(['/customers/edit', id]);
  }

  deleteCustomer(id: number): void {
    if (confirm('Are you sure you want to delete this customer?')) {
      this.customerService.deleteCustomer(id).subscribe({
        next: (response: any) => {
          alert(response.message);
          this.loadCustomers();
        },
        error: (error) => {
          alert('Failed to delete customer');
        }
      });
    }
  }
}