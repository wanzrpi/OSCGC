package card;

public interface spellcard extends Comparable<spellcard> {
	public int geteffect();
	public void seteffect(int effect);
	public String getcardname();
	public void setcardname(String cardname);
	public String getTitle();
	public void setTitle(String title);
	public int gettype();
	public void settype(int type);
}
