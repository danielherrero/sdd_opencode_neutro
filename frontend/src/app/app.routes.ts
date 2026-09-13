import { Routes } from '@angular/router';

const resource = () => import('./resource.page').then(m => m.ResourcePage);
export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'users' },
  { path: 'users', children: [{ path: '', loadComponent: resource }, { path: 'new', loadComponent: resource }, { path: ':id', loadComponent: resource }, { path: ':id/edit', loadComponent: resource }] },
  { path: 'departments', children: [{ path: '', loadComponent: resource }, { path: 'new', loadComponent: resource }, { path: ':id', loadComponent: resource }, { path: ':id/edit', loadComponent: resource }] },
  { path: 'user-departments', children: [{ path: '', loadComponent: resource }, { path: 'new', loadComponent: resource }, { path: ':id', loadComponent: resource }, { path: ':id/edit', loadComponent: resource }] },
  { path: '**', redirectTo: 'users' }
];
