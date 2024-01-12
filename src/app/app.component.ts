import { Component, OnInit } from '@angular/core';
import { AngularFirestore } from '@angular/fire/compat/firestore';
import { NotificationService } from '@app/services';
import * as fromRoot from './store';
import * as fromUser from './store/user';
import { Store, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';




@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

  showSpinner = false;
  title = 'cli-inmueble-app';
  /* si son de tipo observable, deben de ir con signo de dolar y inicializarlo es ponerle el ! */
  user$! : Observable<fromUser.UserResponse>;
  isAuthorized$! : Observable<boolean>;



  constructor(private fs:AngularFirestore,
              private notification: NotificationService,
              /* OBTIENE DATOS DE LOCAL STORAGE */
              private store : Store<fromRoot.State>,
              private router : Router
              ){}

  ngOnInit(){
    /* this.fs.collection("test").stateChanges().subscribe(personas => {
      console.log(personas.map(x=>x.payload.doc.data()))
    }) */


    /* busca dentro del storage global de angular el usuario y devuelve como tipo observable de tipo user */
    this.user$ = this.store.pipe(select(fromUser.getUser)) as Observable<fromUser.UserResponse>;
    this.isAuthorized$ = this.store.pipe(select(fromUser.getIsAuthorized)) as Observable<boolean>;

    /* LOGICA PARA MANTENER SESIÓN GLOBAL */
    /* INTERCEPTA EL TOKEN Y LO MANDA EN UN REQUEST CADA QUE SE HACE
    REFRESH EN EL NAVEGADOR */
    this.store.dispatch(new fromUser.Init());
  }



  onToggleSpinner() : void{
    this.showSpinner = !this.showSpinner;
  }


  onFilesChanged(urls: string | string[]): void{
    console.log('urls', urls)
  }


  onSuccess():void{
    this.notification.success("¡El procedimiento fue exitoso!")
  }

  onError():void{
    this.notification.error("Se encontraron errores en el proceso")
  }

  onSignOut():void{
    localStorage.removeItem('token')
    this.store.dispatch(new fromUser.SignOut)
    this.router.navigate(['/auth/login'])
  }

}
