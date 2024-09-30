import { Component } from '@angular/core';
import { AdminNavbarComponent } from '../admin-navbar/admin-navbar.component';

@Component({
  selector: 'app-admin-deletion-layout',
  standalone: true,
  imports: [
    AdminNavbarComponent
  ],
  templateUrl: './admin-deletion-layout.component.html',
  styleUrl: './admin-deletion-layout.component.scss'
})
export class AdminDeletionLayoutComponent {

}
