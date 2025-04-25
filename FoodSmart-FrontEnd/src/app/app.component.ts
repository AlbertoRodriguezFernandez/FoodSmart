
// FICHERO: app.component.ts: lo que le mostramos a Marina
/*
import { Component } from '@angular/core';
import { SaludoService } from './saludo.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [],
  template: `<h1>{{ saludo }}</h1>`
})

export class AppComponent {
  saludo = '';

  constructor(private saludoService: SaludoService) {}

  ngOnInit() {
    this.saludoService.obtenerSaludo().subscribe(res => {
      this.saludo = res;
    });
  }
}
*/


// FICHERO: app.usercomponent.ts: Lista de Usuarios
/*
import { Component, OnInit } from '@angular/core';
import { UsersService } from './users.service';
import { Users } from './users.model';
import { CommonModule } from '@angular/common'; // Importar CommonModule

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule], // Agregar CommonModule aquí
  template: `
    <h1>Lista de Usuarios</h1>
    <ul>
      <li *ngFor="let user of users">
        {{ user.nombre }} ({{ user.correo }})
      </li>
    </ul>
  `,
})
export class AppComponent implements OnInit {
  users: Users[] = [];

  constructor(private usersService: UsersService) {}

  ngOnInit() {
    this.usersService.getUsers().subscribe((data) => {
      this.users = data;
    });
  }
}
*/

// FICHERO: app.module.ts: Hola desde el backend
/*
import { Component, importProvidersFrom } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { bootstrapApplication } from '@angular/platform-browser';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HttpClientModule],
  template: `<h1>{{ saludo }}</h1>`
})
export class AppComponent {
  saludo = '';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get('http://localhost:8080/api/saludo', { responseType: 'text' })
      .subscribe(res => this.saludo = res);
  }
}
*/


// VERSION DEFINITIVA
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { UsersService } from './users/users.service';
import { Users } from './users/users.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html',
})

export class AppComponent {
    
  users: Users[] = [];
  constructor(private userService: UsersService) {}

    ngOnInit() {
      
      this.userService.getUsers().subscribe((data: Users[]) => {
        this.users = data;
        console.log(this.users);  // Para verificar que llegan datos
    }
    );
  } 
}
