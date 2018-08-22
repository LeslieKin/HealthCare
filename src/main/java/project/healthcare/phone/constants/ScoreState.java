package project.healthcare.phone.constants;

public enum ScoreState {

  NORMAL, FINE, POOR;

  public static final ScoreState get(int score) {
    // adjust score
    if (score < MIN_SCORE) {
      score = MIN_SCORE;
    } else if (score > MAX_SCORE) {
      score = MAX_SCORE;
    }
    if (score >= MIN_FINE) {
      return FINE;
    }
    if (score >= MIN_NORMAL) {
      return NORMAL;
    }
    return POOR;
  }

  private static final int MIN_SCORE = 0;
  private static final int MAX_SCORE = 100;

  private static final int MIN_NORMAL = 60;
  private static final int MIN_FINE   = 80;

}
