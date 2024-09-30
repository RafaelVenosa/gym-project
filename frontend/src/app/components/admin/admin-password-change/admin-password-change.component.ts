import { Component } from '@angular/core';
import { AdminNavbarComponent } from "../admin-navbar/admin-navbar.component";
import { DataService } from '../../../services/data.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-password-change',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    AdminNavbarComponent],
  templateUrl: './admin-password-change.component.html',
  styleUrl: './admin-password-change.component.scss'
})
export class AdminPasswordChangeComponent {
  oldPassword: string = '';
  newPassword: string = '';
  confirmPassword: string = '';
  passwordMismatch: boolean = false; // Flag para exibir a mensagem de erro
  passwordChangedSuccessfully: boolean = false;  // Nova variável para sucesso

  constructor(private dataService: DataService) {}

  onSubmit() {
    // Verifica se as novas senhas são iguais
    if (this.newPassword !== this.confirmPassword) {
      this.passwordMismatch = true; // Exibe o erro
      return;
    }

    // Se as senhas coincidirem, chama o serviço para atualizar a senha
    const data = {
      oldPassword: this.oldPassword,
      password: this.newPassword
    };

    this.dataService.updatePassword(data).subscribe(
      (response) => {
        console.log('Senha alterada com sucesso');
        this.passwordMismatch = false; // Reseta o flag de erro
        this.passwordChangedSuccessfully = true; // Definir como true se a requisição for bem-sucedida
        this.clearInputs();
      },
      (error) => {
        console.error('Erro ao alterar a senha', error);
        this.clearInputs();
      }
    );
  }

  clearInputs() {
    this.oldPassword = '';
    this.newPassword = '';
    this.confirmPassword = '';
  }
}
