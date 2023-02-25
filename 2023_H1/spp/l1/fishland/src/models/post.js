const mongoose = require('mongoose')
const Schema = mongoose.Schema


const fishPostSchema = new Schema({
    title: String,
    author: {
        type: String,
        default: "Anonymous"
    },
    text: String,
    image: String,
    createdAt: {
        type: Date,
        default: Date.now,
    },
})

module.exports = mongoose.model("FishPost", fishPostSchema)