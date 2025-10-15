import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TrainService } from '../../../services/train.service';
import { AuthService } from '../../../services/auth.service';
import { BookingService } from '../../../services/booking.service';

export interface Train {
  id?: number;
  name: string;
  departure_time: string;
  arrival_time: string;
  price_ticket: number;
  day: number;
  month: number;
  nr_seats: number;
  customerId: number;
  customerName?: string;
}

@Component({
  selector: 'app-train-list',
   standalone: false, 
  templateUrl: './train-list.component.html',
  styleUrls: ['./train-list.component.css']
})
export class TrainListComponent implements OnInit {
  trains: Train[] = [];
  loading = false;
  errorMessage = '';
  isAdmin = false;

  constructor(
    private trainService: TrainService,
    private router: Router,
    private authService: AuthService,
    private bookingService: BookingService  
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadTrains();
  }

  loadTrains(): void {
    this.loading = true;
    this.trainService.getAllTrains().subscribe({
      next: (trains) => {
        this.trains = trains;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load trains';
        this.loading = false;
      }
    });
  }

  addTrain(): void {
    this.router.navigate(['/trains/add']);
  }

  editTrain(id: number): void {
    this.router.navigate(['/trains/edit', id]);
  }

  deleteTrain(id: number): void {
    if (confirm('Are you sure you want to delete this train?')) {
      this.trainService.deleteTrain(id).subscribe({
        next: (response: any) => {
          alert(response.message);
          this.loadTrains();
        },
        error: (error) => {
          alert('Failed to delete train');
        }
      });
    }
  }


  bookTrain(train: Train): void {
    if (train.nr_seats === 0) {
      alert('No seats available for this train');
      return;
    }

    const seatsToBook = prompt(
      `How many seats would you like to book? (Available: ${train.nr_seats})`,
      '1'
    );

    if (seatsToBook === null) return; 

    const seats = parseInt(seatsToBook);
    if (isNaN(seats) || seats < 1) {
      alert('Please enter a valid number of seats');
      return;
    }

    if (seats > train.nr_seats) {
      alert(`Only ${train.nr_seats} seats available`);
      return;
    }

    this.bookingService.bookTrain(train.id!, seats).subscribe({
      next: (response: any) => {
        alert(response.message || 'Booking successful!');
        this.loadTrains(); 
      },
      error: (error) => {
        alert(error.error?.message || 'Booking failed. Please try again.');
      }
    });
  }

  getMonthName(month: number): string {
    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
                    'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    return months[month - 1] || '';
  }
}
