import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Window.Type;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.SwingConstants;

import org.json.JSONArray;
import org.json.JSONObject;



public class Window {

	private JFrame frmHeatHanger;
	private JTextField txtEnterTextHere;
	private  PortMain portMan;
	private JTextField txtManuallySetThe;
	JLabel label;
	JLabel label_1;
	JLabel label_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmHeatHanger.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void updateMetrics(String currTemp, int targetTemp, int weatherTemp)
	{
		label.setText(currTemp);
		label_1.setText(targetTemp + "");
		label_2.setText(weatherTemp + "");
	}
	public void updateMetrics(String currTemp, int targetTemp)
	{
		label.setText(currTemp);
		label_1.setText(targetTemp + "");
	}
	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHeatHanger = new JFrame();
		frmHeatHanger.setResizable(false);
		frmHeatHanger.setForeground(Color.WHITE);
		frmHeatHanger.setTitle("HeatHanger");
		frmHeatHanger.setBounds(100, 100, 515, 403);
		frmHeatHanger.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHeatHanger.getContentPane().setLayout(null);
		portMan = new PortMain();
		try {
			portMan.connect("COM6");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		label = new JLabel("0");
		label.setBounds(282, 193, 56, 16);
		frmHeatHanger.getContentPane().add(label);
		
		label_1 = new JLabel("0");
		label_1.setBounds(282, 222, 56, 16);
		frmHeatHanger.getContentPane().add(label_1);
		
		label_2 = new JLabel("0");
		label_2.setBounds(282, 251, 56, 16);
		frmHeatHanger.getContentPane().add(label_2);
		
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e)
			{
				//portMan.sendData(txtEnterTextHere.getText());
				double weatherTemp = getWeather(txtEnterTextHere.getText());
				double temp = (weatherTemp * -1.4 + 60);
				System.out.println("Target Temperature: " + temp);
				portMan.sendData((int)temp  + "");
				updateMetrics(portMan.getCurrentTemp(), (int)temp, (int)weatherTemp);
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JLabel lblCity = new JLabel("City Location");
		lblCity.setForeground(Color.BLACK);
		lblCity.setBounds(32, 27, 72, 16);
		frmHeatHanger.getContentPane().add(lblCity);
		btnSend.setBounds(315, 44, 140, 25);
		frmHeatHanger.getContentPane().add(btnSend);
		
		txtEnterTextHere = new JTextField();
		
		txtEnterTextHere.setToolTipText("Automatically preheat clothes on cold days");
		txtEnterTextHere.setBounds(32, 45, 243, 22);
		frmHeatHanger.getContentPane().add(txtEnterTextHere);
		txtEnterTextHere.setColumns(10);
		
		JLabel lblCityLocation = new JLabel("Heating Options");
		lblCityLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCityLocation.setBounds(227, 13, 111, 16);
		frmHeatHanger.getContentPane().add(lblCityLocation);
		
		JLabel lblTargetTemperature = new JLabel("Target Temperature");
		lblTargetTemperature.setForeground(Color.BLACK);
		lblTargetTemperature.setBounds(32, 86, 117, 16);
		frmHeatHanger.getContentPane().add(lblTargetTemperature);
		
		txtManuallySetThe = new JTextField();
		txtManuallySetThe.setToolTipText("Manually set the preffered temperature of your cloths");
		txtManuallySetThe.setBounds(32, 103, 243, 25);
		frmHeatHanger.getContentPane().add(txtManuallySetThe);
		txtManuallySetThe.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e)
			{
				String targetTemp = txtManuallySetThe.getText();
				portMan.sendData(txtManuallySetThe.getText());
				updateMetrics(portMan.getCurrentTemp(), Integer.parseInt(targetTemp));
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		btnNewButton.setBounds(315, 103, 140, 25);
		frmHeatHanger.getContentPane().add(btnNewButton);
		
		JLabel lblMetrics = new JLabel("Metrics");
		lblMetrics.setBounds(254, 161, 56, 16);
		frmHeatHanger.getContentPane().add(lblMetrics);
		
		JLabel lblCurrentTemperature = new JLabel("Current Temperature:");
		lblCurrentTemperature.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentTemperature.setBounds(137, 193, 133, 16);
		frmHeatHanger.getContentPane().add(lblCurrentTemperature);
		
		
		JLabel lblTargetTemperature_1 = new JLabel("Target Temperature:");
		lblTargetTemperature_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTargetTemperature_1.setBounds(137, 222, 133, 16);
		frmHeatHanger.getContentPane().add(lblTargetTemperature_1);
		
		JLabel lblWeatherTemperature = new JLabel("Weather Temperature:");
		lblWeatherTemperature.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWeatherTemperature.setBounds(137, 251, 133, 16);
		frmHeatHanger.getContentPane().add(lblWeatherTemperature);
	}
	public double getWeather(String city)
	{
		HttpURLConnection uc = null;
        try
        {
        	city = city.replaceAll(" ", "%20");
            URL u = new URL("http://dataservice.accuweather.com/locations/v1/search?q="+city+"&apikey=HackPSU2017");//"http://104.39.93.174:8080/api/login");
            uc = (HttpURLConnection) u.openConnection();
            uc.setRequestProperty("Content-Type", "application/json");
            InputStream in = uc.getInputStream();
            Reader r = new InputStreamReader(in);
            int c;
            String output = "";
            while((c = r.read()) != -1)
            {
                output += (char)c;
            }
            in.close();
            JSONArray obj = new JSONArray(output);
            String name = obj.getJSONObject(0).getString("LocalizedName");
            JSONObject region = obj.getJSONObject(0).getJSONObject("Region");
            String local = region.getString("LocalizedName");
            String key = obj.getJSONObject(0).getString("Key");

            u = new URL("http://dataservice.accuweather.com/currentconditions/v1/"+key+"?apikey=HackPSU2017&details=true");//"http://104.39.93.174:8080/api/login");
            uc = (HttpURLConnection) u.openConnection();
            uc.setRequestProperty("Content-Type", "application/json");
            in = uc.getInputStream();
            r = new InputStreamReader(in);
            output = "";
            while((c = r.read()) != -1)
            {
                output += (char)c;
            }
            in.close();
            JSONArray condition = new JSONArray(output);
            double weathertext = condition.getJSONObject(0).getJSONObject("Temperature").
            		getJSONObject("Metric").getDouble("Value");

            return weathertext;

        }

        catch (IOException ex)
        {
            try
            {
                int code = uc.getResponseCode();
                Reader r = new InputStreamReader(uc.getErrorStream());
                int c;
                while((c = r.read()) != -1)
                {
                    System.out.print((char) c);
                }
                System.out.println(code);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
        catch (Exception e)
        {e.printStackTrace();}
        return 9999999;
	}
}
