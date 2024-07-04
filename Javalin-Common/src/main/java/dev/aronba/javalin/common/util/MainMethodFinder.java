package dev.aronba.javalin.common.util;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.utils.SourceRoot;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MainMethodFinder {
    public static List<String> findMainMethods(File projectDir) throws IOException {
        List<String> mainClasses = new ArrayList<>();
        findMainMethodsRecursive(projectDir, mainClasses);
        return mainClasses;
    }

    private static void findMainMethodsRecursive(File dir, List<String> mainClasses) throws IOException {
        SourceRoot sourceRoot = new SourceRoot(Paths.get(dir.toURI()));
        sourceRoot.tryToParse();
        sourceRoot.getCompilationUnits().forEach(cu -> {
            cu.findAll(MethodDeclaration.class).forEach(method -> {
                if (method.isPublic() && method.isStatic() &&
                        method.getType().asString().equals("void") &&
                        method.getNameAsString().equals("main") &&
                        method.getParameters().size() == 1 &&
                        method.getParameter(0).getType().asString().equals("String[]")) {
                    String fullyQualifiedName = cu.getPackageDeclaration().map(pd -> pd.getNameAsString() + ".").orElse("")
                            + cu.getPrimaryTypeName().orElse("[Unnamed Class]");
                    mainClasses.add(fullyQualifiedName);
                }
            });
        });
    }
}
