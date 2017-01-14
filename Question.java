package EnglishHangmanGamePack;

//Question class
public class Question {
	private String questionText;
	private String answerText;

	public Question(String qt, String at) {
		this.questionText = qt;
		this.answerText = at;
	}

	public String getQuestion() {
		return questionText;
	}

	public String getAnswer() {
		return answerText;
	}

}