import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_PATHS } from '../app.config';

export interface ProductoDTO {
  id: number;
  nombre: string;
  stock: number;
  precio: number;
  estado: string;
  ubicacion: string;
  categoria: string;
  proveedor: string;
}

export interface ProductoDetalle {
  id: number;
  nombre: string;
  stock: number;
  precio: number;
  estado: string;
  ubicacion: string;
  categoria: { id: number; nombre: string } | null;
  proveedor: { id: number; nombre: string } | null;
}

export interface ProductoPayload {
  nombre: string;
  stock: number;
  precio: number;
  estado: string;
  ubicacion: string;
  categoria: { id: number };
  proveedor: { id: number };
}

@Injectable({ providedIn: 'root' })
export class ProductoService {
  constructor(private http: HttpClient) {}

  listar(): Observable<ProductoDTO[]> {
    return this.http.get<ProductoDTO[]>(API_PATHS.productos);
  }

  buscar(id: number): Observable<ProductoDetalle> {
    return this.http.get<ProductoDetalle>(`${API_PATHS.productos}/${id}`);
  }

  crear(producto: ProductoPayload): Observable<ProductoDTO> {
    return this.http.post<ProductoDTO>(API_PATHS.productos, producto);
  }

  actualizar(id: number, producto: ProductoPayload): Observable<ProductoDetalle> {
    return this.http.put<ProductoDetalle>(`${API_PATHS.productos}/${id}`, producto);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${API_PATHS.productos}/${id}`);
  }
}
