package ch.hearc.ig.guideresto.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author julien.plumez
 */
@Entity
@Table(name = "NOTES")
public class Grade {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTES")
  @SequenceGenerator(name = "SEQ_NOTES", sequenceName = "SEQ_NOTES",
          initialValue = 1, allocationSize = 1)
  @Column(name = "NUMERO")
  private Integer id;
  @Column(name = "NOTE")
  private Integer grade;
  @ManyToOne
  @JoinColumn(name = "FK_COMM")
  private CompleteEvaluation evaluation;
  //les deux se connaissent
  @ManyToOne
  @JoinColumn(name = "FK_CRIT")
  private EvaluationCriteria criteria;
  //la note connait le critère d'éval mais le critère d'éval ne connaît pas la note

  public Grade() {
    this(null, null, null);
  }

  public Grade(Integer grade, CompleteEvaluation evaluation, EvaluationCriteria criteria) {
    this(null, grade, evaluation, criteria);
  }

  public Grade(Integer id, Integer grade, CompleteEvaluation evaluation, EvaluationCriteria criteria) {
    this.id = id;
    this.grade = grade;
    this.evaluation = evaluation;
    this.criteria = criteria;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  public CompleteEvaluation getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(CompleteEvaluation evaluation) {
    this.evaluation = evaluation;
  }

  public EvaluationCriteria getCriteria() {
    return criteria;
  }

  public void setCriteria(EvaluationCriteria criteria) {
    this.criteria = criteria;
  }
}