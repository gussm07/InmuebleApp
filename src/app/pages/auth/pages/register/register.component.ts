import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import * as fromRoot from '@app/store';
import * as fromUser from '@app/store/user';
import { Store, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NotificationService } from '@app/services';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  loading$! : Observable<boolean | null>

  constructor(
    private store: Store<fromRoot.State>,
    private notification : NotificationService
  ) { }

  ngOnInit(): void {

    this.loading$ = this.store.pipe(select(fromUser.getLoading))

  }

  registrarUsuario(form: NgForm):void{
    try {
      if(form.valid){
        const userCreateRequest : fromUser.UserCreateRequest = {
          nombre: form.value.nombre,
          apellido_paterno: form.value.apellido_paterno,
          telefono: form.value.telefono,
          username: form.value.username,
          email: form.value.email,
          password: form.value.password,
          apellido_materno: form.value.apellido_materno
        }

        this.store.dispatch(new fromUser.SignUpEmail(userCreateRequest))
        this.notification.success("Registro exitoso!")
        console.log(userCreateRequest)

      }
    } catch (error) {
      console.error("Ha ocurrido un problema al registrar usuario")
      this.notification.error("Ha ocurrido un error al registrar")
    }

  }

}
