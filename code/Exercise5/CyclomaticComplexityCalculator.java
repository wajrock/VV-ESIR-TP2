package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileWriter;
import java.io.IOException;

public class CyclomaticComplexityCalculator extends VoidVisitorAdapter<Void> {

    private FileWriter writer;

    public CyclomaticComplexityCalculator(FileWriter writer) {
        this.writer = writer;
    }

    @Override
    public void visit(MethodDeclaration method, Void arg) {
        super.visit(method, arg);

        // Calcul de la complexité cyclomatique
        int complexity = computeCyclomaticComplexity(method);

        // Ecriture du résultat dans le fichier de rapport
        try {
            writer.write("Method: " + method.getDeclarationAsString() + ", CC: " + complexity + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int computeCyclomaticComplexity(MethodDeclaration method) {
        CyclomaticComplexityVisitor complexityVisitor = new CyclomaticComplexityVisitor();
        return complexityVisitor.calculateComplexity(method);
    }
}
