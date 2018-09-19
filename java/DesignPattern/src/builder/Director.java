package builder;

public class Director {
	private Builder builder;
	public Director(Builder builder) {
		this.builder = builder;
	}
	public void construct() {
		builder.makeTitle("Greeting");
		builder.makeString("from morning to afternoon");
		builder.makeItems(new String[] {
				"Good Morning. ",
				"Good Afternoon. ",
		});
		builder.makeString("evening");
		builder.makeItems(new String[] {
				"Good Evening. ",
				"Good night. ",
				"Good bye. ",
		});
		builder.close();
	}

}
