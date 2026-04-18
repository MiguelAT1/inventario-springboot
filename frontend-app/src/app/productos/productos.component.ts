import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../services/producto.service';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent implements OnInit {

  id: number | null = null;

  form = this.resetForm();

  constructor(
    private service: ProductoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const paramId = params.get('id');
      this.id = paramId ? Number(paramId) : null;

      if (this.id) {
        this.service.buscarPorId(this.id).subscribe({
          next: (data: any) => {
            this.form = data;
          },
          error: (err) => {
            console.error('Error al cargar producto:', err);
            alert('No se pudo cargar el producto.');
          }
        });
      } else {
        this.form = this.resetForm();
      }
    });
  }

  crear() {
    this.service.crear(this.form).subscribe({
      next: () => {
        alert('Producto creado correctamente');
        this.router.navigate(['/']).then(() => location.reload());
      },
      error: (err) => {
        console.error('Error al crear:', err);
        alert('No se pudo crear el producto. Verifica los datos ingresados.');
      }
    });
  }

  editar() {
    if (!this.id) return;

    this.service.actualizar(this.id, this.form).subscribe({
      next: () => {
        alert('Producto actualizado correctamente');
        this.router.navigate(['/']).then(() => location.reload());
      },
      error: (err) => {
        console.error('Error al actualizar:', err);
        alert('No se pudo actualizar el producto. Verifica el proveedor y los datos.');
      }
    });
  }

  private resetForm() {
    return {
      nombre: '',
      stock: 0,
      precio: 0,
      estado: '',
      ubicacion: '',
      proveedorId: 0
    };
  }
}
