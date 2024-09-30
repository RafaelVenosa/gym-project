import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-default-login-layout',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './default-login-layout.component.html',
  styleUrl: './default-login-layout.component.scss'
})
export class DefaultLoginLayoutComponent {
  loginData = {
    email: '',
    password: ''
  };

  constructor(private http: HttpClient, private router: Router) {}

  onLogin() {
    const url = 'http://localhost:8080/gym-authuser/user/login';
    this.http.post<{ token: string, userId: string, userType: string, username: string}>(url, this.loginData)
      .subscribe(
        response => {
          localStorage.setItem('jwtToken', response.token);
          localStorage.setItem('userId', response.userId);
          localStorage.setItem('username', response.username);
          localStorage.setItem('userType', response.userType);
          if(response.userType == 'ADMIN'){
            this.router.navigate(['admin/customer-activation']);
          } if(response.userType == 'INSTRUCTOR'){
            this.router.navigate(['trainer/train-control']);
          } if(response.userType == 'CUSTOMER'){
            this.router.navigate(['customer/train']);
          }
          
        },
        error => {
          console.error('Erro ao fazer login', error);
        }
      );
  }
}
