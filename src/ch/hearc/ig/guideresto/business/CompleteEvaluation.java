package ch.hearc.ig.guideresto.business;

/**
 *
 * @author julien.plumez
 */
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;

public class CompleteEvaluation extends Evaluation {
    @Column(name="COMMENTAIRE")
    private String comment;
    @Column(name="NOM_UTILISATEUR")
    private String username;
    @OneToMany
    //il faut voir comment le mapping est fait dans ce sens...
    //est-ce que vu qu'on mappe déjà de l'autre côté, le framework est assez intelligent pour comprendre...
    private Set<Grade> grades;

    public CompleteEvaluation() {
        this(null, null, null, null);
    }

    public CompleteEvaluation(Date visitDate, Restaurant restaurant, String comment, String username) {
        this(null, visitDate, restaurant, comment, username);
    }
    
    public CompleteEvaluation(Integer id, Date visitDate, Restaurant restaurant, String comment, String username) {
        super(id, visitDate, restaurant);
        this.comment = comment;
        this.username = username;
        this.grades = new HashSet();
    }
    
    public void addGrade(Grade grade){
        this.grades.add(grade);
        grade.setEvaluation(this);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }
}