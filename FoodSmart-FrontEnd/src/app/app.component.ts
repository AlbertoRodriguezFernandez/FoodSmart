
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { UsersService } from './services/users.service';
import { UsersModel } from './models/users';
import { UsersComponent } from './users/users.component';
import { ProductsComponent } from './products/products.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, UsersComponent, ProductsComponent],
  templateUrl: './app.component.html',
})

export class AppComponent {} 








