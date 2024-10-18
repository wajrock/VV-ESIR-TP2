# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](./designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer

We extended PMD by creating an XPath rule that detects three or more nested if statements, even if they aren't direct children of each other. The rule is defined as follows:

```xml
<?xml version="1.0"?>

<ruleset name="Custom Rules"
    xmlns="http://pmd.sourceforge.net/ruleset/2.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://pmd.sourceforge.net/ruleset/2.0.0 https://pmd.sourceforge.io/ruleset_2_0_0.xsd">

    <description>
        Custom rules to detect deeply nested if-statements.
    </description>

    <rule name="DetectDeeplyNestedIfs"
        language="java"
        message="Avoid deeply nested if-statements (3 or more levels)."
        class="net.sourceforge.pmd.lang.rule.xpath.XPathRule">
        
        <description>
            This rule detects code where if-statements are nested 3 levels deep or more. It helps avoid complex, hard-to-read logic.
        </description>

        <priority>3</priority>

        <properties>
            <property name="xpath">
                <value>
                    <![CDATA[
                    //IfStatement[descendant::IfStatement[descendant::IfStatement]]
                    ]]>
                </value>
            </property>
        </properties>

    </rule>

</ruleset>

```
We applied this rule using PMD on the Apache commons-math GitHub project. PMD returned multiple matches, including the following code:

``` java
if (expected instanceof Double) {
    Double exp = (Double) expected;
    Double act = (Double) actual;
    if (isNumber(exp) && isNumber(act) && exp != 0) {
        actL = Double.doubleToLongBits(act);
        expL = Double.doubleToLongBits(exp);
        if (Math.abs(actL - expL) == 1) {
            if (methodName.equals("toRadians") || methodName.equals("atan2")) {
                return;
            }
        }
        format = "%016x";
    }
}
```