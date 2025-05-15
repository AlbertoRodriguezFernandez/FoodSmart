import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'FoodSmart';
  isAuthenticated = false;

  constructor(public authService: AuthService) {
    console.log('AppComponent initialized');
  }

  ngOnInit(): void {
    // Suscribirse a los cambios de autenticación
    this.authService.currentUser.subscribe({
      next: (user) => {
        console.log('Authentication state changed:', user ? 'Logged in' : 'Not logged in');
        this.isAuthenticated = !!user;
      },
      error: (err) => {
        console.error('Error in auth subscription:', err);
        this.isAuthenticated = false;
      }
    });
  }

  getUserName(): string {
    try {
      const user = this.authService.currentUserValue;
      return user && user.name ? user.name : '';
    } catch (error) {
      console.error('Error getting user name:', error);
      return '';
    }
  }

  isLoggedIn(): boolean {
    try {
      return this.authService.isAuthenticated();
    } catch (error) {
      console.error('Error checking authentication:', error);
      return false;
    }
  }

  logout(): void {
    try {
      this.authService.logout();
    } catch (error) {
      console.error('Error during logout:', error);
    }
  }
}






