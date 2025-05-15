import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';
  
  // BehaviorSubject para mantener el estado de autenticación
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    console.log('AuthService initialized');
    // Inicializar estado desde localStorage si existe
    const storedUser = localStorage.getItem('currentUser');
    this.currentUserSubject = new BehaviorSubject<any>(
      storedUser ? JSON.parse(storedUser) : null
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  // Obtener el usuario actual
  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  // Iniciar sesión
  login(mail: string, password: string): Observable<any> {
    console.log('Attempting login with:', { mail });
    
    return this.http.post<any>(`${this.apiUrl}/login`, { mail, password })
      .pipe(
        tap(user => {
          console.log('Login successful, received:', user);
          // Almacenar detalles del usuario en localStorage
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }),
        catchError(error => {
          console.error('Login failed:', error);
          return throwError(() => new Error(error.error || 'Error en la autenticación'));
        })
      );
  }

  // Registrar un nuevo usuario
  register(name: string, mail: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, { name, mail, password })
      .pipe(
        catchError(error => {
          console.error('Registration failed:', error);
          return throwError(() => new Error(error.error || 'Error en el registro'));
        })
      );
  }

  // Cerrar sesión
  logout() {
    console.log('Logging out');
    // Eliminar el usuario del localStorage
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  // Verificar si el usuario está autenticado
  isAuthenticated(): boolean {
    const authenticated = this.currentUserValue !== null;
    console.log('Authentication status:', authenticated);
    return authenticated;
  }
}