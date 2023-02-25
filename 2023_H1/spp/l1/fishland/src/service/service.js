const post = require('../models/post')

const count_on_page = 5;

module.exports =  {

    getAllPosts: async (page) => {
        if (page === undefined || page < 1){
            page = 1
        }
        return {
            data: await post.find().sort({createdAt: -1}).limit(count_on_page).skip(count_on_page * (page-1)),
            page: page,
            count: await post.count(),
            countOnPage: count_on_page
        };
    },

    createPost: async (body, file) => {
        if (body.title === undefined || body.title.trim() === ""){
            throw new Error('Название не может быть пустым')
        }
        if (body.text === undefined || body.text.trim() === ""){
            throw new Error('Текст не может быть пустым')
        }

        let new_post = {
            title: body.title,
            author: body.name,
            text: body.text,
            image: file?.path
        }
        await post.create(new_post);
    },

    init: async () => {
        console.log("init")
        let p = {
          title: "test",
          text: "test",
          // image: "https://sun9-west.userapi.com/sun9-64/s/v1/ig2/xUnVZyr5MAPlZo5bYHnMEDCJi-Puqum27G9QVthTZnhWGl-Keh833SRCuHxi3DWgBWaQAPNks6c93NXTo7QswC71.jpg?size=1197x1600&quality=95&type=album",
          // author: "Анон",
        }
      return await post.create(p)
    }

}