/*import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductsModel } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private apiUrl = 'http://localhost:8080/api/products';  // CAMBIAR URL a la API en el backend

  constructor(private http: HttpClient) {}

  getProducts(): Observable<ProductsModel[]> {
    return this.http.get<ProductsModel[]>(this.apiUrl);
  }
    searchProducts(query: string, page: number = 1, pageSize: number = 10): Observable<ProductsModel[]> {
      return this.http.get<ProductsModel[]>(`${this.apiUrl}/search?query=${query}&page=${page}&pageSize=${pageSize}`);
    }
  
    getProductById(id: string): Observable<ProductsModel> {
      return this.http.get<ProductsModel>(`${this.apiUrl}/${id}`);
  }
  
  getRandomProduct(): Observable<ProductsModel> {
    return this.http.get<ProductsModel>(`${this.apiUrl}/random`);
  }
}*/

/* VERSION WEB SCRAPPING
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
  productName: string;
  imageUrl: string;
  price: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  
  //private apiUrl = 'http://localhost:8080/api/products';
  private apiUrl = 'http://localhost:8080/api/scraper';


  constructor(private http: HttpClient) {}

  searchProducts(query: string): Observable<Product[]> {
    const params = new HttpParams().set('q', query);
    return this.http.get<Product[]>(`${this.apiUrl}/search`, { params });

  }

}
*/

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  searchProducts(query: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/integrated/search?q=${query}`);
  }

  // Mantener los métodos existentes para buscar solo en Open Food Facts o solo en Mercastic
  searchOpenFoodFactsProducts(query: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/products/search?q=${query}`);
  }
  
  searchMercastic(query: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/scraper/search?q=${query}`);
  }
}