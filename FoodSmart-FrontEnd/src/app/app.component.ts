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