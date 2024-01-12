export interface User{
  email: string,
  username: string,
  token:string
  nombre: string,
  apellido_paterno: string,
  apellido_materno: string,
  telefono: string,
}

interface Token{
  refresh: string,
  access:string
}
