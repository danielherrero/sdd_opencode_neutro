export interface User { id: number; nombre: string; apellidos: string; fechaNacimiento: string; }
export interface Department { id: number; nombre: string; descripcion?: string; fechaCreacion: string; }
export interface UserDepartment { id: number; userId: number; departmentId: number; fechaCreacion: string; }
export interface ApiError { message?: string; fieldErrors?: Record<string, string>; }
