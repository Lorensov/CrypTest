import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * @Job for Lacuna Software... DevTest contained in: http://devtest.lacunasoftware.com/#/home
 * @author lorensov
 */
public class principal {
    /**
     * @param args lol
     * 1o Estabilish a TCP connection with the server
     * 2o Receive a message encoded with "T-Scheme"
     * 3o Validade the received message according to "T-Scheme"
     * 4o Break the cipher contained in the message
     * 5o Encode the revealed with "T-Scheme"
     * 6o Send response to server
     * 7o Receive, validade and decrypt the confirmation message from server according to "T-Scheme" and the key used previously to encrypt the first message.
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        //1
          System.out.println("Starting Communication...");
        Socket MyClient = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        try{
            System.out.println("Setting TCP/IP...");
            MyClient = new Socket("191.237.249.140", 64016);
            input = new DataInputStream(MyClient.getInputStream());
            output = new DataOutputStream(MyClient.getOutputStream());}
        catch (UnknownHostException e){System.err.println("Don't know about host: hostname"); }
        catch (IOException e){ System.err.println("Couldn't get I/O for the connection to: hostname");}    
        //2
                System.out.println("Getting message from the server...");
                String responseLine;
                byte[] d;
                
   if (MyClient != null && output != null && input != null){
    try{
        System.out.println("The message from the host is:");
        while ((responseLine = input.readLine()) != null) {System.out.print("Server: " + responseLine);
        System.out.println();
        
        d = new byte [responseLine.length()];
        d = responseLine.getBytes();
        int dg = d.length;
        System.out.println("The Bytes values are:");
         for (int i=0; i<dg ; i++) {System.out.print(d[i]);}
   
     //3 Validate/separate the message according to the "T-Scheme"
      System.out.println("Validating message from the server...");
     //length (2bytes) big endian unsigned short int... shows the length of the message
     //md5 (16bytes) hash value of data
     //data (n bytes)
     //parity (1 byte)
     
//nesse próximo loop eu posso fazer declarção de variável e separar as partes da mensagem em outra array ou variáveis
        
        byte[] tamanho; tamanho = new byte [2];
        byte[] servmd5; servmd5 = new byte [16];
        int bg = d.length - 18;
        //int bg = (Math.abs(d.length) - 18);
        //int bg = (dg-18);
        byte[] data; data = new byte [bg]; 

        System.out.println();
        System.out.println("The length of data[] array is:" +bg);
        System.out.println();
        byte parity = 0;
        
     for (int i=0; i<dg ; i++) {
         if(i == 0 || i == 1){tamanho[i] = d[i];}
         if(1<i && i<18){ servmd5[i-2] = d[i]; }
         if(i>18 && i!=(dg-1)){data[i-18] = d[i];}
         if(i==(dg-1)){ parity = d[i];}
     }
     
     System.out.println(" ");             
     System.out.println("The Length value is: "+tamanho[0]+tamanho[1]);
     System.out.print("The value of md5 is: ");
     for (int i=0; i<servmd5.length;i++){System.out.print(servmd5[i]);}
     System.out.println();
     System.out.print("The value of data is: ");
     for (int i=0; i<data.length;i++){System.out.print(data[i]);}
     System.out.println();     
     System.out.print("The parity Value is: "+parity);
     System.out.println(" ");
    
    //4 Break the cipher contained in the message
    
    //Análise de Frequência
    byte[] moist = new byte[bg];
    int [] moistCounter = new int [bg];
            
    int i,j,counter= 0;
    for (i=0; i < bg; i++){
        counter = 0;
        for (j=0; j < bg; j++){
        if (j<i && data[i] == data[j]){break;}
        if (data[j] == data[i]){counter++;}      
        moist [i] = data[i];
        moistCounter [i] = counter;

    }
}
        //nova array com valores dos bytes e faz bubble sort dela    
        //most counters
        int x,y,aux=0;
        byte baux=0;
            
            //bubble sort de moist, diferenciando byte de int
                        for (x=0;x<bg;x++)
            {
                for (y=0;y<bg;y++)
                {
                    if(moistCounter[x] > moistCounter[y])
                    {
                        //declaração de contador
                        aux = moistCounter[x];
                        moistCounter[x]=moistCounter[y];
                        moistCounter[y]=aux;
                        //declaração de byte
                        baux = moist[x];
                        moist[x]=moist[y];
                        moist[y]=baux;    
                    }
                } 
            }
                        
             //**********printagem de most**********
            System.out.println("Segue abaixo a ordem de bytes de acordo com sua frequência");
        System.out.println("======");
     
      //**********printagem de moist**********
      for (int p=0; p<moist.length;p++){ if (moistCounter[p] != 0) System.out.println("O byte "+moist[p]+" ocorre: "+moistCounter[p]+"x"); }    
     System.out.println("O caralho do Byte mais comum é o: "+moist[0]+" que ocorre: "+moistCounter[0]+" vezes!");
      
    //**********printagem da mensagem**********
     String CodeMessage = new String(data, "US-ASCII");
     System.out.println("Este é a String da Array data[]:");
     System.out.println(CodeMessage);
     System.out.println();
     System.out.println(Arrays.toString(data));
     // ***breaking the message***
     //mais comum coloca na key
        byte spacevalue = (byte) ' '; // (em bytes)
                //creating the key
        byte key; //most int está com valor em ;
            key = (byte) (moist[0] ^ spacevalue);
         System.out.println();
         System.out.println("O valor do espaço é:"+spacevalue);
         System.out.println("O valor do byte mais comum é:"+moist[0]);
         System.out.println("O valor da chave é:"+key);
        //subtrair da string em todos of valores de byte[]d
       for (i=0;i<data.length;i++){ data[i] = (byte) (data[i] ^ key);}
        //printar em string nova mensagem alterada
    
     System.out.println();
     System.out.println("Este é o NOVO valor da String da data[]");
     System.out.println(Arrays.toString(data)); 
     String deCodeMessage = new String(data, "US-ASCII");
     System.out.println("Este é a NOVA String da Array data[]:");
     System.out.println(deCodeMessage);
        }
     }catch (IOException f){System.err.println("No Message Received");}
   }
 /**yet to implement::
     5 Encode the revealed with "T-Scheme"
     problems...
        message length
        do the md5 digest of the message
        data encoded in plain text
        parity changed to 01
     6 Send response to server
     7 Receive, validade and decrypt the confirmation message from server according to "T-Scheme" and 
     the key used previously to encrypt the first message. */
 
        System.out.println("lorensov qualified code!");
}
}
