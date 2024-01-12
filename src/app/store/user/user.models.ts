import {User} from '@app/models/backend/user'

/* CREA UNA INTERFACE MODELO UserResponse de User */
/* Objeto Response */
export{User as UserResponse} from '@app/models/backend/user'

/* PUEDE HACER LA TRANSACCION DEL LOGIN */
/* Objeto Request */
export interface EmailPasswordCredentials{
  username: string;
  password: string
}


/*  */
export interface UserRequest extends User{
  password: string
}


/* IGNORA LA PROPIEDAD token DESDE EL MODELO USER, YA QUE NO ES NECESARIO UN TOKEN PARA CADA
REGISTRO */
export type UserCreateRequest = Omit<UserRequest, 'token' | 'id'>;
