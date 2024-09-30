import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataService } from '../../../services/data.service';
import { CustomerNavbarComponent } from '../customer-navbar/customer-navbar.component';

@Component({
  selector: 'app-customer-trains',
  standalone: true,
  imports: [CommonModule, CustomerNavbarComponent],
  templateUrl: './customer-trains.component.html',
  styleUrl: './customer-trains.component.scss',
})
export class CustomerTrainsComponent implements OnInit {
  trains: any[] = [];

  constructor(private dataService: DataService) {}

  ngOnInit() {
    const userId = localStorage.getItem('userId') || '';
    this.dataService.getAllTrainsFromOneUser(userId).subscribe(
      (data) => {
        this.trains = data;
      },
      (error) => {
        console.error('Erro ao carregar os treinos:', error);
      }
    );
  }

  /*getExercises(train: any): string[] {
    return Object.keys(train)
      .filter((key) => key.startsWith('exercise') && train[key])
      .map((key) => train[key]);
  }*/

  getExerciseNumbers(): number[] {
    // Retorna um array com números de 1 a 20
    return Array.from({ length: 20 }, (_, i) => i + 1);
  }
}
