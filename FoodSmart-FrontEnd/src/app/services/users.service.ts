import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsersModel } from '../models/users';


@Injectable({
  providedIn: 'root'
})
export class UsersService {
  
  private apiUrl = 'http://localhost:8080/api/users';  // URL a la API en el backend

  constructor(private http: HttpClient) {}

  getUsers(): Observable<UsersModel[]> {
    return this.http.get<UsersModel[]>(this.apiUrl);
  }
}