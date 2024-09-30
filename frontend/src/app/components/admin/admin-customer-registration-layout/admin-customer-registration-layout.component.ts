import { Component } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import {
  FormControl,
  Validators,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { CommonModule } from '@angular/common';
import { AdminNavbarComponent } from '../admin-navbar/admin-navbar.component';
import { DataService } from '../../../services/data.service';

@Component({
  selector: 'app-admin-customer-registration-layout',
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
  templateUrl: './admin-customer-registration-layout.component.html',
  styleUrl: './admin-customer-registration-layout.component.scss',
  providers: [DataService],
})
export class AdminCustomerRegistrationLayoutComponent {
  // Inicialização do formulário de email com validações
  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  // Propriedades para armazenar a resposta e o erro do backend
  resposta: any;
  erro: any;

  // Dados a serem enviados na requisição
  data = {
    username: '',
    email: '',
    phoneNumber: '',
    cpf: '',
    userImage: '',
  };

  constructor(private dataService: DataService) {}

  // Método para enviar os dados ao backend
  enviarDados() {
    if (confirm('Tem certeza de que deseja enviar os dados?')) {
      this.dataService.registerCustomer(this.data).subscribe(
        (response: any) => {
          // Tipagem explícita para `response`
          this.resposta = response; // Armazena a resposta do backend
          console.log(response); // Exibe a resposta no console
          alert("Usuário cadastrado com sucesso!");
          this.clearInputs();
        },
        (error: any) => {
          // Tipagem explícita para `error`
          this.erro = error; // Armazena o erro, se ocorrer
          console.error(error); // Exibe o erro no console
          alert("Erro ao cadastrar usuário");
          this.clearInputs();
        }
      );
    } else {
      console.log('Envio de dados cancelado pelo usuário.');
      alert("Registro cancelado");
    }
  }

  clearInputs() {
    this.data = {
      username: '',
      email: '',
      phoneNumber: '',
      cpf: '',
      userImage: '',
    };
  }
}
