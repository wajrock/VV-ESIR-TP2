package fr.istic.vv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

public class PrivateFieldsWithoutGettersDetector extends VoidVisitorWithDefaults<Void> {

    private final FileWriter writer;
    private String currentPackage = "";

    public PrivateFieldsWithoutGettersDetector(FileWriter writer) {
        this.writer = writer;
        try {
            // Écrire l'en-tête une seule fois
            writer.write("Field,Class,Package\n");
        } catch (IOException e) {
            // Gérer l'exception ici, par exemple en imprimant un message d'erreur
            System.err.println("Error writing header to report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        // Obtenir le nom du package
        currentPackage = unit.getPackageDeclaration()
                            .map(pd -> pd.getName().toString())
                            .orElse("[default package]");
        for (TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, arg);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        if (!declaration.isPublic()) return;

        String className = declaration.getFullyQualifiedName().orElse("[Anonymous]");
        Set<String> privateFields = new HashSet<>();

        // Collecter les champs privés
        for (FieldDeclaration field : declaration.getFields()) {
            if (field.isPrivate()) {
                field.getVariables().forEach(var -> privateFields.add(var.getNameAsString()));
            }
        }

        // Vérifier les getters publics
        for (MethodDeclaration method : declaration.getMethods()) {
            if (method.isPublic() && method.getNameAsString().startsWith("get")) {
                String fieldName = method.getNameAsString().substring(3);
                if (!fieldName.isEmpty()) {
                    fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                    privateFields.remove(fieldName);  // Enlever les champs qui ont des getters
                }
            }
        }

        
        // Écrire les champs sans getter dans le rapport
        for (String field : privateFields) {
            try {
                writer.write(String.format("%s, %s, %s%n", field, className, currentPackage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Visiter les types imbriqués
        for (BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration) {
                member.accept(this, arg);
            }
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }
}
