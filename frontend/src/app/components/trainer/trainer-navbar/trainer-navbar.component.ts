import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-trainer-navbar',
  standalone: true,
  imports: [],
  templateUrl: './trainer-navbar.component.html',
  styleUrl: './trainer-navbar.component.scss'
})
export class TrainerNavbarComponent {

  constructor(private router: Router) {}

  logout() {
    localStorage.clear();
    this.router.navigate(['login'])
  }
}
