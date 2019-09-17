import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.*;

public class Principal {
    
    static String ip;
    static int numsocket;
    static Socket socket;
    static String direccion;
    static String nombre;
    public static final String generarip ="^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
   "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final Pattern patronip = Pattern.compile(generarip);
    public static String descargas= "Desktop\\";
    
  
    
    public static void main(String[] args) throws Exception{
        try{
        if(args[0]!=null){
            ip= args[0];
            if(!ipvalida(ip)){
                System.out.println("IP Invalida");
            System.exit(0);
            }
        }else{
            System.out.println("IP Invalida");
            System.exit(0);
        }
        }catch(Exception e){System.out.println("IP Invalida");System.exit(0);}
        try{
        if(args[1]!=null){
            try {
                numsocket= Integer.parseInt(args[1]);
            } catch (Exception e) {System.out.println("Socket Invalido");
            System.exit(1);}
        }
        }catch(Exception e){System.out.println("Socket Invalido");
            System.exit(2);}
        
        try{
        if(args[2]!=null){
            direccion= args[2];
        }
        }catch(Exception e){System.out.println("Dirección Invalida");System.exit(0);}
       
        try{
        socket= new Socket(ip, numsocket);
        }catch(Exception e){System.out.println("Hubo un eror al crear el socket");System.exit(3);}
        PrintWriter escritor=new PrintWriter(socket.getOutputStream(),true);
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String datosEntrada;
        escritor.println(direccion);
	StringTokenizer tokens=new StringTokenizer(direccion, "\\");
        while(tokens.hasMoreTokens()){
            nombre=tokens.nextToken();
        }
        File file = new File(descargas + nombre);
        if (!file.exists()) {
            file.createNewFile();
        }else{
            file.delete();
            file.createNewFile();
        }
        while(true) {
                datosEntrada=lector.readLine();
                if(datosEntrada==null){
                    socket.close();
                    System.exit(0);
                }else{
                    escribirarchivo(datosEntrada);
                    System.out.println(datosEntrada);
                }
        }
    }
    
    public static void escribirarchivo(String cadena) throws IOException{
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{
        File file = new File(descargas + nombre);
        // Si el archivo no existe, se crea uno nuevo.
        if (!file.exists()) {
            file.createNewFile();
        }
        // Si esta wea funca copiarlo en el otro, borrar este comentario para despues
        fw = new FileWriter(file.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);
        bw.write(cadena + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    } finally {try {
            if (bw != null)
                bw.close();
            if (fw != null)
                fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    }

    public static boolean ipvalida(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = patronip.matcher(ip);
        return matcher.matches();
    }
    
}
