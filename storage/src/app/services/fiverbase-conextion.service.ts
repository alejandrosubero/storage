import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Data } from '../model/data.model';
import { NewUser } from '../model/newUser.model';

@Injectable({
  providedIn: 'root'
})
export class FiverbaseConextionService {

  testProcessedOrders: Data[] = [];


  constructor(private http: HttpClient) { }

  urlBase = `https://orange-2df8c-default-rtdb.firebaseio.com/Storage`;
  urlStorageBase = `https://orange-2df8c-default-rtdb.firebaseio.com/`;
  urlBaseDeleteStorage = `https://orange-2df8c-default-rtdb.firebaseio.com/Storage`;
  baseStorageRef = `/Storage.json`;


  // ejemplos:
  //     curl 'https://docs-examples.firebaseio.com/fireblog/posts.json?print=pretty'
  // Especificar print=silent devuelve un 204 No Content en caso de éxito.
  // curl 'https://docs-examples.firebaseio.com/fireblog/posts.json?print=silent'
  // fuente: https://firebase.google.com/docs/database/rest/retrieve-data






  getAllStorangeData(url: string): void {

    this.http.get(url).subscribe((res) => {
      if (res) {
        const processedOrders: Data[] = [];
        for (const [key, value] of Object.entries(res)) {
          const valset = value as Data;
          valset.key = key;
          processedOrders.push(valset);
        }
        console.log(processedOrders);
        this.testProcessedOrders = processedOrders;
      }
    });
  }


  save(elemet: Data, url: string): Observable<any> {
    return this.http.post(url, elemet);
  }


  getAll(url: string): Observable<any> {
    return this.http.get(url);
  }


  update(elemet: any, url: string): Observable<any> {
    const elemetJSON = JSON.stringify(elemet);
    const objectJson = `{"${elemet.key}":${elemetJSON} }`;
    return this.http.patch(url, objectJson);
  }


  // curl -X DELETE \
  // 'https://docs-examples.firebaseio.com/fireblog/users/key.json'
  delete(elemet: Data, urlDelete: string): Observable<any> {
    const deleteUrl = `${urlDelete}/${elemet.key}.json`;
    return this.http.delete(deleteUrl);
  }

  // update(elemet: Data, url: string): void {
  //   const elemetJSON = JSON.stringify(elemet);
  //   const objectJson = `{"${elemet.key}":${elemetJSON} }`;
  //   this.http.patch(url, objectJson).subscribe(res => {
  //     console.log('patchResponse: ', res);
  //   });
  // }

  // delete(elemet: Data, urlDelete: string): void {
  //   const deleteUrl = `${urlDelete}/${elemet.key}.json`;
  //   this.http.delete(deleteUrl).subscribe(deleteResponse => {
  //     console.log('deleteResponse: ', deleteResponse);
  //   });
  // }




}
