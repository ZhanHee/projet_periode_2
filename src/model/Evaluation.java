package model;

public class Evaluation {
	
	private int note;
	private String date;

	public Evaluation(int note, String date) {
		this.note = note;
		this.date = date;
	}

	public int getNote() {
		return note;
	}

	public String getDate() {
		return date;
	}

}
