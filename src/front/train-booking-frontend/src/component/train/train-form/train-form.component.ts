import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TrainService } from '../../../services/train.service';
import { CustomerService } from '../../../services/customer.service';

export interface Customer {
  id?: number;
  name: string;
}

export interface Train {
  id?: number;
  name: string;
  departure_time: string;
  arrival_time: string;
  price_ticket: number;
  day: number;
  month: number;
  nr_seats: number;
  customer_fk: number;
}

@Component({
  selector: 'app-train-form',
   standalone: false, 
  templateUrl: './train-form.component.html',
  styleUrls: ['./train-form.component.css']
})
export class TrainFormComponent implements OnInit {
  trainForm!: FormGroup;
  loading = false;
  isEditMode = false;
  trainId?: number;
  errorMessage = '';
  customers: Customer[] = [];

  constructor(
    private fb: FormBuilder,
    private trainService: TrainService,
    private customerService: CustomerService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
     this.trainForm = this.fb.group({
    id: [''],
    name: ['', Validators.required],
    departure_time: ['', Validators.required],
    arrival_time: ['', Validators.required],
    price_ticket: ['', [Validators.required, Validators.min(0)]],
    day: ['', [Validators.required, Validators.min(1), Validators.max(31)]],
    month: ['', [Validators.required, Validators.min(1), Validators.max(12)]],
    nr_seats: ['', [Validators.required, Validators.min(1)]],
    customer_fk: ['', Validators.required]  
  });

    this.loadCustomers();

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.trainId = +params['id'];
        this.loadTrain(this.trainId);
      }
    });
  }

  loadCustomers(): void {
    this.customerService.getAllCustomers().subscribe({
      next: (customers) => {
        this.customers = customers;
      },
      error: (error) => {
        console.error('Failed to load customers');
      }
    });
  }

  loadTrain(id: number): void {
    this.trainService.getTrainById(id).subscribe({
      next: (train) => {
        this.trainForm.patchValue(train);
      },
      error: (error) => {
        this.errorMessage = 'Failed to load train';
      }
    });
  }

  onSubmit(): void {
    if (this.trainForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const operation = this.isEditMode 
      ? this.trainService.updateTrain(this.trainForm.value)
      : this.trainService.addTrain(this.trainForm.value);

    operation.subscribe({
      next: (response: any) => {
        alert(response.message);
        this.router.navigate(['/trains']);
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Failed to save train';
        this.loading = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/trains']);
  }
}