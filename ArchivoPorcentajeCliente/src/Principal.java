
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Principal {

    static String ip;
    static int numsocket;
    static String ruta;
    public static final String generarip ="^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final Pattern patronip = Pattern.compile(generarip);
    public static String descargas= "Desktop\\";
    
    public static void main(String[] args) {
        try{
            ip= (args[0]);
            if(!ipvalida(ip)){
                System.out.println("IP Invalida");
            System.exit(0); }
        } catch (Exception e) {System.out.println("IP Invalida");System.exit(0);}
        try{
            numsocket= Integer.parseInt(args[1]);
        } catch (Exception e) {System.out.println("Socket Invalido");System.exit(1);}
        try{
            ruta= (args[2]);
        } catch (Exception e) {System.out.println("Dirección Invalida");System.exit(2);}
            Socket client = null;
            PrintWriter escritor= null;
            BufferedReader lector = null;
            try{
            client = new Socket(ip, numsocket);
            }catch ( Exception e ) {
            System.err.println("Error al crear el socket");
        }
            try{
            escritor=new PrintWriter(client.getOutputStream(),true);
            }catch ( Exception e ) {
            System.err.println("Ah ocurrido un error al escribir");
        }
            try{
            lector = new BufferedReader(new InputStreamReader(client.getInputStream()));
           }catch ( Exception e ) {
            System.err.println("Ah ocurrido un error al leer");
        } 
            try{
            escritor.println(ruta);
            String datosEntrada= "";
            while(true) {
                try{
                    datosEntrada=lector.readLine();
                    if(datosEntrada.equals("null")){
                        client.close();
                    }
                }catch(Exception e){System.exit(2); client.close();}
                LeerArchivo(Long.parseLong(datosEntrada),client);
        }
            }catch ( Exception e ) {
            System.err.println("La comunicacion fallo");
        }   
    }
    public static void LeerArchivo(long size, Socket connection) throws IOException{
        BufferedInputStream bis;
        BufferedOutputStream bos;
        byte[] receivedData;
        int in;
        String file;
        //Buffer de 1024 bytes
           receivedData = new byte[1024];
           bis = new BufferedInputStream(connection.getInputStream());
           DataInputStream dis=new DataInputStream(connection.getInputStream());
           //Nombre del fichero y otra wea
           file = dis.readUTF();
           file = file.substring(file.indexOf('\\')+1,file.length());
           //Para guardar fichero recibido
           bos = new BufferedOutputStream(new FileOutputStream(descargas + file));
           System.out.println("Descargando Archivo con tamaño de: " + convertirbytes(size));
           long i= 0;
           int repetido=0;
           int porsentaje= 0;
           while ((in = bis.read(receivedData)) != -1){
           bos.write(receivedData,0,in);
           i += in;
           porsentaje= (int)(((double)i / (double) size) *100);
           if(porsentaje!=repetido){
               repetido= porsentaje;
               barradeprogreso(porsentaje, 100, i, size);}}
           if(porsentaje==100){
              System.out.println("Archivo "+file+" descargado");
           }
        bos.close();
        dis.close();
    }
    
     public static boolean ipvalida(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = patronip.matcher(ip);
        return matcher.matches();
    }
     
      public static String convertirbytes(long size) {
    String hrSize = null;

    double b = size;
    double k = size/1024.0;
    double m = ((size/1024.0)/1024.0);
    double g = (((size/1024.0)/1024.0)/1024.0);
    double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);

    DecimalFormat dec = new DecimalFormat("0.00");

    if ( t>1 ) {
        hrSize = dec.format(t).concat(" TB");
    } else if ( g>1 ) {
        hrSize = dec.format(g).concat(" GB");
    } else if ( m>1 ) {
        hrSize = dec.format(m).concat(" MB");
    } else if ( k>1 ) {
        hrSize = dec.format(k).concat(" KB");
    } else {
        hrSize = dec.format(b).concat(" Bytes");
    }
    return hrSize;
}
     public static void barradeprogreso(int actual, int total, long tamañoactual, long tamañomaximo) {
    if (actual > total) {
        throw new IllegalArgumentException();
    }
    int tamañobarra = 10;
    int porsentaje = ((100 * actual) / total) / tamañobarra;
    char carga = '-';
    String icono = "=";
    String barra = new String(new char[tamañobarra]).replace('\0', carga) + "]";
    StringBuilder barraterminada = new StringBuilder();
    barraterminada.append("[");
    for (int i = 0; i < porsentaje; i++) {
        barraterminada.append(icono);
    }
    String bareRemain = barra.substring(porsentaje, barra.length());
    System.out.print("\rDescargado: " + barraterminada + bareRemain + " " + actual + "% [" + convertirbytes(tamañoactual) + "]");
    if (actual == total) {
        System.out.print("\n");
    }
} 
}
