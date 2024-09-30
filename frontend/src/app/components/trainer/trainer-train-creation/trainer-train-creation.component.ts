import { Component } from '@angular/core';
import { TrainerNavbarComponent } from '../trainer-navbar/trainer-navbar.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DataService } from '../../../services/data.service';

@Component({
  selector: 'app-trainer-train-creation',
  standalone: true,
  imports: [
    TrainerNavbarComponent,
    CommonModule,
    FormsModule
  ],
  templateUrl: './trainer-train-creation.component.html',
  styleUrl: './trainer-train-creation.component.scss'
})
export class TrainerTrainCreationComponent {
  trainName: string = '';
  exercises: { id: number, valor: string }[] = [{ id: 0, valor: '' }];
  private nextId: number = 1;

  constructor(private dataService: DataService) {}

  updateNomeTreino(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.trainName = inputElement.value;
  }

  updateExercicio(event: Event, index: number) {
    const inputElement = event.target as HTMLInputElement;
    this.exercises[index].valor = inputElement.value;
  }

  adicionarExercicio() {
    if (this.exercises.length < 20) {
      this.exercises.push({ id: this.nextId++, valor: '' });
    }
  }

  trackByIndex(index: number, item: any) {
    return item.id;
  }

  // Método para tratar o envio do treino
  onSubmit() {
    const trainCreator = localStorage.getItem('username'); // Pegando o username do localStorage

    // Verifica se os campos obrigatórios estão preenchidos
    if (!this.trainName.trim()) {
      alert('O campo Nome do Treino é obrigatório!');
      return;
    }

    if (!trainCreator) {
      alert('O criador do treino não foi encontrado! Verifique o localStorage.');
      return;
    }

    if (!this.exercises[0].valor.trim()) {
      alert('O Exercício 1 é obrigatório!');
      return;
    }

    // Monta o objeto de treino
    const trainData: any = {
      trainName: this.trainName,
      trainCreator: trainCreator,
      exercise1: this.exercises[0].valor
    };

    // Adiciona os exercícios opcionais
    for (let i = 1; i < this.exercises.length; i++) {
      if (this.exercises[i].valor.trim()) {
        trainData[`exercise${i+1}`] = this.exercises[i].valor;
      }
    }

    // Chama a função para salvar o treino
    this.createTrain(trainData);
  }

  createTrain(data: any) {
    this.dataService.createTrain(data).subscribe(
      (response: any) => {
        alert('Treino criado com sucesso!');
        location.reload();
      },
      (error: any) => {
        console.error('Erro ao criar treino:', error);
      }
    );
  }
}
