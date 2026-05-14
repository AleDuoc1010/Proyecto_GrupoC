import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="main-wrapper animated-bg-gradient">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-12 col-md-8 col-lg-6 glass-card shadow-lg bg-white p-4 fade-in-scale">
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
  `,
  styles: [`
    .animated-bg-gradient {
      background: linear-gradient(90deg, #faf0e6, #f5f5dc, #faf0e6);
      background-size: 200% 200%;
      animation: gradientMove 5s ease infinite;
    }
    @keyframes gradientMove {
      0% { background-position: 0% 50%; }
      50% { background-position: 100% 50%; }
      100% { background-position: 0% 50%; }
    }
    .fade-in-scale {
      animation: scaleIn 1s ease-out;
    }
    @keyframes scaleIn {
      from { transform: scale(0.9); opacity: 0; }
      to { transform: scale(1); opacity: 1; }
    }
  `]
})
export class RegisterComponent {
  user = { name: '', email: '', password: '' };

  onSubmit() {
    console.log('Registrar cuenta', this.user);
  }
}
