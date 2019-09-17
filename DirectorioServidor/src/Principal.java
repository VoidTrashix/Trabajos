import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

class Principal {

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
                System.out.println("Socket:"+numsocket);
            }catch(Exception e){System.out.println("Socket Invalido");System.exit(0);}
            
            try {
                serverSocket=new ServerSocket(numsocket);
            }catch(Exception e){System.out.println("Socket Invalido");System.exit(1);}
            
            
            String entrada="";
            while(true){
                socket= serverSocket.accept();
                BufferedReader lector=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter escritor=new PrintWriter(socket.getOutputStream(),true);
                if((entrada=lector.readLine())!=null){
                System.out.println(entrada);
                EnviarDirecciones(entrada, escritor);
                }
            }
        }else{
            System.out.println("No se introdujo un socket");
        }
  }
  
  public static void EnviarDirecciones(String direccion, PrintWriter escritor){
    File carpeta = new File(direccion);
    if(carpeta.exists()){
    File[] archivos = carpeta.listFiles();
    if (archivos == null || archivos.length == 0) {
      System.out.println("No hay elementos dentro de la carpeta actual");
      return;
    }
    else {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      for (int i=0; i< archivos.length; i++) {
        File archivo = archivos[i];
        escritor.println(String.format("%s (%s) - %d - %s", archivo.getName(), archivo.isDirectory() ? "Carpeta" : "Archivo",archivo.length(),sdf.format(archivo.lastModified())));
      }
      escritor.println("-fin-");
    }
    }else{
        escritor.println("Carpeta no encontrada");
        System.out.println("Carpeta no encontrada");
    }
  }
}