import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-barra-navegacion',
  templateUrl: './barra-navegacion.component.html',
  styleUrls: ['./barra-navegacion.component.css']
})
export class BarraNavegacionComponent implements OnInit {
  
  public iscollapse = true;
  namePdf = 'CV_ALEJANDRO_SUBERO_2021';

  constructor( ) { }

  ngOnInit(): void {
  }


dowload(): void {
    
  }


blobToFile(data: any, type: string, fileName: string) {
  const a = document.createElement('a');
  document.body.appendChild(a);
  a.style.display = 'none';
  const blob = new Blob([data], { type: type });
  const url = window.URL.createObjectURL(blob);
  a.href = url; a.download = fileName; a.click();
  window.URL.revokeObjectURL(url);
}



}
