import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_PATHS } from '../app.config';

export interface MovimientoForm {
  productoId: number;
  cantidad: number;
  observacion: string;
}

export interface MovimientoDTO {
  id: number;
  tipo: string;
  cantidad: number;
  fecha: string;
  observacion: string;
  producto: { id: number; nombre: string };
}

@Injectable({ providedIn: 'root' })
export class MovimientoService {
  constructor(private http: HttpClient) {}

  listar(): Observable<MovimientoDTO[]> {
    return this.http.get<MovimientoDTO[]>(API_PATHS.movimientos);
  }

  entrada(payload: MovimientoForm): Observable<MovimientoDTO> {
    return this.http.post<MovimientoDTO>(`${API_PATHS.movimientos}/entrada`, payload);
  }

  salida(payload: MovimientoForm): Observable<MovimientoDTO> {
    return this.http.post<MovimientoDTO>(`${API_PATHS.movimientos}/salida`, payload);
  }
}
