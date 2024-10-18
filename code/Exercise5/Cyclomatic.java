package fr.istic.vv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.github.javaparser.utils.SourceRoot;

public class Cyclomatic {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Please provide the path to the source code.");
            System.exit(1);
        }

        File file = new File(args[0]);
        if (!file.exists() || !file.isDirectory() || !file.canRead()) {
            System.err.println("Provide a path to an existing readable directory");
            System.exit(2);
        }

        // Chemin vers le fichier de rapport
        String reportPath = "report-cyclomatic.txt";
        try (FileWriter writer = new FileWriter(reportPath)) {
            SourceRoot root = new SourceRoot(file.toPath());
            CyclomaticComplexityCalculator calculator = new CyclomaticComplexityCalculator(writer);

            root.parse("", (localPath, absolutePath, result) -> {
                result.ifSuccessful(unit -> unit.accept(calculator, null));
                return SourceRoot.Callback.Result.DONT_SAVE;
            });

            // Confirmation de la génération du rapport
            System.out.println("Report generated at: " + reportPath);

        } catch (IOException e) {
            System.err.println("Error writing to report file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
