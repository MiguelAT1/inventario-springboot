import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  template: `
    <div class="app-shell">
      <nav *ngIf="isLoggedIn">
        <div>
          <a routerLink="/productos" routerLinkActive="active">Productos</a>
          <a routerLink="/movimientos" routerLinkActive="active">Movimientos</a>
        </div>
        <button (click)="logout()">Logout</button>
      </nav>
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {
  isLoggedIn = false;

  constructor(private authService: AuthService, private router: Router) {
    this.authService.token$.subscribe(token => {
      this.isLoggedIn = !!token;
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
