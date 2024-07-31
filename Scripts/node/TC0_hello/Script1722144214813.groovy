import com.kazurayam.subprocessj.Subprocess;
import com.kazurayam.subprocessj.Subprocess.CompletedProcess;
import com.kms.katalon.core.configuration.RunConfiguration

import internal.GlobalVariable

CompletedProcess cp = new Subprocess().cwd(new File("."))
		.run(Arrays.asList(
			GlobalVariable.NODE_BINARY_PATH,
			"./src/test/js/hello.js"
			)
		)

cp.stderr().forEach({ line -> System.err.println line })
cp.stdout().forEach({ line -> System.out.println line })

//
println cp.toString()
assert cp.returncode() == 0