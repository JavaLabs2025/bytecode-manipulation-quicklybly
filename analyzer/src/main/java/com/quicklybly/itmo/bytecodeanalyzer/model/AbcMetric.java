package com.quicklybly.itmo.bytecodeanalyzer.model;

/*
    ABC rules for Java
    The following rules give the count of Assignments, Branches, Conditionals in the ABC metric for Java:

    1. Add one to the assignment count when:
        Occurrence of an assignment operator (exclude constant declarations and default parameter assignments) (**=, *=, /=, %=, +=, -=, <<=, >>=, &=, !=, ^=, >>>=**).
        Occurrence of an increment or a decrement operator (prefix or postfix) (++, --).
    2. Add one to branch count when
        Occurrence of a function call or a class method call.
        Occurrence of a ‘new’ operator.
    3. Add one to condition count when:
        Occurrence of a conditional operator (<, >, <=, >=, ==, !=).
        Occurrence of the following keywords (‘else’, ‘case’, ‘default’, ‘?’, ‘try’, ‘catch’).
        Occurrence of a unary conditional operator.
 */
public class AbcMetric {

    private long assignment;
    private long branch;
    private long condition;

    public AbcMetric(long assignment, long branch, long condition) {
        this.assignment = assignment;
        this.branch = branch;
        this.condition = condition;
    }

    public AbcMetric(AbcMetric other) {
        this.assignment = other.assignment;
        this.branch = other.branch;
        this.condition = other.condition;
    }

    public void add(AbcMetric other) {
        this.assignment += other.assignment;
        this.branch += other.branch;
        this.condition += other.condition;
    }

    public double getAbc() {
        return Math.sqrt(assignment * assignment + branch * branch + condition * condition);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        AbcMetric abcMetric = (AbcMetric) o;
        return assignment == abcMetric.assignment && branch == abcMetric.branch && condition == abcMetric.condition;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(assignment);
        result = 31 * result + Long.hashCode(branch);
        result = 31 * result + Long.hashCode(condition);
        return result;
    }
}
