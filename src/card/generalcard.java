package card;

public class generalcard implements card{

		private String title;
		private int type;
		private int position;
		private int location;
		private String picture;
		private String file;
		private monstercard monst;
		private spellcard magc;
		private trapcard trapc;
		private int termset;
		public generalcard(){
			title="";
			type=0;
			position=0;
			location=0;
			picture="";
			file="";
			monst=new monster();
			magc=new spell();
			trapc=new trap();
			termset=0;
		}
		public void settermset(int term)
		{
			termset=term;
		}
		public int gettermset()
		{
			return termset;
		}
		public monstercard getmonst(){
			return monst;
		}
		public void setmonst(monstercard monst){
			this.monst.setatk(monst.getatk());
			this.monst.setcardname(monst.getcardname());
			this.monst.setdef(monst.getdef());
			this.monst.seteffect(monst.geteffect());
			this.monst.setlevel(monst.getlevel());
			this.monst.setscf(monst.getscf());
			this.monst.setsummonable(monst.getsummonable());
			this.monst.setspecialsummonable(monst.getspeicalsummonable());
			this.monst.setTitle(monst.getTitle());
			this.monst.settype(monst.gettype());
			this.monst.setatt(monst.getatt());
			this.monst.setrace(monst.getrace());
		}
		public spellcard getmag(){
			return magc;
		}
		public void setmag(spellcard magc){
			this.magc.setcardname(magc.getcardname());
			this.magc.seteffect(magc.geteffect());
			this.magc.setTitle(magc.getTitle());
			this.magc.settype(magc.gettype());
		}
		public trapcard gettrap(){
			return trapc;
		}
		public void settrap(trapcard trapc){
			this.trapc.setcardname(trapc.getcardname());
			this.trapc.seteffect(trapc.geteffect());
			this.trapc.setTitle(trapc.getTitle());
			this.trapc.settype(trapc.gettype());
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
		public int getposition() {
			return position;
		}
		public void setposition(int position) {
			this.position = position;
		}
		public int getlocation(){
			return location;
		}
		public void setlocation(int location){
			this.location=location;
		}
		public String getpicture(){
			return picture;			
		}
		public void setpicture(String picture){
			this.picture=picture;
		}
		public String getfile(){
			return file;
		}
		public void setfile(String file){
			this.file=file;
		}
		@Override
		public int compareTo(card o) {
			return this.title.compareTo(o.getTitle());
		}

	}
