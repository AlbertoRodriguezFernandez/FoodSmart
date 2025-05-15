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
/*
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
*/

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
  products: any[] = []; // Usamos any para manejar campos nutricionales adicionales
  loading = false;
  query = '';
  selectedProduct: any = null;

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
          // Campos básicos
          product_name: item.productName || item.product_name || '',
          image_url: item.imageUrl || item.image_url || '',
          price: item.price || '',
          
          // Campos de información nutricional
          nutriscoreGrade: item.nutriscoreGrade || '',
          ingredientsText: item.ingredientsText || '',
          brands: item.brands || '',
          quantity: item.quantity || '',
          nutriments: item.nutriments || {},
          
          // Campos de integración
          source: item.source || '',
          matchConfidence: item.matchConfidence || 0
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

  /**
   * Muestra los detalles nutricionales de un producto
   */
  showProductDetails(product: any) {
    this.selectedProduct = product; // Para mostrar en un modal futuro
    
    // Para una implementación inicial simple, usamos un alert
    let infoText = `Información de ${product.product_name}\n\n`;
    
    if (product.brands) infoText += `Marca: ${product.brands}\n`;
    if (product.quantity) infoText += `Cantidad: ${product.quantity}\n`;
    if (product.nutriscoreGrade) infoText += `Nutriscore: ${product.nutriscoreGrade.toUpperCase()}\n\n`;
    
    if (product.ingredientsText) {
      infoText += `Ingredientes: ${product.ingredientsText}\n\n`;
    }
    
    if (product.nutriments && Object.keys(product.nutriments).length > 0) {
      infoText += "Información nutricional:\n";
      
      // Mapear los valores nutricionales más comunes
      const nutrients = [
        { key: 'energy', label: 'Energía', unit: 'kcal' },
        { key: 'fat', label: 'Grasas', unit: 'g' },
        { key: 'saturated-fat', label: 'Grasas saturadas', unit: 'g' },
        { key: 'carbohydrates', label: 'Carbohidratos', unit: 'g' },
        { key: 'sugars', label: 'Azúcares', unit: 'g' },
        { key: 'fiber', label: 'Fibra', unit: 'g' },
        { key: 'proteins', label: 'Proteínas', unit: 'g' },
        { key: 'salt', label: 'Sal', unit: 'g' },
        { key: 'sodium', label: 'Sodio', unit: 'g' }
      ];
      
      for (const nutrient of nutrients) {
        if (product.nutriments[nutrient.key]) {
          const value = product.nutriments[nutrient.key];
          const unit = product.nutriments[nutrient.key + '_unit'] || nutrient.unit;
          infoText += `- ${nutrient.label}: ${value}${unit}\n`;
        }
      }
    }
    
    // Fuente de datos
    if (product.source) {
      infoText += `\nDatos nutricionales: ${this.getSourceName(product.source)}\n`;
    }
    
    if (product.price) {
      infoText += `Precio: ${product.price}\n`;
    }
    
    alert(infoText);
    
    // Aquí en el futuro podrías abrir un modal personalizado en lugar de usar alert
    // this.dialog.open(ProductDetailsModalComponent, { data: product });
  }
  
  /**
   * Obtiene un nombre amigable para la fuente de datos
   */
  getSourceName(source: string): string {
    switch (source) {
      case 'openFoodFacts':
        return 'Open Food Facts';
      case 'mercastic':
        return 'Mercastic';
      case 'integrated':
        return 'Open Food Facts + Mercastic';
      default:
        return source;
    }
  }
  
  /**
   * Determina si un producto tiene información nutricional
   */
  hasNutritionalInfo(product: any): boolean {
    return !!(
      product.nutriscoreGrade || 
      product.ingredientsText || 
      (product.nutriments && Object.keys(product.nutriments).length > 0)
    );
  }
}