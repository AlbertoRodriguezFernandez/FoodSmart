import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user = {
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
    
    console.log("Intentando iniciar sesión con:", this.user);
    
    this.authService.login(this.user.mail, this.user.password)
      .subscribe({
        next: (response) => {
          console.log("Inicio de sesión exitoso:", response);
          this.loading = false;
          // Redirigir a la página principal después del login exitoso
          this.router.navigate(['/products']);
        },
        error: (error) => {
          console.error('Error de inicio de sesión:', error);
          this.loading = false;
          this.errorMessage = 'Credenciales incorrectas. Por favor intente nuevamente.';
        }
      });
  }
}