"use strict";

const fs = require("fs");
const jsont = require("json-transforms");

// show the commandline parameter values
process.argv.forEach(function (val, index, array) {
  console.log(index + ": " + val);
});

// path of input JSON file
const sourceFile = process.argv[2];

// path of output JSON file
const targetFile = process.argv[3];

// read the input JSON
const json = JSON.parse(fs.readFileSync(sourceFile, { encoding: "utf8" }));

// perform identity transform
const rules = [jsont.identity];
const transformed = jsont.transform(json, rules);

// write the transformation result into file
fs.writeFileSync(targetFile, JSON.stringify(transformed,null,2));
