// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  name:"dev",
  firebase:{
    config : {
      apiKey: "AIzaSyBNkdWO4iDKxexUPtZp7Tee6oP9A5JDU-c",
      authDomain: "edificacion-app-24956.firebaseapp.com",
      projectId: "edificacion-app-24956",
      storageBucket: "edificacion-app-24956.appspot.com",
      messagingSenderId: "577431054167",
      appId: "1:577431054167:web:10f2943c376fb30708c012"
    }
  },
  /* APUNTA A LA RUTA DEL msApiGateway que está en Eureka*/
  url:'http://localhost:5555/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
