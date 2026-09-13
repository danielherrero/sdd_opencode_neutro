import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Department, User, UserDepartment } from './models';

const API_URL = 'http://localhost:8080';

@Injectable({ providedIn: 'root' })
export class ResourceApi<T> {
  protected readonly http = inject(HttpClient);
  protected readonly path: string;
  constructor(path: string) { this.path = path; }
  list(): Observable<T[]> { return this.http.get<T[]>(`${API_URL}/${this.path}`); }
  get(id: number): Observable<T> { return this.http.get<T>(`${API_URL}/${this.path}/${id}`); }
  create(value: Partial<T>): Observable<T> { return this.http.post<T>(`${API_URL}/${this.path}`, value); }
  update(id: number, value: Partial<T>): Observable<T> { return this.http.put<T>(`${API_URL}/${this.path}/${id}`, value); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${API_URL}/${this.path}/${id}`); }
}
@Injectable({ providedIn: 'root' }) export class UserApi extends ResourceApi<User> { constructor() { super('users'); } }
@Injectable({ providedIn: 'root' }) export class DepartmentApi extends ResourceApi<Department> { constructor() { super('departments'); } }
@Injectable({ providedIn: 'root' }) export class UserDepartmentApi extends ResourceApi<UserDepartment> { constructor() { super('user-departments'); } }
