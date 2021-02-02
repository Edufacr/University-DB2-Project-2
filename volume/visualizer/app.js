//Simple app to load html

const express = require('express')
const app = express();
const router = express.Router();
const path = require('path');

const HTML_FILENAME = '/index.html';
const PORT = 9092;

router.get('/',function(req,res){
    res.sendFile(path.join(__dirname+HTML_FILENAME));
});

router.get('/data.json',function(req,res){
  res.sendFile(path.join(__dirname+'/players.json'));
});

app.use('/', router);

app.listen(PORT, () => {
  console.log(`Server is running at https://localhost:${PORT}`);
});