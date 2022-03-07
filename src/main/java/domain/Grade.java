package domain;

public class Grade implements HasID<Pair<String, String>> {
    Pair<String, String> idGrade;
    private double grade;
    private int saptamanaPredare;
    private String feedback;

    public Grade(Pair<String, String> idGrade, double grade, int saptamanaPredare, String feedback) {
        this.idGrade = idGrade;
        this.grade = grade;
        this.saptamanaPredare = saptamanaPredare;
        this.feedback = feedback;
    }

    @Override
    public Pair<String, String> getID() { return idGrade; }

    @Override
    public void setID(Pair<String, String> idGrade) { this.idGrade = idGrade; }

    public double getGrade() { return grade; }

    public void setGrade(double grade) { this.grade = grade; }

    public int getSaptamanaPredare() { return saptamanaPredare; }

    public void setSaptamanaPredare(int saptamanaPredare) { this.saptamanaPredare = saptamanaPredare; }

    public String getFeedback() { return feedback; }

    public void setFeedback(String feedback) { this.feedback = feedback; }

    @Override
    public String toString() {
        return "Nota{" +
                "id_student = " + idGrade.getObject1() +
                ", id_tema = " + idGrade.getObject2() +
                ", nota = " + grade +
                ", saptamanaPredare = " + saptamanaPredare +
                ", feedback = '" + feedback + '\'' +
                '}';
    }
}
