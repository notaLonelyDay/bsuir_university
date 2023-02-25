const router = require('express').Router()
const service = require('../service/service')
const multer = require('multer')

const trueType = ['image/jpeg', 'image/x-png', 'image/png', 'image/gif']

const upload = multer({
    dest: 'uploads/',
    fileSize: 1024 * 1024 * 10,
    fileFilter: (req,file,cb)=>{
        if (!trueType.includes(file.mimetype)){
            cb(new Error('Incorrect file type'))
        }

        cb(null, true)
    }}).single('file')


router.get('/', async (req, res) => {
    let page = req.query.page

    const posts = await service.getAllPosts(page)
    console.log(posts)
    res.render('index', {title: 'Hey', message: 'FUCK!', posts: posts})
})


router.get('/add', (req, res)=>{
    res.render('add')
})


router.post('/add', (req, res) => {
    upload(req, res, function (err) {
        if (err) {
            res.status(400)
            res.send(err.message)
            return;
        }

        service.createPost(req.body, req.file)
            .then(()=>{ res.redirect("/") })
            .catch((err) => {
                res.status(400)
                res.send(err.message)
            })
    })
})


router.get('/all', async (req, res) => {
    const posts = await service.getAllPosts()
    res.json(posts)
})



module.exports = router;
