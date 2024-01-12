import { Component, OnInit,Output, EventEmitter, Input } from '@angular/core';
import { UserResponse } from '@app/store/user';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  /* ES UN EVENTO QUE SE UTILIZA EN EL COMPONENTE PADRE, EN app.component.html */
  @Output() menuToggle = new EventEmitter<void>();
  @Input() user !: UserResponse | null;
  @Input() isAuthorized !: boolean | null;
  /* SE TRATA DE UN EVENTO QUE DESENCADENA UNA ACCIÓN */
  @Output() signOut = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  onMenuToggleDispatched():void{
    this.menuToggle.emit();
  }

/* METODO PARA REDICCIONAR LA LÓGICA HACIA EL COMPONENTE PADRE */
  onSignOut(): void{
    this.signOut.emit();
  }



}
