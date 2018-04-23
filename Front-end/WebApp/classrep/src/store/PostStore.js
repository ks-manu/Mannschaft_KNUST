import {observable } from 'mobx';

class PostStore {
     all=mobx.observable([
        { id:1,post:'First Post',upvotes:2,downvotes:13},
        {id:2, post:'Second Post',upvotes:15,downvotes:1},
        {id:3, post:'Third Post',upvotes:8,downvotes:5},
    ])
}

export default new PostStore();