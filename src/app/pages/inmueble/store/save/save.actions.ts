import { Action } from "@ngrx/store";
import { InmuebleCreateRequest, InmuebleResponse } from "./save.models";

/* ESTADOS DE OPERACIÓN PARA CREAR UN INMUEBLE */

export enum Types{
  CREATE = '[Inmueble] Create: Start',
  CREATE_SUCCESS = '[Inmueble] Create: Success',
  CREATE_ERROR = '[Inmueble] Create: Error',


  READ = '[Inmueble] Read',
  READ_SUCCESS = '[Inmueble] Read: Success',
  READ_ERROR = '[Inmueble] Read: Error',

}

export class Read implements Action{
  readonly type = Types.READ;
  constructor(){}
}

/* MUESTRA UN ARRAY DEL RESPONSE */
export class ReadSuccess implements Action{
  readonly type = Types.READ_SUCCESS;
  constructor(public inmuebles: InmuebleResponse[]){}
}

export class ReadError implements Action{
  readonly type = Types.READ_ERROR;
  constructor(public error: string){}
}

export class Create implements Action{
  readonly type = Types.CREATE;
  constructor(public inmueble: InmuebleCreateRequest){}
}

export class CreateSuccess implements Action{
    readonly type = Types.CREATE_SUCCESS;
    constructor(public inmueble: InmuebleResponse){}
}

export class CreateError implements Action{
  readonly type = Types.CREATE_ERROR;
  constructor(public error: string){}
}

export type All = Create | CreateSuccess | CreateError | Read | ReadSuccess | ReadError;


