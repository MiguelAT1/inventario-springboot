import { Routes } from '@angular/router';
import { ProductosComponent } from './productos/productos.component';
import { MovimientosComponent } from './movimientos/movimientos.component';
import { LoginComponent } from './login/login.component';
import { authGuard, adminGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'productos', component: ProductosComponent, canActivate: [authGuard] },
  { path: 'movimientos', component: MovimientosComponent, canActivate: [adminGuard] },
  { path: '**', redirectTo: 'login' }
];
