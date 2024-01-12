import { createSelector, createFeatureSelector } from "@ngrx/store";
/* OBTIENE LA DATA DEL LOCALSTORAGE */
import { UserState } from "./user.reducer";


export const getUserState = createFeatureSelector<UserState>('user');

export const getUser = createSelector(
  getUserState,
  /* entity representa la data almacenada en localstorage */
  (state) => state.entity
)

export const getLoading = createSelector(
  getUserState,
  (state) => state.loading
)

export const getIsAuthorized = createSelector(
  getUserState,
  /* para ver si existe el usuario y retornar true o false */
  (state) => !!state.username

)


