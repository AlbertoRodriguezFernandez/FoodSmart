import { Component, OnInit } from '@angular/core';
import { UsersService } from '../services/users.service';
import { UsersModel } from '../models/users';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})


export class UsersComponent implements OnInit{
  
  users: UsersModel[] = [];

  constructor(private usersService: UsersService) {}

  ngOnInit(): void {
    this.usersService.getUsers().subscribe({
      next: (data) => {
        this.users = data;
        console.log('Usuarios recibidos:', data);
      },
      error: (err) => {
        console.error('Error al obtener usuarios:', err);
      }
    });
  }
}




  

