import { Component, OnInit } from '@angular/core';
import * as fromRoot from '@app/store';
import * as fromList from '../../store/save'
import { Observable } from 'rxjs';
import {InmuebleResponse} from '../../store/save'
import { Store, select } from '@ngrx/store';

@Component({
  selector: 'app-inmueble-list',
  templateUrl: './inmueble-list.component.html',
  styleUrls: ['./inmueble-list.component.scss']
})
export class InmuebleListComponent implements OnInit {

  inmuebles$ !: Observable<InmuebleResponse[] | null>
  loading$ !: Observable<boolean | null>

  pictureDefault : string = "https://cdn.dribbble.com/users/683081/screenshots/2728654/media/7bb2b47d0574d39b20354620e4fa50c8.png"


  constructor(
    private store: Store<fromRoot.State>
  ) { }

  ngOnInit(): void {
    /* inicia la secuencia para consultar los datos del servidor */
    this.store.dispatch(new fromList.Read())
    this.loading$ = this.store.pipe(select(fromList.getLoading));
    this.inmuebles$ = this.store.pipe(select(fromList.getInmuebles));
  }

}
