// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  urlBase : `https://orange-2df8c-default-rtdb.firebaseio.com/Storage`,
  urlStorageBase : `https://orange-2df8c-default-rtdb.firebaseio.com/`,
  urlBaseDeleteStorage :`https://orange-2df8c-default-rtdb.firebaseio.com/Storage`,
  baseStorageRef :`/Storage.json`,
  apikey:`AIzaSyDn0_tRQCthFh177gK5QikOQjiQXVnQEdU`,
  baseUsersRef : `Users.json`,
  urlUserBase : `https://kanbanfire-9ae89-default-rtdb.firebaseio.com/`,
  urlBaseDeleteUsers : `https://kanbanfire-9ae89-default-rtdb.firebaseio.com/Users`,
  urlAskey:'abcdef0123456789',
  urlAsIv:'0123456789abcdef'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
