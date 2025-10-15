import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../guards/auth.guard';
import { AdminGuard } from '../guards/admin.guard';

import { LoginComponent } from '../component/auth/login/login.component';
import { SignupComponent } from '../component/auth/signup/signup.component';
import { DashboardComponent } from '../component/dashboard/dashboard.component';
import { UserListComponent } from '../component/user/user-list/user-list.component';
import { UserFormComponent } from '../component/user/user-form/user-form.component';
import { CustomerListComponent } from '../component/customer/customer-list/customer-list.component';
import { CustomerFormComponent } from '../component/customer/customer-form/customer-form.component';
import { TrainListComponent } from '../component/train/train-list/train-list.component';
import { TrainFormComponent } from '../component/train/train-form/train-form.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { 
    path: 'dashboard', 
    component: DashboardComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'users', 
    component: UserListComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'users/edit/:id', 
    component: UserFormComponent, 
    canActivate: [AuthGuard, AdminGuard] 
  },
  { 
    path: 'customers', 
    component: CustomerListComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'customers/add', 
    component: CustomerFormComponent, 
    canActivate: [AuthGuard, AdminGuard] 
  },
  { 
    path: 'customers/edit/:id', 
    component: CustomerFormComponent, 
    canActivate: [AuthGuard, AdminGuard] 
  },
  { 
    path: 'trains', 
    component: TrainListComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'trains/add', 
    component: TrainFormComponent, 
    canActivate: [AuthGuard, AdminGuard] 
  },
  { 
    path: 'trains/edit/:id', 
    component: TrainFormComponent, 
    canActivate: [AuthGuard, AdminGuard] 
  },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
