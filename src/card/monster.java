package card;

public class monster implements monstercard{

		private String title;
		private String cardname;
		private int type;
		private int level;
		private int atk;
		private int def;
		private int effect;
		private String race;
		private String att;
		private boolean summonable;
		private boolean specialsummonable;
		private int scf;
		private int attacktime;
		public monster()
		{
			title="";
			cardname="";
			type=0;
			level=0;
			atk=0;
			def=0;
			effect=0;
			race="";
			att="";
			summonable=false;
			specialsummonable=false;
		}
		public int getattacktime(){
			return attacktime;
		}
		public void setattacktime(int i){
			attacktime=i;
		}
		public String getrace(){
			return race;
		}
		public void setrace(String race){
			this.race=race;
		}
		public String getatt(){
			return att;
		}
		public void setatt(String att){
			this.att=att;
		}
		public int geteffect(){
			return effect;
		}
		public void seteffect(int effect){
			this.effect=effect;
		}
		public String getcardname(){
			return cardname;
		}
		public void setcardname(String cardname){
			this.cardname=cardname;
		}
		public boolean getsummonable(){
			return summonable;
		}
		public void setsummonable(boolean summonable){
			this.summonable=summonable;
		}
		public boolean getspeicalsummonable(){
			return specialsummonable;
		}
		public void setspecialsummonable(boolean specialsummonable){
			this.specialsummonable=specialsummonable;
		}
		public int getscf(){
			return scf;
		}
		public void setscf(int scf){
			this.scf=scf;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int gettype() {
			return type;
		}
		public void settype(int type) {
			this.type = type;
		}
		public int getlevel() {
			return level;
		}
		public void setlevel(int level) {
			this.level = level;
		}
		public int getatk(){
			return atk;
		}
		public void setatk(int atk){
			this.atk=atk;
		}
		public int getdef(){
			return def;
		}
		public void setdef(int def){
			this.def=def;
		}
		@Override
		public int compareTo(monstercard o) {
			return this.title.compareTo(o.getTitle());
		}

	}
