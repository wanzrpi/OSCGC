package yugioh;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;

import javax.swing.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import card.card;
import card.generalcard;
import card.spell;
import card.spellcard;
import card.monster;
import card.monstercard;
import card.trap;
import card.trapcard;
public class play {
	static DataOutputStream toServer;
	static DataInputStream fromServer;
	static String[] position={"Face Down","Face Up","Attack position","defend position"};
	static String[] spell_type={"normal","continuous","euqip","quick-play","field","ritual"};
	static String[] trap_type={"normal","continuous","counter"};
	static String[] monster_type={"normal","effect","ritual","fusion","synchro","xyz"};
	static String[] effect_type={"destory all monster cards","draw cards","destory all spell cards","search x card from deck and add to hand"
		,"deal n amount damage","heal n amount lp","increase attack/defence for equiped monster","destory selected spell card"};
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static ArrayList<card> player_deck = new ArrayList<card>();
	static ArrayList<card> player_hand = new ArrayList<card>();
	static ArrayList<card> player_grave = new ArrayList<card>();
	static ArrayList<card> player_out = new ArrayList<card>();
	static ArrayList<card> player_monsterzone = new ArrayList<card>();
	static ArrayList<card> player_spellzone = new ArrayList<card>();
	static ArrayList<card> player_fieldzone = new ArrayList<card>();
	static ArrayList<card> opponent_deck = new ArrayList<card>();
	static ArrayList<card> opponent_hand = new ArrayList<card>();
	static ArrayList<card> opponent_grave = new ArrayList<card>();
	static ArrayList<card> opponent_out = new ArrayList<card>();
	static ArrayList<card> opponent_monsterzone = new ArrayList<card>();
	static ArrayList<card> opponent_spellzone = new ArrayList<card>();
	static ArrayList<card> opponent_fieldzone = new ArrayList<card>();
	static int player_lp=8000;static int opponent_lp=8000;
	static int term=0;
	static int phase=0;
    static int num_summon=0;

    private static void createAndShowGUI(){
        //Create and set up the window.
        JFrame frame = new JFrame("game window");
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(1440, 900));
        
