package bowling.step2.domain.frame;

import java.util.Objects;

public class FinalFrame extends Frame{

    private static final String FINAL_FRAME_EXCEPTION = "마지막 프레임 번호는 10만 가능합니다.";
    private static final int FINAL_FRAME_NUMBER = 10;

    public FinalFrame(int frameNo) {
        super(frameNo);
    }

    @Override
    void validateFrameNo(int frameNo) {
        if (frameNo != FINAL_FRAME_NUMBER){
            throw new IllegalArgumentException(FINAL_FRAME_EXCEPTION);
        }
    }

    @Override
    public Frame pitch(int pitch) {
        pitches.addFinalPitch(pitch);
        pitches.validateFinalPitches();
        return this;
    }

    @Override
    public boolean pitchesOver() {
        return pitches.finalEnd();
    }

    @Override
    public Frame nextFrame() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalFrame that = (FinalFrame) o;
        return frameNo == that.frameNo &&
                Objects.equals(pitches, that.pitches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frameNo, pitches);
    }

    @Override
    public String toString() {
        return pitches.toString();
    }
}