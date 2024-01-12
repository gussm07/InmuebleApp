import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-menu-list',
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.scss']
})
export class MenuListComponent implements OnInit {

  @Output() menuToggle = new EventEmitter<void>();

  /* RECIBE EL PARAMETRO SI ESTA LOGGEADO */
  @Input() isAuthorized !: boolean | null;
  /* ENVIA EL PARAMETRO PARA SABER SI YA SALIO DE SESIÓN */
  @Output() signOut = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  closeMenu():void{
    this.menuToggle.emit();
  }

  onSignOut():void{
    this.signOut.emit();
  }

}
