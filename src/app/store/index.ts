import {ActionReducerMap} from '@ngrx/store'
import * as fromUser from './user'

export interface State {
  /* fromUser viene del reducer y es el que mantiene la data y el modelo del usuario */
  user: fromUser.UserState
}

export const reducers: ActionReducerMap<State> = {
  user: fromUser.reducer
}

export const effects = [
  fromUser.UserEffects
]
