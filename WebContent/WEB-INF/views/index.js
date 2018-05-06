/**
 * 
 */
const testFolder = '/home/anand/Documents/books/';
const fs = require('fs');

function callDir()  {
fs.readdir(testFolder, (err, files) => {
  files.forEach(file => {
    console.log(file);
  });
})
}