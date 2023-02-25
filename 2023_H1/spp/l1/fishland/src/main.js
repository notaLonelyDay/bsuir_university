const express = require('express');
const routes = require('./routes/route.js')
const path = require('path')
const mongoose = require('mongoose')

mongoose.connect("mongodb://root:root@localhost")


const app = express()
const port = 3000

app.use('/', routes)
app.use('/static', express.static(path.join(__dirname, 'static')))
app.use('/uploads', express.static(path.join(__dirname, '../uploads/')))


app.set('view engine', 'pug')
app.set("views", path.join(__dirname, "views"));


app.listen(port, ()=>{
    console.log("Server started!")
} );