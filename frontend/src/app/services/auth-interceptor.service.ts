import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'  // Torna o interceptor disponível em toda a aplicação
})
export class AuthInterceptor implements HttpInterceptor {

  // Método intercept: intercepta todas as requisições HTTP antes que elas sejam enviadas
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    // Recupera o token JWT armazenado no localStorage
    const token = localStorage.getItem('jwtToken');

    // Se o token existir, clona a requisição original e adiciona o cabeçalho Authorization
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`  // Adiciona o Bearer Token no cabeçalho
        }
      });
    }

    // Passa a requisição (com ou sem o token) para o próximo handler na cadeia
    return next.handle(request);
  }
}
