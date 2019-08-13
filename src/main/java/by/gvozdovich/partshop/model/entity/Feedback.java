package by.gvozdovich.partshop.model.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representation of feedbacks
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class Feedback implements DbEntity {
    private int feedbackId;
    private User user;
    private Part part;
    private LocalDate date;
    private String comment;
    private int star;

    private Feedback() {

    }

    public static class Builder {
        private Feedback newFeedback;

        public Builder() {
            newFeedback = new Feedback();
        }

        public Builder withFeedbackId(int feedbackId) {
            newFeedback.feedbackId = feedbackId;
            return this;
        }

        public Builder withUser(User user) {
            newFeedback.user = user;
            return this;
        }

        public Builder withPart(Part part) {
            newFeedback.part = part;
            return this;
        }

        public Builder withDate(LocalDate date) {
            newFeedback.date = date;
            return this;
        }

        public Builder withComment(String comment) {
            newFeedback.comment = comment;
            return this;
        }

        public Builder withStar(int star) {
            newFeedback.star = star;
            return this;
        }

        public Feedback build() {
            return newFeedback;
        }
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public User getUser() {
        return user;
    }

    public Part getPart() {
        return part;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public int getStar() {
        return star;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return feedbackId == feedback.feedbackId &&
                user == feedback.user &&
                part == feedback.part &&
                star == feedback.star &&
                Objects.equals(date, feedback.date) &&
                Objects.equals(comment, feedback.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackId, user, part, date, comment, star);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", user=" + user +
                ", part=" + part +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                ", star=" + star +
                '}';
    }
}
