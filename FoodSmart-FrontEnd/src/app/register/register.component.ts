import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  user = {
    name: '',
    mail: '',
    password: ''
  };
  
  errorMessage = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { 
    // Redirigir si ya está logueado
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/products']);
    }
  }

  onSubmit() {
    this.loading = true;
    this.errorMessage = '';
    
    console.log("Intentando registrar:", this.user);
    
    this.authService.register(this.user.name, this.user.mail, this.user.password)
      .subscribe({
        next: (response) => {
          console.log("Registro exitoso:", response);
          this.loading = false;
          // Opcional: iniciar sesión automáticamente o redirigir al login
          this.router.navigate(['/login']);
        },
        error: (error) => {
          console.error('Error al registrar usuario:', error);
          this.loading = false;
          this.errorMessage = 'Error al registrar usuario. Por favor intente nuevamente.';
        }
      });
  }
}