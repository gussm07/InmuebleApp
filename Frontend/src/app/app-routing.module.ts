import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {/* OBTIENE ACCESO A LAS PAGINAS ESTATICAS */
    path:'',
    children:[
      {
        path: 'inmueble',
        loadChildren: () => import('./pages/inmueble/inmueble.module').then(m=>m.InmuebleModule)
      },
      {
        path: 'auth',
        loadChildren: () => import('./pages/auth/auth.module').then(m=>m.AuthModule)
      },
      {
        path: 'static',
        loadChildren: () => import('./pages/static/static.module').then(m=>m.StaticModule)
      },
      {/* CUANDO SE HACE EL LOGIN EXITOSAMENTE */
        path: '',
        pathMatch: 'full',
        redirectTo: 'static/welcome'
      }
    ]
  },
  /* CUANDO NO EXISTAN RUTAS DISPONIBLES */
  {
    path: '**',
    pathMatch:'full',
    redirectTo:'static/404'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
