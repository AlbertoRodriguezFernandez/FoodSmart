import { Component, importProvidersFrom } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { bootstrapApplication } from '@angular/platform-browser';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HttpClientModule],
  template: `<h1>{{ saludo }}</h1>`
})
export class AppComponent {
  saludo = '';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get('http://localhost:8080/api/saludo', { responseType: 'text' })
      .subscribe(res => this.saludo = res);
  }
}