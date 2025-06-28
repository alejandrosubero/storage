package com.js.storage.backup;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class FileManager {


    public FileManager() {
    }

    public void createFolder(String paht) {
        File localFile = new File(paht);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
    }


    public boolean checkFolder(String paht) {
        File localFile = new File(paht);
        if (!localFile.exists()) {
            return false;
        }
        return true;
    }

    public Boolean createNewFile(String paht, String name) {
        File localFile = new File(paht);
        File file = new File(localFile, name);
        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean fileExist(String paht, String name) {
        boolean respuesta = false;
        File localFile = new File(paht);
        File file = new File(localFile, name);

        if (file.exists()) {
            respuesta = true;
        }
        return respuesta;
    }


    public boolean writeInFile(String paht, String name, String texto) {
        FileWriter fichero = null;
        PrintWriter printWriter = null;
        boolean respuesta = false;

        File localFile = new File(paht);
        File file = new File(localFile, name);

        try {
            if (file.exists()) {
                fichero = new FileWriter(file);
                printWriter = new PrintWriter(fichero);
                printWriter.println(texto);
                printWriter.flush();
                printWriter.close();
                respuesta = true;
            } else {
                if (this.createNewFile(paht, name)) {
                    this.writeInFile(paht, name, texto);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            respuesta = false;
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return respuesta;

    }

    public String readFile(String paht, String name){
        String contenido = "";
        FileInputStream fin = null;

        File localFile = new File(paht);
        File file = new File(localFile, name);
        Log.d("******** readFile = ", "name: "+name + " paht: "+ paht);
        if(file.exists()){
            try {
                Log.d("**** existe = ", "si" );
                fin = new FileInputStream(file);

            }catch ( FileNotFoundException fn){
                fn.printStackTrace();
            }
            InputStreamReader archivo = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(archivo);
            int asiii;
            try {
                while ((asiii = br.read()) != -1){
                    char lectura = (char) asiii;
                    contenido += lectura;
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        Log.d("**** contenido = ", contenido );
        return contenido;
    }

}
