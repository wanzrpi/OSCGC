package card;

public interface card extends Comparable<card> {

	public String getTitle();
	public void setTitle(String title);
	public int gettype();
	public void settype(int type);
	public int getposition();
	public void setposition(int position);
	public int getlocation();
	public void settermset(int term);
	public int gettermset();
	public void setlocation(int location);
	public String getpicture();
	public void setpicture(String picture);
	public String getfile();
	public void setfile(String file);
	public monstercard getmonst();
	public spellcard getmag();
	public trapcard gettrap();
	public void setmag(spellcard spell);
	public void settrap(trapcard trap);
	public void setmonst(monstercard monst);
}

