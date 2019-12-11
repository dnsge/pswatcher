package org.dnsge.pswatcher;

import org.dnsge.powerschoolapi.detail.Course;
import org.dnsge.powerschoolapi.detail.GradeGroup;
import org.dnsge.powerschoolapi.detail.GradingPeriod;
import org.dnsge.pswatcher.history.Modification;

import java.io.IOException;
import java.util.List;

public class TelegramModificationActor extends TelegramSender implements Actor<List<Modification<Course>>> {

    public TelegramModificationActor(String token, String chatId) {
        super(token, chatId);
    }

    @Override
    public void actOn(List<Modification<Course>> obj) {
        if (obj.size() == 0)
            return;

        StringBuilder message = new StringBuilder();
        for (Modification<Course> m : obj) {
            GradeGroup oldGroup = m.from().getGradeGroup(GradingPeriod.F1);
            GradeGroup newGroup = m.to().getGradeGroup(GradingPeriod.F1);
            message.append(
                    String.format("Your F1 grade in %s changed from %.2f%% (%s) to a %.2f%% (%s)",
                            m.to().getCourseName(),
                            oldGroup.getNumberGrade(),
                            oldGroup.getLetterGrade(),
                            newGroup.getNumberGrade(),
                            newGroup.getLetterGrade()
                    )
            );
            message.append('\n');
        }

        try {
            sendMessage(message.toString());
        } catch (IOException e) {
            MiniLogger.err(e);
        }
    }

}
