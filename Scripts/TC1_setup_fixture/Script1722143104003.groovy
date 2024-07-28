import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.subprocessj.Subprocess;
import com.kazurayam.subprocessj.Subprocess.CompletedProcess;
import com.kms.katalon.core.configuration.RunConfiguration

import internal.GlobalVariable

// project directory
Path projectDir = Paths.get(RunConfiguration.getProjectDir())

// source data
Path source = projectDir.resolve('src/test/fixture/data.json')
assert Files.exists(source)

// fixture data
Path target = projectDir.resolve('build/data.json')
Files.createDirectories(target.getParent())

// copy the source data into the fixture data
CompletedProcess cp = new Subprocess().cwd(new File("."))
		.run(Arrays.asList(
			GlobalVariable.NODE_BINARY_PATH,  // node
			"./identity-transform.js",        // ./indentity-transform.js
			source.toString(),                // ./src/test/fixture/data.json
			target.toString()                 // ./build/data.json         
			)
		)

cp.stderr().forEach({ line -> System.err.println line })
cp.stdout().forEach({ line -> System.out.println line })

//
println cp.toString()
assert cp.returncode() == 0