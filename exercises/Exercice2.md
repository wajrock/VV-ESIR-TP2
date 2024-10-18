
# Using PMD


Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset (see the [pmd install instruction](./pmd-help.md)). Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.


## Answer

here is the project that we use for the question https://github.com/bitcoin-wallet/bitcoin-wallet. We found a false positive on a line 217 which is "final InputStream is = contentResolver.openInputStream(backupUri)". To solve this problem, we should ensure that resources like this inputstream object areclosed after use, for eg: try (InputStream is = contentResolver.openInputStream(backupUri)) {
    // 
}
The false negative are the braces that doesn't affecte the code.
 
