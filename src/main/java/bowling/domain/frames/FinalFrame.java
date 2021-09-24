package bowling.domain.frames;


import bowling.domain.Score;
import bowling.domain.exception.IncorrectNumberOfPinsException;
import com.sun.tools.javac.util.List;

public class FinalFrame extends Frame {

    private static final int FRAME_MAX_ATTEMPTS = 3;
    private static final int TWO_STRIKES = 20;
    private static final int THREE_STRIKES = 30;
    private static final int SECOND_ATTEMPTS = 2;

    public FinalFrame() {
        super();
    }

    public FinalFrame(final boolean isFinish) {
        super(isFinish);
    }

    @Override
    protected boolean isFinishFrame() {
        if (isOverAttempts()) {
            return true;
        }
        if (this.scores.size() == SECOND_ATTEMPTS) {
            return !isPossibleThirdRoll();
        }
        return false;
    }

    @Override
    public void checkValidNextScore(final Score score) {
        int nextDownPins = this.scores.downPins() + score.getNumberOfPins();
        if (isStrikeScore(nextDownPins)) {
            return;
        }
        if (nextDownPins > NUMBER_OF_PINS || nextDownPins < 0) {
            throw new IncorrectNumberOfPinsException();
        }
    }

    private boolean isStrikeScore(int nextDownPins) {
        return List.of(TWO_STRIKES, THREE_STRIKES).contains(nextDownPins);
    }

    private boolean isPossibleThirdRoll() {
        if (this.scores.size() != SECOND_ATTEMPTS) {
            return false;
        }
        if (isStrike()) {
            return true;
        }
        return isSpare();
    }

    @Override
    protected boolean isOverAttempts() {
        return this.scores.size() >= FRAME_MAX_ATTEMPTS;
    }
}