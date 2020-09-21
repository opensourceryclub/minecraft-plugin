const express = require('express')
const fileUpload = require('express-fileupload');
const app = express()
const port = 80

app.use(fileUpload({
    useTempFiles : true,
    tempFileDir : '/tmp/'
}));

app.post('/upload', function(req, res) {
	let files = Object.keys( req.files )
	console.log('recieved', files)
});

app.listen(port, () => {
  console.log(`App running on http://localhost:${port}`)
})