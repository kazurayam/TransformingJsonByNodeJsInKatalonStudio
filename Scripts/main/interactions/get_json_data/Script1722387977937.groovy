import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import com.kms.katalon.core.configuration.RunConfiguration

import internal.GlobalVariable

// Input
Path projectDir = Paths.get(RunConfiguration.getProjectDir());
Path input = projectDir.resolve("src/test/fixture/data.json")

// Output
Path output = projectDir.resolve("work").resolve(GlobalVariable.TestSuiteShortName + "-source.json")
Files.createDirectories(output.getParent())

Files.copy(input, output, StandardCopyOption.REPLACE_EXISTING);
