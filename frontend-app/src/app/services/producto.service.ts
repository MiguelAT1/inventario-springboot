import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private apiUrl = 'http://localhost:8083/productos';
  private movimientosUrl = 'http://localhost:8083/movimientos';

  constructor(private http: HttpClient) {}

  listar() {
    return this.http.get(this.apiUrl);
  }

  crear(producto: any) {
    return this.http.post(this.apiUrl, producto);
  }

  buscarPorId(id: number) {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  actualizar(id: number, producto: any) {
    return this.http.put(`${this.apiUrl}/${id}`, producto);
  }

  eliminar(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  listarProveedores() {
    return this.http.get(`${this.apiUrl}/proveedores`);
  }

  listarBajoStock(limite: number) {
    return this.http.get(`${this.apiUrl}/bajo-stock/${limite}`);
  }

  registrarEntrada(data: any) {
    return this.http.post(`${this.movimientosUrl}/entrada`, data);
  }

  registrarSalida(data: any) {
    return this.http.post(`${this.movimientosUrl}/salida`, data);
  }

  listarMovimientos() {
    return this.http.get(this.movimientosUrl);
  }

  listarMovimientosPorProducto(productoId: number) {
    return this.http.get(`${this.movimientosUrl}/producto/${productoId}`);
  }
}
