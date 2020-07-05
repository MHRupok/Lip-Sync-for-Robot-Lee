package lipsync;

import gnu.io.NRSerialPort;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mehed
 */
class Arduino {

    int deg = 0;

    Arduino(int x) throws IOException {
//        for (String s : NRSerialPort.getAvailableSerialPorts()) {
//            System.out.println("Availible port: " + s);
//        }

        String port = "COM5";
        int baudRate = 9600;
        NRSerialPort serial = new NRSerialPort(port, baudRate);
        serial.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(serial.getInputStream()));
        PrintWriter pw = new PrintWriter(serial.getOutputStream());

        while (!br.ready()) {

        }
        System.out.println("wait over.");
        
        //pw.write(x);
        //pw.flush();
        //System.out.println(br);
//        String d=br.readLine();
//        System.out.println("d="+d);
//        
//        
        
//        
//        d=br.readLine();
//        System.out.println("d="+d);

        //serial.disconnect();
    }

    void sends(int x) {
        
    }

}
