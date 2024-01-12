import {UserResponse} from './user.models';
import * as fromActions from './user.actions';

export interface UserState {
  entity : UserResponse | null;
  id : string | null;
  username : string | null;
  loading: boolean | null;
  error : string | null;
}


const initialState : UserState = {
  entity: null,
  id: null,
  username: null,
  loading: null,
  error: null
}

export function reducer(state = initialState, action: fromActions.All | any) : UserState {

    switch(action.type) {
        //init
        case fromActions.Types.INIT: {
          return {...state, loading: true};
        }

        case fromActions.Types.INIT_AUTHORIZED: {
          return {...state, loading: false, entity: action.user, username: action.username, error: null};
        }

        case fromActions.Types.INIT_UNAUTHORIZED: {
          return {...state, loading: false, entity: null, username: null, error: null};
        }

        case fromActions.Types.INIT_ERROR: {
          return {...state, loading: false, entity: null, username: null, error: action.error};
        }

        //login
        case fromActions.Types.SIGN_IN_EMAIL: {
          return {...state, loading: true, entity: null, username: null, error: null};
        }

        case fromActions.Types.SIGN_IN_EMAIL_SUCCESS: {
          return {...state, loading: false, entity: action.user, username: action.username, error: null};
        }

        case fromActions.Types.SIGN_IN_EMAIL_ERROR: {
          return {...state, loading: false, entity: null, username: null, error: action.error};
        }

        //signup o registro de usuarios
        case fromActions.Types.SIGN_UP_EMAIL: {
          return {...state, loading: true, entity: null, username: null, error: null};
        }

        case fromActions.Types.SIGN_UP_EMAIL_SUCCESS: {
          return {...state, loading: false, entity: action.user, username: action.username, error: null};
        }

        case fromActions.Types.SIGN_UP_EMAIL_ERROR: {
          return {...state, loading: false, entity: null, username: null, error: action.error};
        }

        //LOGOUT o Salir de Sesion
        case fromActions.Types.SIGN_OUT_EMAIL: {
          return {...initialState};
        }

        case fromActions.Types.SIGN_OUT_EMAIL_SUCCESS: {
          return {...initialState};
        }

        case fromActions.Types.SIGN_OUT_EMAIL_ERROR: {
          return {...state, loading: false, entity: null, username: null, error: action.error};
        }

        default: {
          return state;
        }
    }
}
