
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from '../../../services/customer.service';


@Component({
  selector: 'app-customer-form',
   standalone: false, 
  templateUrl: './customer-form.component.html',
  styleUrls: ['./customer-form.component.css']
})
export class CustomerFormComponent implements OnInit {
  customerForm!: FormGroup;
  loading = false;
  isEditMode = false;
  customerId?: number;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.customerForm = this.fb.group({
      id: [''],
      name: ['', Validators.required]
    });

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.customerId = +params['id'];
        this.loadCustomer(this.customerId);
      }
    });
  }

  loadCustomer(id: number): void {
    this.customerService.getCustomerById(id).subscribe({
      next: (customer) => {
        this.customerForm.patchValue(customer);
      },
      error: (error) => {
        this.errorMessage = 'Failed to load customer';
      }
    });
  }

  onSubmit(): void {
    if (this.customerForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const operation = this.isEditMode 
      ? this.customerService.updateCustomer(this.customerForm.value)
      : this.customerService.addCustomer(this.customerForm.value);

    operation.subscribe({
      next: (response) => {
        alert(response.message);
        this.router.navigate(['/customers']);
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Failed to save customer';
        this.loading = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/customers']);
  }
}