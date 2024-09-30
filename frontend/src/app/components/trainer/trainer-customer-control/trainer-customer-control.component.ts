import { Component } from '@angular/core';
import { TrainerNavbarComponent } from '../trainer-navbar/trainer-navbar.component';
import { DataService } from '../../../services/data.service';
import { HttpParams } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-trainer-customer-control',
  standalone: true,
  imports: [
    FormsModule,
    TrainerNavbarComponent,
    CommonModule],
  templateUrl: './trainer-customer-control.component.html',
  styleUrl: './trainer-customer-control.component.scss'
})
export class TrainerCustomerControlComponent {

  searchParams = { name: '', cpf: ''};
  users: any[] = [];
  trains: any[] = [];
  currentUserId: string = '';

  constructor(private dataService: DataService) {}

  // Função de busca de usuários
  onSearch() {
    let params = new HttpParams();

    if (this.searchParams.name) {
      params = params.set('username', this.searchParams.name);
    }
  
    this.dataService.searchUsersExerciseMicroservice(params).subscribe((response: any) => {
      this.users = response.content;
    });
  }

  seeCustomerTrains(userId: string) {
    this.currentUserId = userId;
    this.dataService.getAllTrainsFromOneUser(userId).subscribe((response: any) => {
      this.trains = response || [];
    });
  }

  deactivateTrain(trainId: string) {
    if (confirm('Tem certeza que deseja desativar este treino?')) {
      this.dataService.deactivateTrain(trainId).subscribe((response: any) => {
        console.log(response);
        location.reload();
      });
    }
  }

  deleteTrain(trainId: string) {
    if (confirm('Tem certeza que deseja remover este treino?')) {
      this.dataService.deleteTrain(trainId).subscribe((response: any) => {
        console.log(response);
        location.reload();
      });
    }
  }

  extendTrain(trainId: string) {
    if (confirm('Tem certeza que deseja extender a validade deste treino?')) {
      this.dataService.extendTrain(trainId).subscribe((response: any) => {
        console.log(response);
        this.refreshTrains();
      });
    }
  }

  refreshTrains() {
    if (this.currentUserId) {
      this.seeCustomerTrains(this.currentUserId);
    }
  }
}
