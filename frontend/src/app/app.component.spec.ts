import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, Router } from '@angular/router';
import { AppComponent } from './app.component';

describe('AppComponent RF-001/RF-011', () => {
  let fixture: ComponentFixture<AppComponent>;
  beforeEach(async () => { await TestBed.configureTestingModule({ imports: [AppComponent], providers: [provideRouter([])] }).compileComponents(); fixture = TestBed.createComponent(AppComponent); fixture.detectChanges(); });
  it('muestra las tres áreas principales y navegación accesible', () => { const text = fixture.nativeElement.textContent; expect(text).toContain('Usuarios'); expect(text).toContain('Departamentos'); expect(text).toContain('Relaciones'); expect(fixture.nativeElement.querySelector('nav[aria-label="Navegación principal"]')).not.toBeNull(); });
});
