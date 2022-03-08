package me.juliarn.smartmirror.backend.api.services.mso.model;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class CoverLessonsState {

  private CoverLesson[] coverLessons;

  @Creator
  public CoverLessonsState(CoverLesson[] coverLessons) {
    this.coverLessons = coverLessons;
  }

  public CoverLesson[] getCoverLessons() {
    return this.coverLessons;
  }

  public void setCoverLessons(CoverLesson[] coverLessons) {
    this.coverLessons = coverLessons;
  }

  @Introspected
  public static class CoverLesson {
    private String date;
    private int period;
    private String coveredTeacher;
    private String teacher;
    private String comment;
    private String course;
    private String subject;

    @Creator
    public CoverLesson(
        String date,
        int period,
        String coveredTeacher,
        String teacher,
        String comment,
        String course,
        String subject) {
      this.date = date;
      this.period = period;
      this.coveredTeacher = coveredTeacher;
      this.teacher = teacher;
      this.comment = comment;
      this.course = course;
      this.subject = subject;
    }

    public String getDate() {
      return this.date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public int getPeriod() {
      return this.period;
    }

    public void setPeriod(int period) {
      this.period = period;
    }

    public String getCoveredTeacher() {
      return this.coveredTeacher;
    }

    public void setCoveredTeacher(String coveredTeacher) {
      this.coveredTeacher = coveredTeacher;
    }

    public String getTeacher() {
      return this.teacher;
    }

    public void setTeacher(String teacher) {
      this.teacher = teacher;
    }

    public String getComment() {
      return this.comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

    public String getCourse() {
      return this.course;
    }

    public void setCourse(String course) {
      this.course = course;
    }

    public String getSubject() {
      return this.subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }
  }
}
