import java.net.*;
import java.io.*;

public class Principal {
    
    static int numsocket;
    static Socket socket;
    static ServerSocket serverSocket;

    public static void main(String[] args) throws Exception{
        try{
            int i= Integer.parseInt(args[args.length-1]);
        } catch (Exception e) {System.out.println("Socket Invalido");}
        if(args[args.length-1]!=null){
            try {
                numsocket= Integer.parseInt(args[args.length-1]);
            }catch(Exception e){System.out.println("Socket Invalido");System.exit(0);}
            
            try {
                serverSocket=new ServerSocket(numsocket);
            }catch(Exception e){System.out.println("No se pudo crear el servidor");System.exit(1);}
            
            
            String entrada="";
            
            while(!serverSocket.isClosed()){
                socket= serverSocket.accept();
                BufferedReader lector=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter escritor=new PrintWriter(socket.getOutputStream(),true);
                if((entrada=lector.readLine())!=null){
                System.out.println(entrada);
                enviararchivo(entrada, escritor);
                serverSocket.close();
                socket.close();
                }else{
                    System.out.println("Mensaje Invalido");
                }
            }
        }else{
            System.out.println("No se introdujo un socket");
        }
    }
    
    public static String enviararchivo(String directorio, PrintWriter escritor) throws FileNotFoundException, IOException{
        String texto= "";
        File file= new File(directorio);
        if(file.exists()){
            FileReader fr = null;
            BufferedReader br = null;
      try {
         fr = new FileReader (file);
         br = new BufferedReader(fr);
         String linea;
         while((linea=br.readLine())!=null){escritor.println(linea);}
      }catch(Exception e){ e.printStackTrace(); }finally{ try{ if( null != fr ){   fr.close(); } }catch (Exception e2){ e2.printStackTrace(); }}
        }else{
            System.out.println("Archivo no encontrado");
        }
        return texto;
    }
    
}
