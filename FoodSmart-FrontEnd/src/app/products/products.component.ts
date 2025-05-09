/*import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../services/products.service';
import { ProductsModel } from '../models/product';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule],  // Importante añadir CommonModule para usar *ngFor
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {  // Implementa OnInit correctamente

  products: ProductsModel[] = [];
  loading: boolean = true;
  error: boolean = false;
  searchQuery: string = '';

  constructor(private productsService: ProductsService) {}

  ngOnInit(): void {
    // Ya que getProducts() está comentado en tu servicio, usamos searchProducts
    this.searchProducts('');
  }

  searchProducts(query: string): void {
    this.loading = true;
    this.error = false;
    
    this.productsService.searchProducts(query).subscribe({
      next: (data) => {
        this.products = data;
        this.loading = false;
        console.log('Productos recibidos:', data);
      },
      error: (err) => {
        console.error('Error al obtener productos:', err);
        this.error = true;
        this.loading = false;
      }
    });
  }
}*/

/*
// VERSION API
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/products.service';
import { ProductsModel } from '../models/product';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})

export class ProductsComponent implements OnInit {
  products: ProductsModel[] = [];
  loading = false;
  query = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    // Cargar productos populares al inicio
    this.search('');
  }

  search(q?: string): void {
    if (q !== undefined) {
      this.query = q;
    }
    
    this.loading = true;
    this.productService.searchProducts(this.query).subscribe({
      next: (data) => {
        this.products = data.map(item => ({
          product_name: item.productName,
          image_url: item.imageUrl,
          price: item.price
        }));
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al buscar productos:', err);
        this.loading = false;
        this.products = [];
      }
    });
  }
}
*/

// VERSION WEB SCRAPING
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/products.service';
import { ProductsModel } from '../models/product';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})

export class ProductsComponent implements OnInit {
  products: ProductsModel[] = [];
  loading = false;
  query = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    // Cargar productos populares al inicio
    this.search('');
  }

  search(q?: string): void {
    if (q !== undefined) {
      this.query = q;
    }
    
    this.loading = true;
    this.productService.searchProducts(this.query).subscribe({
      next: (data) => {
        console.log('Datos recibidos del backend:', data);
        this.products = data.map(item => ({
          product_name: item.productName,
          image_url: item.imageUrl || '',
          price: item.price || ''
        }));
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al buscar productos:', err);
        this.loading = false;
        this.products = [];
      }
    });
  }
}
