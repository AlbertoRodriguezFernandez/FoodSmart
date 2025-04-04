import { Component, OnInit } from '@angular/core';
import { UsersService } from './users.service';
import { Users } from './users.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [],
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