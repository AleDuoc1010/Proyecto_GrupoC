import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="main-wrapper">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-12 col-md-8 col-lg-6 glass-card shadow-lg bg-white p-4">
            <div class="text-center mb-4">
              <h2 class="fw-bold" style="color: var(--pet-brown);">Registrarme</h2>
              <p class="text-muted">Crea tu cuenta para reportar mascotas y recibir alertas.</p>
            </div>
            <form (ngSubmit)="onSubmit()">
              <div class="mb-3">
                <label class="form-label">Nombre completo</label>
                <input type="text" class="form-control rounded-pill" [(ngModel)]="user.name" name="name" placeholder="Tu nombre completo" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Correo electrónico</label>
                <input type="email" class="form-control rounded-pill" [(ngModel)]="user.email" name="email" placeholder="correo@ejemplo.com" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Contraseña</label>
                <input type="password" class="form-control rounded-pill" [(ngModel)]="user.password" name="password" placeholder="Crea una contraseña" required />
              </div>
              <button type="submit" class="btn btn-pet w-100">Crear cuenta</button>
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
export class RegisterComponent {
  user = { name: '', email: '', password: '' };

  onSubmit() {
    console.log('Registrar cuenta', this.user);
  }
}
