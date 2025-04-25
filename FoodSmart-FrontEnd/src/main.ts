/*
Esto es lo primero que se ejecuta al iniciar la aplicación.
Se importa el módulo AppComponent, que es el componente principal de la aplicación (app.component.html).
Se importa el módulo bootstrapApplication, que es el encargado de iniciar la aplicación.
Se importa el módulo importProvidersFrom, que se utiliza para importar módulos de Angular.
*/

/*
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(HttpClientModule)
  ]
}).catch(err => console.error(err));
*/




import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient } from '@angular/common/http';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient()
  ]
}).catch(err => console.error(err));