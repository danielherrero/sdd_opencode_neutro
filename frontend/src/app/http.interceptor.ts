import { HttpInterceptorFn } from '@angular/common/http';

/** Logs request metadata in development without serialising credentials. */
export const developmentLogger: HttpInterceptorFn = (request, next) => {
  const safeRequest = { method: request.method, url: request.url, headers: request.headers.keys(), body: request.body ? '[payload omitted]' : null };
  if (typeof ngDevMode !== 'undefined' && ngDevMode) console.debug('[HTTP]', safeRequest);
  return next(request);
};
