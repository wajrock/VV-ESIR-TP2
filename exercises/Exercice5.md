# Cyclomatic Complexity with JavaParser

With the help of JavaParser implement a program that computes the Cyclomatic Complexity (CC) of all methods in a given Java project. The program should take as input the path to the source code of the project. It should produce a report in the format of your choice (TXT, CSV, Markdown, HTML, etc.) containing a table showing for each method: the package and name of the declaring class, the name of the method, the types of the parameters and the value of CC.
Your application should also produce a histogram showing the distribution of CC values in the project. Compare the histogram of two or more projects.


Include in this repository the code of your application. Remove all unnecessary files like compiled binaries. Do include the reports and plots you obtained from different projects. See the [instructions](../sujet.md) for suggestions on the projects to use.

You may use [javaparser-starter](../code/javaparser-starter) as a starting point.

## Answer 

We developed a tool using JavaParser to calculate Cyclomatic Complexity (CC) for all methods in a Java project. The program:

- Takes the path to the source code.
- Analyzes each method using the formula 
- 𝐶𝐶 = 𝐸 − 𝑁 + 2𝑃 where 
- E is edges, 
- N is nodes, and 
- P is connected components.
- Extracts method details (package, class, name, parameters, and CC value).
- Generates a CSV report with the results.