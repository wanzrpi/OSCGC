package card;

public interface monstercard extends Comparable<monstercard> {
	public String getrace();
	public void setrace(String race);
	public String getatt();
	public void setatt(String att);
	public int geteffect();
	public void seteffect(int effect);
	public int getattacktime();
	public void setattacktime(int i);
	public String getcardname();
	public void setcardname(String cardname);
	public boolean getsummonable();
	public void setsummonable(boolean summonable);
	public boolean getspeicalsummonable();
	public void setspecialsummonable(boolean specialsummonable);
	public int getscf();
	public void setscf(int scf);
	public String getTitle();
	public void setTitle(String title);
	public int gettype();
	public void settype(int type);
	public int getlevel();
	public void setlevel(int level);
	public int getatk();
	public void setatk(int atk);
	public int getdef();
	public void setdef(int def);
}
