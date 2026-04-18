import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductoService } from '../services/producto.service';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-lista-productos',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './lista-productos.component.html',
  styleUrl: './lista-productos.component.css'
})
export class ListaProductosComponent implements OnInit {

  productos: any[] = [];
  movimientos: any[] = [];
  filtroId: number | null = null;

  productoSeleccionadoId: number | null = null;
  tipoMovimiento: 'entrada' | 'salida' | null = null;

  movimientoForm = {
    cantidad: 0,
    observacion: ''
  };

  constructor(
    private service: ProductoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargar();
    this.cargarMovimientos();

    this.router.events.subscribe(() => {
      this.cargar();
      this.cargarMovimientos();
    });
  }

  cargar() {
    this.service.listar().subscribe((data: any) => {
      this.productos = data;
    });
  }

  cargarMovimientos() {
    this.service.listarMovimientos().subscribe((data: any) => {
      this.movimientos = data;
    });
  }

  buscarPorId() {
    if (!this.filtroId) {
      this.cargar();
      return;
    }

    this.service.buscarPorId(this.filtroId).subscribe({
      next: (data: any) => {
        this.productos = data ? [data] : [];
      },
      error: () => {
        alert('Producto no encontrado');
        this.cargar();
      }
    });
  }

  eliminar(id: number) {
    if (!confirm('¿Seguro que quieres eliminar?')) return;

    this.service.eliminar(id).subscribe({
      next: () => {
        this.cargar();
        this.cargarMovimientos();
      },
      error: (err) => {
        console.error('Error al eliminar:', err);
      }
    });
  }

  editar(id: number) {
    this.router.navigate(['/nuevo', id]);
  }

  abrirMovimiento(id: number, tipo: 'entrada' | 'salida') {
    this.productoSeleccionadoId = id;
    this.tipoMovimiento = tipo;
    this.movimientoForm = {
      cantidad: 0,
      observacion: ''
    };
  }

  cancelarMovimiento() {
    this.productoSeleccionadoId = null;
    this.tipoMovimiento = null;
    this.movimientoForm = {
      cantidad: 0,
      observacion: ''
    };
  }

  guardarMovimiento() {
    if (!this.productoSeleccionadoId || this.movimientoForm.cantidad <= 0) {
      alert('Ingresa una cantidad válida');
      return;
    }

    const payload = {
      productoId: this.productoSeleccionadoId,
      cantidad: this.movimientoForm.cantidad,
      observacion: this.movimientoForm.observacion
    };

    const peticion =
      this.tipoMovimiento === 'entrada'
        ? this.service.registrarEntrada(payload)
        : this.service.registrarSalida(payload);

    peticion.subscribe({
      next: () => {
        alert(`Movimiento de ${this.tipoMovimiento} registrado correctamente`);
        this.cancelarMovimiento();
        this.cargar();
        this.cargarMovimientos();
      },
      error: (err) => {
        console.error(err);
        alert('No se pudo registrar el movimiento. Verifica el stock o los datos.');
      }
    });
  }
}
