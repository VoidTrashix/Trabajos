
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Principal {

    static int numsocket;
    static Socket socket;
    static ServerSocket socketServidor;
    
    public static void main(String[] args) throws Exception{
        try{
            numsocket= Integer.parseInt(args[args.length-1]);
            System.out.println("Socket:"+numsocket);
        }catch(Exception e){System.out.println("No se introdujo un socket valido");System.exit(0);}
        if(args[args.length-1]!=null){
            try {
                numsocket= Integer.parseInt(args[args.length-1]);
            }catch(Exception e){System.out.println("No se introdujo un socket valido");System.exit(0);}
            
            try {
                socketServidor=new ServerSocket(numsocket);
                socket= socketServidor.accept();
            }catch(Exception e){System.out.println("No se pudo crear el servidor");System.exit(1);}
            
            BufferedReader lector=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String entrada="";
            Scanner scanner=new Scanner(System.in);
            String salida;
            PrintWriter escritor=new PrintWriter(socket.getOutputStream(),true);
            do{
                if(!(entrada=lector.readLine()).isEmpty()){
                System.out.println(entrada);
                if(entrada.equalsIgnoreCase("fin")){
                    System.out.println("me voy");
                    socket.close();
                    socketServidor.close();
                    System.exit(0);
                } 
                salida=scanner.nextLine();
                escritor.println(salida);
                }else{
                    System.out.println("No se introdujo un mensaje valido");
                }
                
            }while(!entrada.equalsIgnoreCase("fin"));
        }else{
            System.out.println("No se introdujo un socket");
        }
        
    }
}
