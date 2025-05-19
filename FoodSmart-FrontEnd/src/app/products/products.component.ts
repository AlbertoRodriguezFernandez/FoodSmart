import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/products.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {
  products: any[] = [];
  filteredProducts: any[] = [];
  loading = false;
  query = '';
  showFilters = false;
  selectedProduct: any = null;
  
  // Datos para los filtros
  categories: string[] = [
    'Lácteos', 'Carnes', 'Pescados', 'Frutas', 'Verduras', 
    'Cereales', 'Legumbres', 'Bebidas', 'Snacks', 'Dulces'
  ];
  
  nutriscores: string[] = ['a', 'b', 'c', 'd', 'e'];
  
  filters = {
    category: '',
    nutriscoreGrade: '',
    minPrice: null as number | null,
    maxPrice: null as number | null,
    maxCalories: null as number | null,
    maxFat: null as number | null,
    maxSugars: null as number | null,
    minProteins: null as number | null
  };

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
          matchConfidence: item.matchConfidence || 0,
          
          // Campos adicionales para categorías
          categories: item.categories || '',
          category_tags: item.category_tags || []
        }));
        
        // Aplicar filtros iniciales
        this.applyFilters();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al buscar productos:', err);
        this.loading = false;
        this.products = [];
        this.filteredProducts = [];
      }
    });
  }

  /**
   * Muestra los detalles nutricionales de un producto
   */
  showProductDetails(product: any) {
    this.selectedProduct = product;
    
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
  
  /**
   * Alterna la visibilidad del panel de filtros
   */
  toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }
  
  /**
   * Aplica los filtros a los productos
   */
  applyFilters(): void {
    this.filteredProducts = this.products.filter(product => {
      // Filtrar por categoría
      if (this.filters.category && 
          (!product.categories || !product.categories.toLowerCase().includes(this.filters.category.toLowerCase()))) {
        return false;
      }
      
      // Filtrar por nutriscore
      if (this.filters.nutriscoreGrade && 
          product.nutriscoreGrade !== this.filters.nutriscoreGrade) {
        return false;
      }
      
      // Filtrar por precio mínimo
      if (this.filters.minPrice !== null) {
        const price = this.extractNumericPrice(product.price);
        if (price === null || price < this.filters.minPrice) {
          return false;
        }
      }
      
      // Filtrar por precio máximo
      if (this.filters.maxPrice !== null) {
        const price = this.extractNumericPrice(product.price);
        if (price === null || price > this.filters.maxPrice) {
          return false;
        }
      }
      
      // Filtrar por calorías máximas
      if (this.filters.maxCalories !== null && 
          product.nutriments && 
          product.nutriments.energy) {
        const calories = parseFloat(product.nutriments.energy);
        if (!isNaN(calories) && calories > this.filters.maxCalories) {
          return false;
        }
      }
      
      // Filtrar por grasas máximas
      if (this.filters.maxFat !== null && 
          product.nutriments && 
          product.nutriments.fat) {
        const fat = parseFloat(product.nutriments.fat);
        if (!isNaN(fat) && fat > this.filters.maxFat) {
          return false;
        }
      }
      
      // Filtrar por azúcares máximos
      if (this.filters.maxSugars !== null && 
          product.nutriments && 
          product.nutriments.sugars) {
        const sugars = parseFloat(product.nutriments.sugars);
        if (!isNaN(sugars) && sugars > this.filters.maxSugars) {
          return false;
        }
      }
      
      // Filtrar por proteínas mínimas
      if (this.filters.minProteins !== null && 
          product.nutriments && 
          product.nutriments.proteins) {
        const proteins = parseFloat(product.nutriments.proteins);
        if (!isNaN(proteins) && proteins < this.filters.minProteins) {
          return false;
        }
      }
      
      return true;
    });
  }
  
  /**
   * Extrae el valor numérico del precio
   */
  extractNumericPrice(priceString: string): number | null {
    if (!priceString) return null;
    
    // Eliminar símbolos de moneda y espacios, y reemplazar comas por puntos
    const cleanPrice = priceString.replace(/[€$\s]/g, '').replace(',', '.');
    const price = parseFloat(cleanPrice);
    
    return isNaN(price) ? null : price;
  }
  
  /**
   * Limpia todos los filtros
   */
  clearFilters(): void {
    this.filters = {
      category: '',
      nutriscoreGrade: '',
      minPrice: null,
      maxPrice: null,
      maxCalories: null,
      maxFat: null,
      maxSugars: null,
      minProteins: null
    };
    
    this.applyFilters();
  }
  
  /**
   * Verifica si hay filtros activos
   */
  hasActiveFilters(): boolean {
    return !!(
      this.filters.category ||
      this.filters.nutriscoreGrade ||
      this.filters.minPrice !== null ||
      this.filters.maxPrice !== null ||
      this.filters.maxCalories !== null ||
      this.filters.maxFat !== null ||
      this.filters.maxSugars !== null ||
      this.filters.minProteins !== null
    );
  }
}