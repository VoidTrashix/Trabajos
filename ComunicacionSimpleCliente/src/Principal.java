import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Principal {

    static String ip;
    static int numsocket;
    static String texto;
    public static final String generarip ="^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final Pattern patronip = Pattern.compile(generarip);
    public static void main(String[] args) throws Exception{
        try{
            ip=args[0];
        if(!ipvalida(ip)){
                System.out.println("IP Invalida");
            System.exit(0); }
        }catch (Exception e){System.out.println("IP Invalida");
            System.exit(0);}
        try{
        if(args[1]!=null){
            try {
                numsocket= Integer.parseInt(args[1]);
            } catch (Exception e) {System.out.println("Socket Invalido");
            System.exit(1);}
        }
        }catch (Exception e){System.out.println("Socket Invalido");
            System.exit(1);}
        try{
        if(!args[2].isEmpty() || args[2]!=null){
            texto= args[2];
        }
        }catch (Exception e){System.out.println("Mensaje Ivalido");
            System.exit(3);}
        try{
        Socket socket= new Socket(ip, numsocket);
        PrintWriter escritor= new PrintWriter(socket.getOutputStream(), true);
        if(!texto.isEmpty()){
            escritor.println(texto);
        }
        socket.close();
        }catch(IOException e){System.out.println("Error al crear el socket"); System.exit(4);}
    }
     public static boolean ipvalida(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = patronip.matcher(ip);
        return matcher.matches();
    }
}