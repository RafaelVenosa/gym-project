import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataService } from '../../../services/data.service';
import { FormsModule } from '@angular/forms';
import { TrainerNavbarComponent } from '../trainer-navbar/trainer-navbar.component';

@Component({
  selector: 'app-trainer-expired-trains',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TrainerNavbarComponent],
  templateUrl: './trainer-expired-trains.component.html',
  styleUrl: './trainer-expired-trains.component.scss'
})
export class TrainerExpiredTrainsComponent implements OnInit{
  trains: any[] = [];

  constructor(private dataService: DataService) {}

  ngOnInit() {
    console.log("Teste");
    this.loadTrains();
  }

  loadTrains() {
    this.dataService.getExpiredTrains().subscribe(
      (response: any) => {
        this.trains = response.content;
        console.log(response.content)
      },
      (error) => {
        console.error('Erro ao carregar os treinos', error);
      }
    );
  }

  getExerciseNumbers(): number[] {
    // Retorna um array com números de 1 a 20
    return Array.from({ length: 20 }, (_, i) => i + 1);
  }

  extendTrain(trainId: string){
    this.dataService.extendTrain(trainId).subscribe((response: any) => {
      alert("Treino extendido com sucesso!")
      location.reload();
    })
  }
}
