package qna.domain;

import org.hibernate.annotations.Where;
import qna.exception.CannotDeleteException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Answers {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Answers() {
        answers = new ArrayList<>();
    }

    public void add(final Answer answer) {
        answers.add(answer);
    }

    public List<DeleteHistory> delete(final User loginUser) throws CannotDeleteException {
        checkAnswerWrittenBySomeoneElse(loginUser);
        for (Answer answer : answers) {
            answer.changeDeleted(true);
        }
        return DeleteHistory.of(answers);
    }

    private void checkAnswerWrittenBySomeoneElse(User loginUser) throws CannotDeleteException {
        boolean isExistWrittenAnswerBySomeoneElse = answers.stream().anyMatch(answer -> !answer.isOwner(loginUser));
        if (isExistWrittenAnswerBySomeoneElse) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }
}
