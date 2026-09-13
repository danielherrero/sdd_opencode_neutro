import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({ selector: 'empresa-root', standalone: true, imports: [RouterOutlet, RouterLink, RouterLinkActive, MatSidenavModule, MatToolbarModule, MatButtonModule, MatIconModule], template: `
  <mat-sidenav-container class="app-frame">
    <mat-sidenav #drawer class="side-nav" [mode]="mobile() ? 'over' : 'side'" [opened]="!mobile()">
      <div class="brand"><span class="brand-mark">E</span><div><strong>empresa</strong><small>operations desk</small></div></div>
      <p class="nav-caption">Workspace</p>
      <nav aria-label="Navegación principal">
        <a mat-button routerLink="/users" routerLinkActive="active" (click)="mobile() && drawer.close()"><mat-icon>group</mat-icon>Usuarios</a>
        <a mat-button routerLink="/departments" routerLinkActive="active" (click)="mobile() && drawer.close()"><mat-icon>account_tree</mat-icon>Departamentos</a>
        <a mat-button routerLink="/user-departments" routerLinkActive="active" (click)="mobile() && drawer.close()"><mat-icon>hub</mat-icon>Relaciones</a>
      </nav>
      <div class="side-note"><span class="pulse"></span><span>API conectada<br><small>localhost:8080</small></span></div>
    </mat-sidenav>
    <mat-sidenav-content>
      <mat-toolbar class="top-bar"><button mat-icon-button aria-label="Abrir menú" (click)="drawer.toggle()"><mat-icon>menu</mat-icon></button><span class="crumb">Empresa / <strong>Operations</strong></span><span class="toolbar-spacer"></span><span class="status-dot"></span><span class="live-label">Live workspace</span></mat-toolbar>
      <main class="page-content"><router-outlet /></main>
    </mat-sidenav-content>
  </mat-sidenav-container>
`, styles: [`
  .app-frame { height: 100vh; background: var(--paper); } .side-nav { width: 248px; padding: 28px 16px 20px; background: var(--ink); color: #d7e0ec; border: 0; } .brand { display:flex; align-items:center; gap:12px; padding: 0 10px 42px; color:#fff; } .brand strong { display:block; font: 700 21px var(--display); letter-spacing:-.04em; } .brand small { color:#8291a5; font-size:10px; letter-spacing:.08em; text-transform:uppercase; } .brand-mark { display:grid; place-items:center; width:34px; height:34px; background:var(--lime); color:var(--ink); font:700 22px var(--display); border-radius:10px 2px 10px 2px; } .nav-caption { padding:0 12px; color:#718096; font-size:10px; letter-spacing:.16em; text-transform:uppercase; } nav a { width:100%; justify-content:flex-start; gap:12px; margin:5px 0; color:#aebdce; border-radius:8px; } nav a.active { color:var(--ink); background:var(--lime); } nav a mat-icon { font-size:19px; } .side-note { position:absolute; bottom:24px; left:26px; display:flex; gap:9px; color:#aebdce; font-size:11px; line-height:1.5; } .side-note small { color:#718096; } .pulse,.status-dot { display:inline-block; width:7px; height:7px; margin-top:4px; border-radius:50%; background:#9ee85b; box-shadow:0 0 0 4px rgba(158,232,91,.13); } .top-bar { height:72px; padding:0 32px; background:var(--paper); color:var(--ink); border-bottom:1px solid #dfe4e8; } .top-bar button { display:none; } .crumb { font-size:13px; color:#778293; } .crumb strong { color:var(--ink); } .toolbar-spacer { flex:1; } .live-label { margin-left:10px; font-size:11px; color:#667284; } .page-content { min-height:calc(100vh - 72px); padding: 42px clamp(22px, 5vw, 72px); } @media (max-width: 800px) { .side-nav { width:280px; } .top-bar { padding:0 16px; } .top-bar button { display:inline-flex; margin-right:10px; } .page-content { padding:28px 18px; } .live-label { display:none; } }
`] })
export class AppComponent { readonly mobile = signal(typeof window !== 'undefined' && window.innerWidth <= 800); }
