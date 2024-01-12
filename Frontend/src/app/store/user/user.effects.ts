import { Injectable } from '@angular/core';
import * as fromActions from './user.actions'
import { HttpClient } from '@angular/common/http';
import { Actions, createEffect } from '@ngrx/effects';
import { NotificationService } from '@app/services';
import { Router } from '@angular/router';
import {Observable, of} from 'rxjs'
import {catchError, map,switchMap,tap} from 'rxjs/operators'
import { UserResponse } from './user.models';
import { environment } from 'environments/environment';
import { ofType } from '@ngrx/effects';

/* PARA DISPARAR EL EFFECT DEBEMOS DE OCUPAR EL ACTION */
type Action = fromActions.All;

@Injectable()
export class UserEffects{

  username:string | null | undefined;

  constructor(
    private httpClient: HttpClient,
    private actions: Actions,
    private notifications: NotificationService,
    private router: Router
    ){}

    signUpEmail: Observable<Action> = createEffect(() =>
      this.actions.pipe(
        ofType(fromActions.Types.SIGN_UP_EMAIL),
        //DEVUELVE LA DATA DEL USUARIO QUE TIENE QUE DAR DE ALTA Y REGISTRAR EN BACKEND
        map((action: fromActions.SignUpEmail) => action.user),
        switchMap(userData =>
          this.httpClient.post<UserResponse>(`${environment.url}api/authentication/sign-up`,userData)
          /* EVALUAR EL POSIBLE RESULTADO LUEGO DE EVALUAR LA LLAMADA DE LA URL AL BACKEND */
          .pipe(
            /* REGISTRA EN EL NAVEGADOR EL TOKEN */
            tap((response: UserResponse) =>{
              localStorage.setItem('token', response.token);
              this.router.navigate(['/'])
            }),
            /* LA RESPUESTA SATISFACTORIA DEL MAP, SIEMPRE REGRESA EL OBSERVABLE, CASO CONTRARIO DE EL ERROR
            QUE VA ENVUELTO DE UN of */
            map((response:UserResponse) => new fromActions.SignUpEmailSuccess(response.username, response || null)),
            catchError(err =>{
              this.notifications.error("Errores al registrar un nuevo usuario");
              /* DEVUELVE UN OBSERVABLE CON of */
              return of(new fromActions.SignUpEmailError(err.message));
            })
          )
        )
      )
  );



  signInEmail: Observable<Action> = createEffect(() =>
  this.actions.pipe(
    ofType(fromActions.Types.SIGN_IN_EMAIL),
    //AQUI SE ENCUENTRA EL USUARIO Y EL PASSWORD PARA HACER LOGIN en action.credential
    map((action: fromActions.SignInEmail) => action.credentials),
    switchMap(credentials =>
      //LINEA DE LLAMADA AL SERVIDOR CON URL Y PARAMETROS, Usuario y contraseña alojados en userData
      this.httpClient.post<UserResponse>(`${environment.url}api/authentication/sign-in`,credentials)
      /* EVALUAR EL POSIBLE RESULTADO LUEGO DE EVALUAR LA LLAMADA DE LA URL AL BACKEND */
      .pipe(
        /* REGISTRA EN EL NAVEGADOR EL TOKEN */
        tap((response: UserResponse) =>{
          /* response trae toda la información del usuario */
          localStorage.setItem('token', response.token);
          console.log(response)
          this.notifications.success("Bienvenido" + ` ${response.username}!`)
          this.router.navigate(['/'])
        }),
        /* LA RESPUESTA SATISFACTORIA DEL MAP, SIEMPRE REGRESA EL OBSERVABLE, CASO CONTRARIO DE EL ERROR
        QUE VA ENVUELTO DE UN of */
        map((response:UserResponse) => new fromActions.SignInEmailSuccess(response.username, response || null)),
        catchError(err =>{
          this.notifications.error("Error de autentificación");
          /* DEVUELVE UN OBSERVABLE CON of */
          return of(new fromActions.SignInEmailError(err.message));
        })
      )
    )
  )
);


//SE EJECUTA CUANDO SE HAGA EL REFRESH AL NAVEGADOR
init: Observable<Action> = createEffect(() =>
this.actions.pipe(
  ofType(fromActions.Types.INIT),
  /* TRAE LA INFORMACIÓN DESDE EL LOCALSTORAGE UNA VEZ VALIDADO EL LOGIN  */
  switchMap(async () => localStorage.getItem('token')),
  switchMap(token => {
    if(token){
      return this.httpClient.get<UserResponse>(`${environment.url}api/user`)
    .pipe(
      tap((response: UserResponse) =>{
        console.log('Data del usuario que viene del servidor', response)
      }),
      /* LA RESPUESTA SATISFACTORIA DEL MAP, SIEMPRE REGRESA EL OBSERVABLE, CASO CONTRARIO DE EL ERROR
      QUE VA ENVUELTO DE UN of */
      map((response:UserResponse) => new fromActions.InitAuthorized(response.username, response || null)),
      catchError(err =>{
        /* DEVUELVE UN OBSERVABLE CON of */
        return of(new fromActions.InitError(err.message));
      })
    )
    }else{
      return of(new fromActions.InitUnauthorized());
    }

  }))
);






}
