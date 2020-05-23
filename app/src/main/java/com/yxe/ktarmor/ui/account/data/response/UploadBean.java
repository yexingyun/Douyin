package com.yxe.ktarmor.ui.account.data.response;

/**
 * Created by xingyunye on
 * 2020/5/8.16:32
 */
public class UploadBean extends BaseBean<UploadBean> {

    /**
     * data : {"id":12,"createTime":1588926511028,"updateTime":1588926511028,"userid":"75593256","url":"/images/加速视频.mp4","videoTitle":"加速视频","videoCoverImgUrl":null,"filename":"加速视频.mp4","type":1,"playNum":null,"zanNum":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12
         * createTime : 1588926511028
         * updateTime : 1588926511028
         * userid : 75593256
         * url : /images/加速视频.mp4
         * videoTitle : 加速视频
         * videoCoverImgUrl : null
         * filename : 加速视频.mp4
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
