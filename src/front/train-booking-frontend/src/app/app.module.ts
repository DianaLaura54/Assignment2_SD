
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';


import { AuthInterceptor } from '../interceptors/auth.interceptor';
import { ErrorInterceptor } from '../interceptors/error.interceptor';


import { LoginComponent } from '../component/auth/login/login.component';
import { SignupComponent } from '../component/auth/signup/signup.component';
import { DashboardComponent } from '../component/dashboard/dashboard.component';

import { UserListComponent } from '../component/user/user-list/user-list.component';
import { UserFormComponent } from '../component/user/user-form/user-form.component';
import { CustomerListComponent } from '../component/customer/customer-list/customer-list.component';
import { CustomerFormComponent } from '../component/customer/customer-form/customer-form.component';
import { TrainListComponent } from '../component/train/train-list/train-list.component';
import { TrainFormComponent } from '../component/train/train-form/train-form.component';

import { NavbarComponent } from '../component/shared/navbar/navbar.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    UserListComponent,
    UserFormComponent,
    CustomerListComponent,
    CustomerFormComponent,
    TrainListComponent,
    TrainFormComponent,
    DashboardComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }