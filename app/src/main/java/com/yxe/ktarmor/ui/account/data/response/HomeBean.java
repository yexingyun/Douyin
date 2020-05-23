package com.yxe.ktarmor.ui.account.data.response;

import java.util.List;

/**
 * Created by xingyunye on
 * 2020/5/6.16:28
 */
public class HomeBean extends BaseBean<HomeBean> {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * createTime : 1588244543506
         * updateTime : 1588244543506
         * userid : 75593256
         * url : /images/dahuangfeng.mp4
         * videoTitle : dahuangfeng
         * videoCoverImgUrl : null
         * filename : dahuangfeng.mp4
         * type : 1
         * playNum : null
         * zanNum : null
         */

        private int id;
        private long createTime;
        private long updateTime;
        private String userid;
        private String url;
        private String videoTitle;
        private Object videoCoverImgUrl;
        private String filename;
        private int type;
        private Object playNum;
        private Object zanNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public Object getVideoCoverImgUrl() {
            return videoCoverImgUrl;
        }

        public void setVideoCoverImgUrl(Object videoCoverImgUrl) {
            this.videoCoverImgUrl = videoCoverImgUrl;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getPlayNum() {
            return playNum;
        }

        public void setPlayNum(Object playNum) {
            this.playNum = playNum;
        }

        public Object getZanNum() {
            return zanNum;
        }

        public void setZanNum(Object zanNum) {
            this.zanNum = zanNum;
        }
    }
}
