import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from '@app/guards/unauth/unauth.guard';

const routes: Routes = [
  {
    path:'login',
    loadChildren: () => import('./pages/login/login.module').then(m=>m.LoginModule),
    /* SI ESTAS EN SESIÓN Y QUIERES INICIAR A LOGIN NO SE PODRÁ VER */
    canActivate: [UnauthGuard]
  },
  {
    path:'register',
    loadChildren: () => import('./pages/register/register.module').then(m=>m.RegisterModule),
    /* SI ESTAS EN SESIÓN Y QUIERES INICIAR A LOGIN NO SE PODRÁ VER */
    canActivate: [UnauthGuard]

  },
  {
    path:'**',
    pathMatch: 'full',
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
