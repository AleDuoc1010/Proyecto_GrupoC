import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="main-wrapper">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-12 col-md-8 col-lg-6 glass-card shadow-lg bg-white p-4">
            <div class="text-center mb-4">
              <h2 class="fw-bold" style="color: var(--pet-brown);">Ingresar</h2>
              <p class="text-muted">Accede a tu cuenta para gestionar reportes y mascotas encontradas.</p>
            </div>
            <form (ngSubmit)="onSubmit()">
              <div class="mb-3">
                <label class="form-label">Correo electrónico</label>
                <input type="email" class="form-control rounded-pill" [(ngModel)]="credentials.email" name="email" placeholder="correo@ejemplo.com" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Contraseña</label>
                <input type="password" class="form-control rounded-pill" [(ngModel)]="credentials.password" name="password" placeholder="Tu contraseña" required />
              </div>
              <button type="submit" class="btn btn-pet w-100">Ingresar</button>
            </form>
            <div class="text-center mt-3">
              <a routerLink="/" style="color: var(--pet-brown);">Volver al inicio</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class LoginComponent {
  credentials = { email: '', password: '' };

  onSubmit() {
    // Aquí puedes agregar la lógica de envío real cuando tengas la API lista.
    console.log('Iniciar sesión con', this.credentials);
  }
}
