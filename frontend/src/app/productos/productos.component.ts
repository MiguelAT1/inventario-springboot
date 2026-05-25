import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Categoria, CategoriaService } from '../services/categoria.service';
import { Proveedor, ProveedorService } from '../services/proveedor.service';
import { ProductoDTO, ProductoDetalle, ProductoPayload, ProductoService } from '../services/producto.service';

interface ProductoForm {
  id?: number;
  nombre: string;
  stock: number;
  precio: number;
  estado: string;
  ubicacion: string;
  categoriaId: number;
  proveedorId: number;
}

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './productos.component.html'
})
export class ProductosComponent implements OnInit {
  productos: ProductoDTO[] = [];
  productosOriginales: ProductoDTO[] = [];
  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];
  mensaje = '';
  cargando = false;
  searchId = '';
  esAdmin = false;
  productoForm: ProductoForm = this.crearFormularioInicial();

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.esAdmin = this.authService.isAdmin();
    this.recargarTodo();
  }

  crearFormularioInicial(): ProductoForm {
    return {
      nombre: '',
      stock: 0,
      precio: 0,
      estado: '',
      ubicacion: '',
      categoriaId: 0,
      proveedorId: 0
    };
  }

  recargarTodo(): void {
    this.cargando = true;
    this.searchId = '';
    this.productoService.listar().subscribe({
      next: productos => {
        this.productos = productos;
        this.productosOriginales = [...productos];
      },
      error: () => {
        this.mensaje = 'No se pudo cargar la lista de productos.';
      }
    });
    this.categoriaService.listar().subscribe({
      next: categorias => (this.categorias = categorias),
      error: () => {
        this.mensaje = 'No se pudo cargar las categorías.';
      }
    });
    this.proveedorService.listar().subscribe({
      next: proveedores => (this.proveedores = proveedores),
      error: () => {
        this.mensaje = 'No se pudo cargar los proveedores.';
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }

  editarProducto(id: number): void {
    this.productoService.buscar(id).subscribe({
      next: producto => {
        this.productoForm = {
          id: producto.id,
          nombre: producto.nombre || '',
          stock: producto.stock || 0,
          precio: producto.precio || 0,
          estado: producto.estado || '',
          ubicacion: producto.ubicacion || '',
          categoriaId: producto.categoria?.id ?? 0,
          proveedorId: producto.proveedor?.id ?? 0
        };
        this.mensaje = '';
      },
      error: () => {
        this.mensaje = 'No se pudo cargar el producto para editar.';
      }
    });
  }

  guardar(): void {
    if (this.productoForm.categoriaId === 0 || this.productoForm.proveedorId === 0) {
      this.mensaje = 'Selecciona categoría y proveedor.';
      return;
    }

    const payload: ProductoPayload = {
      nombre: this.productoForm.nombre,
      stock: this.productoForm.stock,
      precio: this.productoForm.precio,
      estado: this.productoForm.estado,
      ubicacion: this.productoForm.ubicacion,
      categoria: { id: this.productoForm.categoriaId },
      proveedor: { id: this.productoForm.proveedorId }
    };

    if (this.productoForm.id) {
      this.productoService.actualizar(this.productoForm.id, payload).subscribe({
        next: () => {
          this.mensaje = 'Producto actualizado correctamente.';
          this.cancelarEdicion();
          this.recargarTodo();
        },
        error: () => {
          this.mensaje = 'Error al actualizar el producto.';
        }
      });
    } else {
      this.productoService.crear(payload).subscribe({
        next: () => {
          this.mensaje = 'Producto creado correctamente.';
          this.cancelarEdicion();
          this.recargarTodo();
        },
        error: () => {
          this.mensaje = 'Error al crear el producto.';
        }
      });
    }
  }

  eliminarProducto(id: number): void {
    if (!confirm('¿Eliminar este producto?')) {
      return;
    }

    this.productoService.eliminar(id).subscribe({
      next: () => {
        this.mensaje = 'Producto eliminado.';
        this.recargarTodo();
      },
      error: () => {
        this.mensaje = 'Error al eliminar el producto.';
      }
    });
  }

  cancelarEdicion(): void {
    this.productoForm = this.crearFormularioInicial();
    this.mensaje = '';
  }

  filtrarPorId(): void {
    if (!this.searchId || this.searchId.toString().trim() === '') {
      this.mensaje = 'Por favor ingresa un ID válido.';
      return;
    }

    const id = parseInt(this.searchId.toString(), 10);
    const productosFiltrados = this.productosOriginales.filter(p => p.id === id);

    if (productosFiltrados.length === 0) {
      this.mensaje = `No se encontró producto con ID ${id}.`;
      this.productos = [];
    } else {
      this.productos = productosFiltrados;
      this.mensaje = '';
    }
  }

  verTodos(): void {
    this.productos = [...this.productosOriginales];
    this.searchId = '';
    this.mensaje = '';
  }
}
