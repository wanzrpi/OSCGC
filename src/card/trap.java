package card;

public class trap implements trapcard{

		private String title;
		private String cardname;
		private int type;
		private int effect;
		public trap()
		{
			title="";
			cardname="";
			type=0;
			effect=0;
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
		@Override
		public int compareTo(trapcard o) {
			return this.title.compareTo(o.getTitle());
		}

	}
