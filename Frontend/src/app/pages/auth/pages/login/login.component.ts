import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import * as fromRoot from '@app/store';
import * as fromUser from '@app/store/user';
import { Store } from '@ngrx/store';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loading$ !: Observable<boolean | null>

  constructor(
    /* Contiene la data del usuario dentro del localstorage */
    private store : Store<fromRoot.State>
  ) { }

  ngOnInit(): void {
  }

  loginUsuario(form: NgForm):void{
    const userLoginRequest : fromUser.EmailPasswordCredentials = {
      username : form.value.username,
      password : form.value.password

    }
    /* LLAMA AL METODO QUE HACE EL LOGIN */
    this.store.dispatch(new fromUser.SignInEmail(userLoginRequest));


  }

}
