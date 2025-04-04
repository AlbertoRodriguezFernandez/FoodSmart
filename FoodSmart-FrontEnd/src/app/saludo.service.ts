import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SaludoService {
  private apiUrl = 'http://localhost:8080/api/saludo';

  constructor(private http: HttpClient) {}

  obtenerSaludo(): Observable<string> {
    return this.http.get(this.apiUrl, { responseType: 'text' });
  }
}