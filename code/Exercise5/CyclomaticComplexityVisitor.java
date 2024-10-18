package fr.istic.vv;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CyclomaticComplexityVisitor extends VoidVisitorAdapter<Void> {

    private int complexity = 1; // Par défaut 1 pour la méthode elle-même

    @Override
    public void visit(IfStmt n, Void arg) {
        complexity++;
        super.visit(n, arg);
    }

    @Override
    public void visit(ForStmt n, Void arg) {
        complexity++;
        super.visit(n, arg);
    }

    @Override
    public void visit(WhileStmt n, Void arg) {
        complexity++;
        super.visit(n, arg);
    }

    @Override
    public void visit(SwitchStmt n, Void arg) {
        complexity++;
        super.visit(n, arg);
    }

    @Override
    public void visit(CatchClause n, Void arg) {
        complexity++;
        super.visit(n, arg);
    }

    public int calculateComplexity(MethodDeclaration method) {
        method.accept(this, null);
        return complexity;
    }
}
