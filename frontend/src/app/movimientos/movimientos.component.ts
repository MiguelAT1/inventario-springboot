import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MovimientoDTO, MovimientoForm, MovimientoService } from '../services/movimiento.service';
import { ProductoDTO, ProductoService } from '../services/producto.service';

@Component({
  selector: 'app-movimientos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './movimientos.component.html'
})
export class MovimientosComponent implements OnInit {
  movimientos: MovimientoDTO[] = [];
  productos: ProductoDTO[] = [];
  mensaje = '';
  movimientoForm: MovimientoForm = {
    productoId: 0,
    cantidad: 1,
    observacion: ''
  };

  constructor(
    private movimientoService: MovimientoService,
    private productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.cargarMovimientos();
    this.productoService.listar().subscribe({
      next: productos => (this.productos = productos),
      error: () => (this.mensaje = 'No se pudieron cargar los productos para los movimientos.')
    });
  }

  cargarMovimientos(): void {
    this.movimientoService.listar().subscribe({
      next: movimientos => (this.movimientos = movimientos),
      error: () => (this.mensaje = 'No se pudieron cargar los movimientos.')
    });
  }

  registrarEntrada(): void {
    if (this.movimientoForm.productoId === 0 || this.movimientoForm.cantidad <= 0) {
      this.mensaje = 'Selecciona producto y cantidad válida.';
      return;
    }

    this.movimientoService.entrada(this.movimientoForm).subscribe({
      next: () => {
        this.mensaje = 'Entrada registrada correctamente.';
        this.reiniciarFormulario();
        this.cargarMovimientos();
      },
      error: () => {
        this.mensaje = 'Error al registrar la entrada.';
      }
    });
  }

  registrarSalida(): void {
    if (this.movimientoForm.productoId === 0 || this.movimientoForm.cantidad <= 0) {
      this.mensaje = 'Selecciona producto y cantidad válida.';
      return;
    }

    this.movimientoService.salida(this.movimientoForm).subscribe({
      next: () => {
        this.mensaje = 'Salida registrada correctamente.';
        this.reiniciarFormulario();
        this.cargarMovimientos();
      },
      error: () => {
        this.mensaje = 'Error al registrar la salida.';
      }
    });
  }

  reiniciarFormulario(): void {
    this.movimientoForm = { productoId: 0, cantidad: 1, observacion: '' };
  }
}
