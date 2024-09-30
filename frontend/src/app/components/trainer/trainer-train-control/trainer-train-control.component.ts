import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataService } from '../../../services/data.service';
import { FormsModule } from '@angular/forms';
import { TrainerNavbarComponent } from '../trainer-navbar/trainer-navbar.component';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-trainer-train-control',
  standalone: true,
  imports: [CommonModule, FormsModule, TrainerNavbarComponent],
  templateUrl: './trainer-train-control.component.html',
  styleUrl: './trainer-train-control.component.scss',
})
export class TrainerTrainControlComponent implements OnInit {
  searchParams = { username: '' };
  trains: any[] = [];
  users: any[] = [];
  affiliatedUsersMap: { [key: string]: any[] } = {}; // Inicialização
  editingTrain: any = null; // Controla qual treino está sendo editado

  constructor(private dataService: DataService) {}

  ngOnInit() {
    this.loadTrains();
    this.affiliatedUsersMap = {}; // Inicializando aqui se necessário
  }

  // Função que carrega todos os treinos do treinador
  loadTrains() {
    this.dataService.getAllTrainsFromOneTrainer().subscribe((response: any) => {
      this.trains = response.content;
    });
  }

  toggleUsers(train: any) {
    if (!train.showUsers) {
      this.dataService.getAllUsersFromOneTrain(train.trainId).subscribe(
        (response: any) => {
          // Verifique se a resposta é um array
          if (Array.isArray(response)) {
            this.affiliatedUsersMap[train.trainId] = response;
          } else {
            console.error('A resposta não é um array:', response);
            this.affiliatedUsersMap[train.trainId] = []; // Inicializa como um array vazio em caso de erro
          }
          train.showUsers = true;
          this.editingTrain = null; // Esconde o modo de edição, se estiver ativo
        },
        (error) => {
          console.error('Erro ao carregar usuários afiliados:', error);
        }
      );
    } else {
      train.showUsers = false;
    }
  }

  onSearch() {
    let params = new HttpParams();

    if (this.searchParams.username){
      params = params.set('username', this.searchParams.username);
    }
  
    this.dataService.searchUsersExerciseMicroservice(params).subscribe((response: any) => {
      this.users = response.content;
      console.log(response.content)
    });
  }

  registerUserInTrain(userId: string, trainId: string) {
    const body = {trainId: trainId}
    this.dataService.registerUserInTrain(userId, body).subscribe((response: any) => {
      alert("Usuário associado com sucesso!")
      location.reload();
    })
  }

  // Função para iniciar a edição do treino
  editTrain(train: any) {
    // Criar uma cópia profunda do treino para evitar modificações indesejadas
    this.editingTrain = train;
    train.showUsers = false;
  }

  // Salva as alterações feitas no treino editado
  saveTrain(trainId: string) {
    this.dataService.updateTrain(trainId, this.editingTrain).subscribe(
      () => {
        this.loadTrains();
        this.editingTrain = null; // Sai do modo de edição
      },
      (error) => {
        console.error('Erro ao salvar o treino', error);
      }
    );
  }

  // Cancela a edição e retorna ao modo de visualização
  cancelEdit() {
    this.editingTrain = null;
  }

  // Função auxiliar para gerar números de exercícios (1 a 20)
  getExerciseNumbers(): number[] {
    return Array.from({ length: 20 }, (_, i) => i + 1);
  }

  // Desativa um treino
  deactivateTrain(trainId: string) {
    this.dataService.deactivateTrain(trainId).subscribe((response: any) => {
      alert("Treino desativado com sucesso!")
      location.reload();
    });
  }

  deleteTrain(trainId: string) {
    this.dataService.deleteTrain(trainId).subscribe((response:any) => {
      alert("Treino excluido com sucesso!")
      location.reload();
    })
  }

  extendTrain(trainId: string) {
    this.dataService.extendTrain(trainId).subscribe((response:any) => {
      alert("Treino estendido com sucesso!")
      location.reload();
    })
  }
}
