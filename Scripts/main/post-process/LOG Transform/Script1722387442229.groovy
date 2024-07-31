import java.nio.file.Files
import java.nio.file.Path

import groovy.json.JsonOutput


// check the parameters given by the caller Test Case
assert inputJson != null
assert inputJson instanceof Path

assert templates != null
assert templates instanceof Closure

assert outputJson != null    // Path
assert outputJson instanceof Path

if (shouldBeLessThan != null) {
	assert shouldBeLessThan instanceof Integer
} else {
	shouldBeLessThan = 10
}

// apply the transformer over the input JSON to get the result
List<Map<String, Object>> entries = templates.call(inputJson)

// Re-construct a Map in the format of HAR
def reconstructed = entries

// jsonify the reconstructed object,
String reconstructedJson = JsonOutput.toJson(reconstructed)

Files.writeString(outputJson, JsonOutput.prettyPrint(reconstructedJson));
