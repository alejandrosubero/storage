import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AESEncryptDecryptService {

  constructor() { }

  secretKey = 'YourSecretKeyForEncryption&Descryption';

  // Cifrado
  encripted(data: any) {
    if (data != null && data !== undefined && data !== '') {
      const key = CryptoJS.enc.Latin1.parse(environment.urlAskey);
      const iv = CryptoJS.enc.Latin1.parse(environment.urlAsIv);
      const encrypted = CryptoJS.AES.encrypt(data, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.ZeroPadding
      });
      // console.log('Cifrado: ', encrypted.toString());
      return encrypted.toString();
    }
    return data;
  }


  // Descifrar
  decrypted(dataEncrypted: any) {
    const key = CryptoJS.enc.Latin1.parse(environment.urlAskey);
    const iv = CryptoJS.enc.Latin1.parse(environment.urlAsIv);
    const decrypted = CryptoJS.AES.decrypt(dataEncrypted, key, {
      iv: iv,
      padding: CryptoJS.pad.ZeroPadding
    });
    // console.log('Descifrar:', decrypted.toString(CryptoJS.enc.Utf8));
    return decrypted.toString(CryptoJS.enc.Utf8);
  }



  encrypt(value: string): string {
    return CryptoJS.AES.encrypt(value, this.secretKey.trim()).toString();
  }

  decrypt(textToDecrypt: string) {
    return CryptoJS.AES.decrypt(textToDecrypt, this.secretKey.trim()).toString(CryptoJS.enc.Utf8);
  }

  
  encryptAs(value: string, secretKey:string): string {
    return CryptoJS.AES.encrypt(value, secretKey.trim()).toString();
  }

  decryptAs(textToDecrypt: string, secretKey:string) {
    return CryptoJS.AES.decrypt(textToDecrypt, secretKey.trim()).toString(CryptoJS.enc.Utf8);
  }
}




