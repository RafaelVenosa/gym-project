import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-navbar',
  standalone: true,
  imports: [],
  templateUrl: './customer-navbar.component.html',
  styleUrl: './customer-navbar.component.scss'
})
export class CustomerNavbarComponent {
  constructor(private router: Router) {}

  logout() {
    localStorage.clear();
    this.router.navigate(['login'])
  }
}
