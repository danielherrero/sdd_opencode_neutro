import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { ResourcePage } from './resource.page';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ResourcePage RF-008/RF-009', () => {
  let fixture: ComponentFixture<ResourcePage>;
  beforeEach(async () => { await TestBed.configureTestingModule({ imports: [ResourcePage], providers: [provideRouter([{ path: '**', component: ResourcePage }]), provideHttpClient(), provideHttpClientTesting()] }).compileComponents(); fixture = TestBed.createComponent(ResourcePage); fixture.detectChanges(); });
  it('mantiene el formulario invalidado hasta completar los campos obligatorios', () => { expect(fixture.componentInstance.form.valid).toBeFalse(); fixture.componentInstance.form.patchValue({ nombre: 'Ana', apellidos: 'López', fechaNacimiento: '1990-01-01', contrasena: 'secret' }); expect(fixture.componentInstance.form.valid).toBeTrue(); });
});