        try {
			frame.getContentPane().add(new JPanelWithBackground("background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        JLabel lbl1 = new JLabel("label 1");
        lbl1.setLocation(27, 20);
        lbl1.setSize(86, 14);
//        frame.add(lbl1);
        frame.pack();
        frame.setVisible(true);
    }
	public static card processcard(String name)
	{
		card ret=new generalcard();
        String filename=name+".txt";
        ret.setfile(filename);
        ret.setlocation(0);
        ret.setpicture(String(name+".jpg"));
        ret.setposition(0);
        ret.setTitle(name);
		try {
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(ret.getfile())); 
            BufferedReader br = new BufferedReader(reader);
            String line = "";  
            line = br.readLine();
            if (line.equals("monster"))
            {
            	monstercard temp=new monster();
                line = br.readLine();
                temp.setcardname(line);
                temp.setTitle(line);
                line = br.readLine();
                temp.setatk(Integer.parseInt(line));
                line = br.readLine();
                temp.setdef(Integer.parseInt(line));
                line = br.readLine();
                temp.setlevel(Integer.parseInt(line));
                line = br.readLine();
                temp.setatt(line);
                line = br.readLine();
                temp.setrace(line);
                line = br.readLine();
                temp.seteffect(Integer.parseInt(line));
                line = br.readLine();
                temp.setscf(Integer.parseInt(line));
                line = br.readLine();
                temp.settype(Integer.parseInt(line));
                line = br.readLine();
                temp.setsummonable(Boolean.parseBoolean(line));
                line = br.readLine();
                temp.setspecialsummonable(Boolean.parseBoolean(line));
                ret.setmonst(temp);
                ret.settype(0);
            }
            else if (line.equals("spell"))
            {
            	spellcard temp=new spell();
                line = br.readLine();
                temp.setcardname(line);
                temp.setTitle(line);
                line = br.readLine();
                temp.seteffect(Integer.parseInt(line));
                line = br.readLine();
                temp.settype(Integer.parseInt(line));
                ret.setmag(temp);
                ret.settype(1);
            }
            else
            {
            	trapcard temp=new trap();
                line = br.readLine();
                temp.setcardname(line);
                temp.setTitle(line);
                line = br.readLine();
                temp.seteffect(Integer.parseInt(line));
                line = br.readLine();
                temp.settype(Integer.parseInt(line));
                ret.settrap(temp);
                ret.settype(2);
            }
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	private static String String(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void process_effect(int type)
	{
		if (effect_type[type].equals("destory all monster cards"))
		{
			for (int i=0;i<player_monsterzone.size();i++)
			{
				player_grave.add(player_monsterzone.get(0));
				player_monsterzone.remove(0);
			}
			for (int i=0;i<opponent_monsterzone.size();i++)
			{
				opponent_grave.add(opponent_monsterzone.get(0));
				opponent_monsterzone.remove(0);
			}
		}
		if (effect_type[type].equals("destory all spell cards"))
		{
			for (int i=0;i<player_spellzone.size();i++)
			{
				player_grave.add(player_spellzone.get(0));
				player_spellzone.remove(0);
			}
			for (int i=0;i<opponent_monsterzone.size();i++)
			{
				opponent_grave.add(opponent_spellzone.get(0));
				opponent_spellzone.remove(0);
			}
		}
		if (effect_type[type].equals("draw cards"))
		{
			for (int i=0;i<2;i++)
			{
				if (term%2==1)
				{
					player_hand.add(player_deck.get(0));
					player_deck.remove(0);
				}
				else
				{
					opponent_hand.add(opponent_deck.get(0));
					opponent_deck.remove(0);
				}
			}
		}
	}
	public static void process_com(String command) throws IOException
	{
		if (command.equals("attack"))
		{
			if (term%2!=1)
			{
				System.out.println("You can only attack in your own term!");
				return;
			}
			if (phase!=3)
			{
				System.out.println("You can only attack in battle phase!");
				return;
			}
			String newcommand=in.readLine();
			String[] commands=newcommand.split(" ");
			int index=Integer.parseInt(commands[0]);
			int target=Integer.parseInt(commands[1]);
			if (player_monsterzone.get(index).getmonst().getattacktime()==0)
			{
				System.out.println("This monster cannot attack anymore!");
				return;
			}
			if (opponent_monsterzone.size()==0)
			{
				opponent_lp=opponent_lp-player_monsterzone.get(index).getmonst().getatk();
				player_monsterzone.get(index).getmonst().setattacktime(0);
				return;
			}
			if (opponent_monsterzone.size()<=target)
			{
				System.out.println("Target not exist!");
				return;
			}
		}
		if (command.equals("set spell/trap"))
		{
			String newcommand=in.readLine();
			int index=Integer.parseInt(newcommand);
			if (term%2!=1)
			{
				System.out.println("You can only set spell/trap card in your own term!");
				return;
			}
			if (phase!=2&&phase!=4)
			{
				System.out.println("You can only set spell/trap card in main phase!");
				return;
			}
			if (player_hand.get(index).gettype()==0)
			{
				System.out.println("You cannot place a monster card in Spell/trap zone!");
				return;
			}
			if (player_spellzone.size()==5)
			{
				System.out.println("There is no space to set a new spell/trap card!");
				return;
			}
			player_spellzone.add(player_hand.get(index));
			player_spellzone.get(player_spellzone.size()-1).settermset(term);
			player_hand.remove(index);
		}
		if (command.equals("cast spell/trap"))
		{
			String newcommand=in.readLine();
			String[] commands=newcommand.split(" ");
			String place=commands[0];
			int index=Integer.parseInt(commands[1]);
			if (term%2!=1&&place.equals("hand"))
			{
				System.out.println("You cannot cast a spell card from hand in enmey term!");
				return;
			}
			if (player_spellzone.size()==5)
			{
				System.out.println("There is no space to set a new spell/trap card!");
				return;
			}
			if (place.equals("hand")&&player_hand.get(index).gettype()==0)
			{
				System.out.println("You cannot cast a monster as a spell card!");
				return;
			}
			if (place.equals("hand")&&player_hand.get(index).gettype()==2)
			{
				System.out.println("You cannot cast a trap card from hand!");
				return;
			}
			if (place.equals("zone")&&player_spellzone.get(index).gettermset()==term
					&&player_spellzone.get(index).gettype()==2)
			{
				System.out.println("You cannot cast a trap card in the term you set it!");
				return;
			}
			if (place.equals("zone")&&player_spellzone.get(index).gettermset()==term
					&&player_spellzone.get(index).gettype()==1&&
					spell_type[player_spellzone.get(index).getmag().gettype()].equals("quick-play"))

			{
				System.out.println("You cannot cast a quick-play spell card in the term you set it!");
				return;
			}
			if (place.equals("zone"))
			{
				if (player_spellzone.get(index).gettype()==1)
					process_effect(player_spellzone.get(index).getmag().geteffect());
				else
					process_effect(player_spellzone.get(index).gettrap().geteffect());		
				player_grave.add(player_spellzone.get(index));
				player_spellzone.remove(index);
			}
			if (place.equals("hand"))
			{
				System.out.println(player_hand.get(index).getmag().geteffect());
				if (player_hand.get(index).gettype()==1)
					process_effect(player_hand.get(index).getmag().geteffect());	
				player_grave.add(player_hand.get(index));
				player_hand.remove(index);
			}
		}
		if (command.equals("summon monster"))
		{
			String newcommand=in.readLine();
			String[] commands=newcommand.split(" ");
			int index=Integer.parseInt(commands[0]);
			int position=Integer.parseInt(commands[1]);
			if (term%2!=1)
			{
				System.out.println("You can only summon monster cards in your own term!");
				return;
			}
			if (phase!=2&&phase!=4)
			{
				System.out.println("You can only summon monster cards in main phase!");
				return;
			}
			if (num_summon==0)
			{
				System.out.println("You have reach the max number of monsters that can summon in this term!");
				return;
			}
			if (player_hand.get(index).gettype()!=0)
			{
				System.out.println("You can only summon monster cards!");
				return;
			}
			if (position!=0&&position!=2)
			{
				System.out.println("You can only summon monster as attack position or face down!");
				return;
			}				
			if(player_hand.get(index).getmonst().getscf()>0)
			{
				System.out.println("please select "+player_hand.get(index).getmonst().getscf()+" monsters to sacrifice for this monster");
				newcommand=in.readLine();
				commands=newcommand.split(" ");
				if (commands.length<player_hand.get(index).getmonst().getscf())
				{
					System.out.println("not enough monsters!");
					return;
				}
			
			}
			else
			{
				if(player_monsterzone.size()==5)
				{
					System.out.println("Too many monsters!");
					return;
				}
			}
			player_monsterzone.add(player_hand.get(index));
			player_monsterzone.get(player_monsterzone.size()-1).setposition(position);
			player_hand.remove(index);
			num_summon--;
		}
		if (command.equals("Show my hand"))
		{
			for (int i=0;i<player_hand.size();i++)
				System.out.println(player_hand.get(i).getTitle());
		}
		if (command.equals("Show my lp"))
		{
				System.out.println(player_lp);
		}
		if (command.equals("Show enemy lp"))
		{
				System.out.println(opponent_lp);
		}
		if (command.equals("Show enemy hand"))
			System.out.println(opponent_hand.size());
		if (command.equals("Show my grave"))
		{
			for (int i=0;i<player_grave.size();i++)
				System.out.println(player_grave.get(i).getTitle());
		}
		if (command.equals("Show enemy grave"))
		{
			for (int i=0;i<opponent_grave.size();i++)
				System.out.println(opponent_grave.get(i).getTitle());
		}
		if (command.equals("Show my monsterzone"))
		{
			for (int i=0;i<player_monsterzone.size();i++)
				System.out.println(player_monsterzone.get(i).getTitle()+"position="+position[player_monsterzone.get(i).getposition()]);
		}
		if (command.equals("Show enemy monsterzone"))
		{
			for (int i=0;i<opponent_monsterzone.size();i++)
				if (opponent_monsterzone.get(i).getposition()==0)
					System.out.println("unknown");
				else
					System.out.println(opponent_monsterzone.get(i).getTitle()+"position="+position[opponent_monsterzone.get(i).getposition()]);
		}	
		if (command.equals("Show my spellzone"))
		{
			for (int i=0;i<player_spellzone.size();i++)
				System.out.println(player_spellzone.get(i).getTitle()+"position="+position[player_spellzone.get(i).getposition()]);
		}
		if (command.equals("Show enemy spellzone"))
		{
			for (int i=0;i<opponent_spellzone.size();i++)
				if (opponent_spellzone.get(i).getposition()==0)
					System.out.println("unknown");
				else
					System.out.println(opponent_spellzone.get(i).getTitle()+"position="+position[opponent_spellzone.get(i).getposition()]);
		}
		
			
	}
	public static String[] process_opp_com(String command)
	{
		String[] ret = {};
		if (command=="Show my hand")
		{
			for (int i=0;i<opponent_hand.size();i++)
				ret[i]=opponent_hand.get(i).getTitle();
		}
		if (command=="Show enemy hand")
			ret[0]=Integer.toString((player_hand.size()));
		if (command=="Show my grave")
		{
			for (int i=0;i<opponent_grave.size();i++)
				ret[i]=(opponent_grave.get(i).getTitle());
		}
		if (command=="Show enemy grave")
		{
			for (int i=0;i<player_grave.size();i++)
				ret[i]=(player_grave.get(i).getTitle());
		}
		if (command=="Show my monsterzone")
		{
			for (int i=0;i<opponent_monsterzone.size();i++)
				ret[i]=(opponent_monsterzone.get(i).getTitle()+" position="+position[opponent_monsterzone.get(i).getposition()]);
		}
		if (command=="Show enemy monsterzone")
		{
			for (int i=0;i<player_monsterzone.size();i++)
				if (player_monsterzone.get(i).getposition()==0)
					ret[i]=("unknown");
				else
					ret[i]=(player_monsterzone.get(i).getTitle()+" position="+position[player_monsterzone.get(i).getposition()]);
		}	
		if (command=="Show my spellzone")
		{
			for (int i=0;i<opponent_spellzone.size();i++)
				ret[i]=(opponent_spellzone.get(i).getTitle()+" position="+position[opponent_spellzone.get(i).getposition()]);
		}
		if (command=="Show enemy spellzone")
		{
			for (int i=0;i<player_spellzone.size();i++)
				if (player_spellzone.get(i).getposition()==0)
					ret[i]=("unknown");
				else
					ret[i]=(player_spellzone.get(i).getTitle()+" position="+position[player_spellzone.get(i).getposition()]);
		}
		return ret;
			
	}
	  public static void client()
	  {
	    Scanner keyboard = new Scanner( System.in );

	    try
	    {
	      // Create a socket and try to connect to the server
	      Socket socket = new Socket( "localhost", 8126 );
	      // Socket socket = new Socket( "monica.cs.rpi.edu", 8123 );
	      // Socket socket = new Socket( "128.213.7.2", 8123 );

	      // Create an input stream to receive data from the server
	      fromServer = new DataInputStream( socket.getInputStream() );

	      // Create an output stream to send data to the server
	      toServer = new DataOutputStream( socket.getOutputStream() );
	      System.out.print( "Duel start! Sending deck to server " );
	      toServer.writeInt(player_deck.size());
	      for (int i=0;i<player_deck.size();i++)
	    	  toServer.writeUTF(player_deck.get(i).getTitle());
	while ( true )
	{
	      // Get the radius from the user
//	      System.out.print( "Please enter command: " );
//	      String command = keyboard.nextLine();
//	      System.out.println(command);
	      // Send the radius to the server
//	      toServer.writeUTF(command);
//	      toServer.flush();

//	      String recieve = fromServer.readUTF();   // BLOCKING !!!!!!

	      // Display to the text area
//	      System.out.println( "recieve ");
	}
	    }
	    catch ( IOException ex )
	    {
	      System.err.println( ex );
	    }
	  }
	public static void server()
	  {
	    System.out.println( "Server started at " + new Date() );

	    try
	    {
	      // Create a server socket that will "listen" on port 8123
	      ServerSocket serverSocket = new ServerSocket( 8126 );
	      //
	      // For a given machine (IP address), there are 65536 port
	      //  numbers available (unsigned short -- 2 bytes)
	      //
	      // The first 1024 port numbers are reserved...
	      //
	      //  ftp     21
	      //  ssh     22
	      //  telnet  23
	      //  http    80
	      //  https   443
	      //  etc.


	      // Listen for a connection request
	      Socket socket = serverSocket.accept();  // BLOCKING !!!!!!!

	      System.out.println( "Accepted incoming connection" );
	      
	      // Create data input and output streams
	      DataInputStream inputFromClient =
	        new DataInputStream( socket.getInputStream() );
	      DataOutputStream outputToClient =
	        new DataOutputStream( socket.getOutputStream() );
	      int size=inputFromClient.readInt();
	      for (int i=0;i<size;i++)
	      {
	    	  String temp=inputFromClient.readUTF();
	    	  card tempcard=new generalcard();
	          tempcard=processcard(temp);
	          opponent_deck.add(tempcard); 
	      }
	      for (int i=0;i<5;i++)
		  {
				player_hand.add(player_deck.get(0));
				opponent_hand.add(opponent_deck.get(0));
				player_deck.remove(0);
				opponent_deck.remove(0);
		  }
		  while(true)
		  {
			  term++;
			    if (term%2==1)
			    {
			    	System.out.println("terms "+term);
				  	System.out.println("Your term");
					System.out.println("Draw phase");
					player_hand.add(player_deck.get(0));
					player_deck.remove(0);
					System.out.println("standby phase");
					phase=1;
					while (true)
					{
						System.out.println("No effect can be trigger this time, want to conitune?(y or commands)");
						outputToClient.writeUTF("No effect can be trigger this time, want to conitune?(y or commands)");
						try {
							String command=in.readLine();
							if (command.equals("y"))
							{
//						        String opponent_command = inputFromClient.readUTF();   // BLOCKING !!!!!
//						        while (opponent_command!="y")
	//					        {
		//					        opponent_command = inputFromClient.readUTF();   // BLOCKING !!!!!
			//				        String[] areturn=process_opp_com(opponent_command);
				//			        outputToClient.writeInt(areturn.length);
					//	        }
								break;
							}
							else
								process_com(command);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("Main phase");
					phase=2;
					num_summon=1;
					String command=in.readLine();
					while (command.equals("jump")!=true)
					{
						process_com(command);
						command=in.readLine();
					}
					System.out.println("Battle phase");
					phase=3;
					command=in.readLine();
					for (int i=0;i<player_monsterzone.size();i++)
						player_monsterzone.get(i).getmonst().setattacktime(1);
					while (command.equals("jump")!=true)
					{
						process_com(command);
						command=in.readLine();
					}
					System.out.println("Main phase 2");
					phase=4;
					command=in.readLine();
					while (command.equals("jump")!=true)
					{
						process_com(command);
						command=in.readLine();
					}
					System.out.println("End phase");
					phase=5;
					command=in.readLine();
					while (command.equals("jump")!=true)
					{
						process_com(command);
						command=in.readLine();
					}
			    }
			}
	    }


	    catch( IOException ex )
	    {
	      System.err.println( ex );
	    }
	  }
	public static void main(String args[]) 
	{
		try {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream("mydeck.txt")); 
            BufferedReader br = new BufferedReader(reader);
            String line = "";  
            line = br.readLine();
            card temp=new generalcard();
            while (line != null) {  
                temp=processcard(line);
                player_deck.add(temp);
                line = br.readLine();
            }  
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long seed = System.nanoTime();
		Collections.shuffle(player_deck, new Random(seed));
		if (args[0].equals("server"))
			server();
		else
			client();
	}
}
