import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { UserApi } from './api';
import { environment } from '../environments/environment';

describe('UserApi RF-003/RF-012', () => {
  let api: UserApi; let http: HttpTestingController;
  beforeEach(() => { TestBed.configureTestingModule({ providers: [UserApi, provideHttpClient(), provideHttpClientTesting()] }); api = TestBed.inject(UserApi); http = TestBed.inject(HttpTestingController); });
  afterEach(() => http.verify());
  it('consulta usuarios usando la URL del environment y conserva el método GET', () => { api.list().subscribe(value => expect(value).toEqual([])); const request = http.expectOne(`${environment.apiUrl}/users`); expect(request.request.method).toBe('GET'); request.flush([]); });
});
