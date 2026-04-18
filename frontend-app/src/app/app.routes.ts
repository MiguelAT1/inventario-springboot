import { Routes } from '@angular/router';
import { ProductosComponent } from './productos/productos.component';
import { ListaProductosComponent } from './listaproductos/lista-productos.component';

export const routes: Routes = [
  { path: '', component: ListaProductosComponent },
  { path: 'nuevo', component: ProductosComponent },
  { path: 'nuevo/:id', component: ProductosComponent }
];