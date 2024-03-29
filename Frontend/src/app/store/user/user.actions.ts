import { Action } from "@ngrx/store";
import { EmailPasswordCredentials, UserCreateRequest, UserResponse } from "./user.models";


/* SABER LOS ESTADOS DEL USUARIO */
export enum Types{
  INIT = '[User] Init: Start',
  INIT_AUTHORIZED = '[User] Init: Authorized',
  INIT_UNAUTHORIZED = '[User] Init: Unauthorized',
  INIT_ERROR = '[User] Init: Error',


  SIGN_IN_EMAIL = '[User] Login: Start',
  SIGN_IN_EMAIL_SUCCESS = '[User] Login: Success',
  SIGN_IN_EMAIL_ERROR = '[User] Login: Error',


  SIGN_UP_EMAIL = '[User] Registrar Usuario con Email: Start',
  SIGN_UP_EMAIL_SUCCESS = '[User] Registrar Usuario con Email: Success',
  SIGN_UP_EMAIL_ERROR = '[User] Registrar Usuario con Email: Error',



  SIGN_OUT_EMAIL = '[User] Logout: Start',
  SIGN_OUT_EMAIL_SUCCESS = '[User] Logout: Success',
  SIGN_OUT_EMAIL_ERROR = '[User] Logout: Error',




}


//PERMITE SABER SI UN USUARIO ESTA EN SESION
export class Init implements Action{
  readonly type = Types.INIT;
  constructor(){}
}

export class InitAuthorized implements Action{
  readonly type = Types.INIT_AUTHORIZED;
  constructor(public username: string, public user:UserResponse | null){}
}


export class InitUnauthorized implements Action{
  readonly type = Types.INIT_UNAUTHORIZED
  constructor(){}
}

export class InitError implements Action{
  readonly type = Types.INIT_ERROR;
  constructor(public error:string){}
}

//LOGIN

export class SignInEmail implements Action{
  readonly type = Types.SIGN_IN_EMAIL
  constructor(public credentials: EmailPasswordCredentials){}
}


export class SignInEmailSuccess implements Action{
  readonly type = Types.SIGN_IN_EMAIL_SUCCESS
  constructor(public username: string, public user:UserResponse){}
}

export class SignInEmailError implements Action{
  readonly type = Types.SIGN_IN_EMAIL_ERROR
  constructor(public error:string){}
}


//SIGN UP O REGISTRO DE USUARIOS
export class SignUpEmail implements Action{
  readonly type = Types.SIGN_UP_EMAIL;
  constructor(public user: UserCreateRequest){}
}

export class SignUpEmailSuccess implements Action{
  readonly type = Types.SIGN_UP_EMAIL_SUCCESS;
  constructor(public username: string, public user: UserResponse | null){}
}

export class SignUpEmailError implements Action{
  readonly type = Types.SIGN_UP_EMAIL_ERROR;
  constructor(public error: string){}
}


/* SALIR DE SESION */
export class SignOut implements Action{
  readonly type = Types.SIGN_OUT_EMAIL
  constructor(){}
}

export class SignOutSuccess implements Action{
  readonly type = Types.SIGN_OUT_EMAIL_SUCCESS
  constructor(){}
}

export class SignOutError implements Action{
  readonly type = Types.SIGN_OUT_EMAIL_ERROR
  constructor(public error: string){}
}


/* PERMITE EL ACCESO DE ESTOS DATOS */
export type All = Init | InitAuthorized | InitUnauthorized | InitError
                  | SignInEmail | SignInEmailSuccess | SignInEmailError
                  | SignUpEmail | SignUpEmailSuccess | SignUpEmailError
                  | SignOut | SignOutSuccess | SignOutError;
