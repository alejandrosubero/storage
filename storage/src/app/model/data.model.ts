import { Byte } from "@angular/compiler/src/util";

export class Data {

    alertAmounts: number;
    category: string;
    dateSave: DateSave;
    itemActive: boolean;
    itemDescription: string;
    itemName: string;
    itemNumber: string;
    itemType: string;
    itemUse: string;
    showAlert: boolean;
    statusCloud: string;
    storeArea: string;
    storeSecction: string;
    storeType: string;
    type: any;
    uid: number;
    unit: string;
    key: string;
    itemImageString: string;
    itemImage: Byte[];
    itemImageBase64String: string;

    constructor() { }

}



export class DateSave {
    date: number;
    day: number;
    hours: number;
    minutes: number;
    month: number;
    seconds: number;
    time: number;
    timezoneOffset: number;
    year: number;
}
