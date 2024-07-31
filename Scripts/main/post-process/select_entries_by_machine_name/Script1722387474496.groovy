import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern

import com.jayway.jsonpath.Criteria
import com.jayway.jsonpath.Filter
import com.jayway.jsonpath.JsonPath
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonSlurper
import internal.GlobalVariable

// Input
Path projectDir = Paths.get(RunConfiguration.getProjectDir());
Path input = projectDir.resolve("work").resolve(GlobalVariable.TestSuiteShortName + "-source.json")

// Output
Path output = projectDir.resolve("work").resolve(GlobalVariable.TestSuiteShortName + "-selection.json")
Files.createDirectories(output.getParent())

// specify how I use Jayway JsonPath to transform the input JSON
Closure cls = { Path inputJson ->
	Filter filter1 = Filter.filter(Criteria.where("machine_name").regex(Pattern.compile('sss[abf]')));
	Filter filter2 = Filter.filter(
		Criteria.where("['history'][*]['up_machine_name']").regex(Pattern.compile('sss[abf]')));

	Filter filter = filter1.or(filter2)

	List<Map<String, Object>> result =
		JsonPath.parse(Files.newInputStream(inputJson))
			.read("\$[?]", filter)

	return result
}

// apply the templates to transform the input into the output
WebUI.callTestCase(findTestCase("Test Cases/main/post-process/LOG Transform"),
					[
						"inputJson": input,
						"templates": cls,
						"outputJson": output,
						"shouldBeLessThan":  90
					])

// verify the transformation result
def selection = new JsonSlurper().parse(output)

// assert what is expected
assert selection.size() > 0 : "there is no entries that have 'machine_name' that match sss[abf]"