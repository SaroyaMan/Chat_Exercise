package chatSystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Dialog.ModalExclusionType;
import java.awt.TextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ClinetGUI implements StringConsumer, StringProducer
{
	private JFrame frmChatApplication;
	private ConnectionProxy proxy;
	private Socket socket;
	private TextArea blockText;
	private String nick;
	private boolean noob = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClinetGUI window = new ClinetGUI();
					window.frmChatApplication.setVisible(true);
				}
				catch (Exception e) {e.printStackTrace();}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClinetGUI()
	{
		initialize();
		try
		{
			socket = new Socket("127.0.0.1", 1300);
			proxy = new ConnectionProxy(socket);
			proxy.addConsumer(this);
			proxy.start();
		}
		catch (IOException e) {e.printStackTrace();}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		nick = "";
		while (nick.equals(""))
		{
			nick = JOptionPane.showInputDialog(null,"Please enter your name:","Enter name");
			if (nick == null) System.exit(1);
		}

		frmChatApplication = new JFrame();
		frmChatApplication.setIconImage(Toolkit.getDefaultToolkit().getImage("user.png"));
		frmChatApplication.getContentPane().setBackground(new Color(0, 153, 255));
		frmChatApplication.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		frmChatApplication.setResizable(false);
		frmChatApplication.setTitle("Chat Application - By Yoav Saroya");
		frmChatApplication.setBounds(100, 100, 814, 531);
		frmChatApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatApplication.getContentPane().setLayout(null);
		
		blockText = new TextArea();
		blockText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent arg)
			{
				blockText.setEditable(false);
			}
		});
		blockText.setBounds(58, 47, 655, 336);
		blockText.setFont(new Font("Tahoma", Font.BOLD, 16));
		frmChatApplication.getContentPane().add(blockText);
		
		JLabel label1 = new JLabel("Enter text here:");
		label1.setBackground(Color.LIGHT_GRAY);
		label1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label1.setBounds(39, 410, 128, 16);
		frmChatApplication.getContentPane().add(label1);
		
		JFormattedTextField message = new JFormattedTextField();
		message.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if (message.getText().length() > 46)
					e.consume();
			}
		});
		message.setBounds(179, 404, 381, 33);
		message.setBorder(new LineBorder(Color.BLACK, 2));
		message.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmChatApplication.getContentPane().add(message);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBorder(new LineBorder(Color.BLACK, 2));
		btnSend.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!message.getText().equals(""))
				{
					proxy.consume(getNickName()+": "+message.getText());
					message.setText("");
				}
			}
		});
		btnSend.setIcon(new ImageIcon("mail.png"));
		btnSend.setSelectedIcon(null);
		btnSend.setBackground(new Color(153, 153, 102));
		btnSend.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSend.setBounds(604, 403, 100, 33);
		frmChatApplication.getContentPane().add(btnSend);
		
		JLabel nickname = new JLabel("");
		nickname.setBackground(Color.RED);
		nickname.setFont(new Font("Tahoma", Font.BOLD, 16));
		nickname.setBounds(288, 13, 234, 28);
		nickname.setText("Hello "+nick+"!");
		frmChatApplication.getContentPane().add(nickname);
		
		JLabel lblNewLabel = new JLabel("Made By Yoav Saroya");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setIcon(new ImageIcon("C.png"));
		lblNewLabel.setBounds(0, 455, 302, 41);
		frmChatApplication.getContentPane().add(lblNewLabel);
	}

	@Override
	public void addConsumer(StringConsumer sc)
	{

	}

	@Override
	public void removeConsumer(StringConsumer sc)
	{
		
	}

	@Override
	public void consume(String str)
	{
		if (noob == false)
		{
			blockText.append(new Date().toString().replace("IDT", "")+": " +
					 "Welcome to the chat "+getNickName()+"!\n");
		}
		blockText.append(new Date().toString().replace("IDT", "")+str+"\n");
		noob = true;
	}
	
	public String getNickName() {return nick;}
}