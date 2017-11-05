import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PortMain
{
	private SerialWriter portWriter;
	private static String currentTemp;
    public PortMain()
    {
        super();
        currentTemp = "0";
    }
    public String getCurrentTemp()
    {
    	return currentTemp;
    }
    public void sendData(String data)
    {
    	portWriter.setOutData(data);
    	(new Thread(portWriter)).start();
    }
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();
                portWriter = new SerialWriter(out);
                (new Thread(new SerialReader(in))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    /** */
    public static class SerialReader implements Runnable 
    {

        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                	currentTemp = new String(buffer,0,len);
                	currentTemp.replaceAll("\r\n", "");
                	System.out.println(currentTemp);
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
    	private OutputStream out;
        private String data;
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
            data = "";
        }
        void setOutData(String data)
        {
        	this.data = data;
        }
        public void run ()
        {
            try
            {
                for(int i = 0; i < data.length(); i++)
                {
                    this.out.write(data.charAt(i));
                }
                data = "";
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }
}