import { Injectable } from '@angular/core';
import { LogingResponse } from '../model/loging-response.model';

import { AESEncryptDecryptService } from './aesencrypt-decrypt-service.service';
import { SessionStorageServiceService } from './session-storage-service.service';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  constructor(private encryptService: AESEncryptDecryptService, private storage: SessionStorageServiceService) { }

  set(key: string, data: any): void {
    try {
      this.storage.clear();
      this.storageTheSession(data);
    } catch (e) {
      console.error('Error saving to localStorage', e);
    }
  }


  get(key: string): any {
    try {
      this.storage.checkSeccion();
      return this.storage.getAll();
    } catch (e) {
      console.error('Error getting data from localStorage', e);
      return null;
    }
  }

  removeItem(key: string): void {
    this.storage.remove(key);
  }

  getItem(key: string): any {
    try {
      return this.storage.get(key);
    } catch (e) {
      console.error('Error getting data from localStorage', e);
      return null;
    }
  }

  setItem(key: string, value: any): void {
    this.storage.set(key, value);
  }

  cleanAndRemove(key: string): void {
    this.storage.clear();
  }

  clear(): void {
    this.storage.clear();
  }



  storageTheSession(dataLogin: LogingResponse): void {
    try {
      this.storage.set('status', dataLogin.status);
      this.storage.set('username', dataLogin.username);
      // this.storage.set('authorization', dataLogin.authorization);
      // this.storage.set('token', dataLogin.token);
      // this.storage.set('userCode', dataLogin.userCode);
      // this.storage.set('taxes', dataLogin.taxes);
      // this.storage.set('userImagen', dataLogin.userImagen);
      // this.storage.set('configurationRedy', dataLogin.configurationRedy);
      // this.storage.set('data', dataLogin.data);
      // console.error('dataLoginType :: ', dataLogin);
    } catch (e) {
      console.error('Error :: ', e);
      return null;
    }
  }



}
