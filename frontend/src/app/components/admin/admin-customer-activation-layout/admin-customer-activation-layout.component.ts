import { Component } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormControl, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import { CommonModule } from '@angular/common';
import { AdminNavbarComponent } from '../admin-navbar/admin-navbar.component';
import { DataService } from '../../../services/data.service';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-admin-customer-activation-layout',
  standalone: true,
  imports: [
    FormsModule, 
    MatFormFieldModule, 
    MatInputModule, 
    ReactiveFormsModule, 
    MatButtonModule, 
    MatDividerModule, 
    MatIconModule,
    CommonModule,
    AdminNavbarComponent
  ],
  templateUrl: './admin-customer-activation-layout.component.html',
  styleUrl: './admin-customer-activation-layout.component.scss'
})

export class AdminCustomerActivationLayoutComponent {
  searchParams = { name: '', cpf: '' };
  users: any[] = [];

  constructor(private dataService: DataService) {}

  onSearch() {
    let params = new HttpParams();

    if (this.searchParams.name) {
     params = params.set('username', this.searchParams.name);
    }
    if (this.searchParams.cpf) {
      params = params.set('cpf', this.searchParams.cpf);
    }

    this.dataService.getAuthUser(params).subscribe((response: any) => {
      this.users = response.content;
    });
  }

  activateUserYear(userId: string) {
    this.dataService.activateUserYear(userId).subscribe((response: any) => {
      alert("Usuário foi ativado por um ano!");
      location.reload();
    })
  }

  activateUserMounth(userId: string) {
    this.dataService.activateUserMounth(userId).subscribe((response: any) => {
      alert("Usuário foi ativado por um mês!");
      location.reload();
    })
  }

  deactivateUser(userId: string) {
    this.dataService.deactivateUser(userId).subscribe((response: any) => {
      alert("Usuário foi desativado!");
      location.reload();
    })
  }

  deleteUser(userId: string) {
    this.dataService.deleteCustomer(userId).subscribe((response: any) => {
      alert("Usuário foi deletado!");
      location.reload();
    })
  }


}
