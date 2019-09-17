
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Principal {

    static String ip;
    static int numsocket;
    static Socket socket;
    public static final String generarip ="^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final Pattern patronip = Pattern.compile(generarip);
    public static void main(String[] args) throws Exception{
        try{
            ip=args[0];
        if(!ipvalida(ip)){
                System.out.println("IP Invalida");
            System.exit(0); }
        }catch(Exception e){System.out.println("IP Invalida");
            System.exit(0);}
        try{
        if(args[1]!=null){
            try {
                numsocket= Integer.parseInt(args[1]);
            } catch (Exception e) {System.out.println("Socket Invalido");
            System.exit(1);}
        }
        }catch(Exception e){System.out.println("Escriba un Socket Invalido");
            System.exit(2);}
        try{
        socket= new Socket(ip, numsocket);
        }catch(Exception e){System.out.println("Hubo un eror al crear el socket");System.exit(3);}
        PrintWriter escritor=new PrintWriter(socket.getOutputStream(),true);
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String datos;
        String datosEntrada;
        Scanner scanner=new Scanner(System.in);
        while(true) {
            if(!(datos=scanner.nextLine()).isEmpty()){
                escritor.println(datos);
                datosEntrada=lector.readLine();
                System.out.println(datosEntrada);
            }else{
                System.out.println("Mensaje invalido");
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
