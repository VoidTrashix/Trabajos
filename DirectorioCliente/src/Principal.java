
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Principal {

    static String ip;
    static int numsocket;
    static Socket socket;
    static String direccion;
    
    public static final String IPv4_REGEX ="^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final Pattern IPv4_PATTERN = Pattern.compile(IPv4_REGEX);
   
   
    public static void main(String[] args) throws Exception{
        try{
        if(args[0]!=null){
            ip= args[0];
            if(!isValidInet4Address(ip)){
                System.out.println("Escriba una ip valida");
            System.exit(0);
            }
        }else{
            System.out.println("Escriba una ip valida");
            System.exit(0);
        }
        }catch(Exception e){System.out.println("Escriba una IP valida");System.exit(0);}
        try{
        if(args[1]!=null){
            try {
                numsocket= Integer.parseInt(args[1]);
            } catch (Exception e) {System.out.println("Escriba un socket valido");
            System.exit(1);}
        }else{
            System.out.println("Escriba un socket valido");
            System.exit(2);
        }
        }catch(Exception e){System.out.println("Escriba un socket valido");
            System.exit(2);}
        
        try{
        if(args[2]!=null){
            direccion= args[2];
        }else{
            System.out.println("Escriba una dirección valida");
            System.exit(3);
        }
        }catch(Exception e){System.out.println("Escriba una dirección valida");System.exit(0);}
        
        
        //Codigo
        try{
        socket= new Socket(ip, numsocket);
        }catch(Exception e){System.out.println("Hubo un eror al crear el socket");System.exit(3);}
        PrintWriter escritor=new PrintWriter(socket.getOutputStream(),true);
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String datosEntrada;
        escritor.println(direccion);
        System.out.println("");
        while(true) {
                datosEntrada=lector.readLine();
                if(datosEntrada.equals("-fin-")){
                    socket.close();
                    System.exit(0);
                }
                if(datosEntrada.equals("No se encontró la carpeta escrita")){
                    socket.close();
                    System.exit(0);
                }else{
                    System.out.println(datosEntrada);
                }
        }
    }
    
    public static boolean isValidInet4Address(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = IPv4_PATTERN.matcher(ip);
        return matcher.matches();
    }
    
}
